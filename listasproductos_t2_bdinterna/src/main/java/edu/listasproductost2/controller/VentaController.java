package edu.listasproductost2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Carrito;
import edu.listasproductost2.model.Cliente;
import edu.listasproductost2.model.DetalleVenta;
import edu.listasproductost2.model.Producto;
import edu.listasproductost2.model.Vendedor;
import edu.listasproductost2.model.Venta;
import edu.listasproductost2.service.CarritoService;
import edu.listasproductost2.service.ClienteService;
import edu.listasproductost2.service.DetalleVentaService;
import edu.listasproductost2.service.ProductoService;
import edu.listasproductost2.service.VendedorService;
import edu.listasproductost2.service.VentaService;

@Controller

@RequestMapping("/venta")
public class VentaController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired 
	private VendedorService vendedorService;
	
	@Autowired
	private ProductoService serviceProducto;
	
	@Autowired
	private CarritoService carroService;
	
	@Autowired
	private VentaService ventaService;
	
	@Autowired 
	private DetalleVentaService detalleService;
	
	
	@GetMapping("/selecCliente")
	public String mostrarLista(Model model) {
		 
		List<Cliente> listaC = clienteService.buscarTodosClientes();
		model.addAttribute("listaC", listaC);
		
		
		List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
		model.addAttribute("listaV", listaV);
		
		
		
		
		return "venta/selecCliente";
		
	}
	
	//Para obtener ya de una la id del cliente a comprar
	@GetMapping("/comprar/{id}")
	public String verDetalle(@PathVariable("id") int idCliente, Model model) {
		
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		
		
		
		model.addAttribute("clienteCompra", cliente);

		
		List<Producto> lista = serviceProducto.soloDisponibles();
		
		model.addAttribute("listaProduc", lista);
		
		return "venta/selecProductos";
	}	
	
	
	@GetMapping("/addCarrito/{id}/{idCliente}")
	public String agregarProductoAlCarrito(
	    @PathVariable("id") int idProducto,
	    @PathVariable("idCliente") int idCliente, Model model
	) {
		
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		
		
		
		model.addAttribute("clienteCompra", cliente);
		
		//Para renderizar solo el producto que quiero
		Producto prod = serviceProducto.buscarPorId(idProducto);
		model.addAttribute("producto", prod);		


	    return "venta/addCarro"; // Redirige a la página de selección de productos
	}
	
	@PostMapping("/addCarroFinal")
	public String comprarProducto( Model model, RedirectAttributes redirectAttributes,
	    @RequestParam("idCliente") int idCliente,
	    @RequestParam("productoId") int productoId,
	    @RequestParam("cantidad") int cantidad
	    
	) {

	
	    Producto prod = serviceProducto.buscarPorId(productoId);
	    
	    
	    //Instancia el objeto carrito, donde va qué producto se compró y su cantidad
	    Carrito carrito = new Carrito(prod, cantidad);
	    
	    //Lo guarda en carrito, que usaremos más al rato
	    carroService.guardarACarrito(carrito);
	    
	    List<Carrito> listaCarro = carroService.buscarTodoCarrito();
	    
	    System.out.println(listaCarro.get(0).getCantidadCompra());

	    
	    //Volvemos a ver si quiere comprar más productos
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		
		
		
		model.addAttribute("clienteCompra", cliente);

		
		List<Producto> lista = serviceProducto.soloDisponibles();
		
		model.addAttribute("listaProduc", lista);
		
		return "venta/selecProductos"; 
	}	
	
	@GetMapping("/guardarVenta/{idCliente}")
	public String gardVenta(@PathVariable("idCliente") int idCliente, Model model, Venta venta, RedirectAttributes redirectAttributes) {
		
		
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		model.addAttribute("clienteCompra", cliente);
		
		List<Carrito> listaCarro = carroService.buscarTodoCarrito();
		
		if(listaCarro.isEmpty()) {
			//Maneja mensaje de error por si quiere terminar la venta y no ha comprado nada
			redirectAttributes.addFlashAttribute("mensajeError", "No hay productos en tu carrito, lo siento");
			return "redirect:/venta/comprar/" + idCliente;
			
		}else {
			return "venta/formVenta";
			
		}
		
		
	}
	
	@PostMapping("/finVenta")
	public String guardar(Venta venta, BindingResult result, Model model, RedirectAttributes redirectAttributes, @RequestParam("clienteid") int idCliente	                      ) {

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	        }

			Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
			model.addAttribute("clienteCompra", cliente);
			
	        return "venta/formVenta";
	    }
	    //El cliente que la hizo
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		model.addAttribute("clienteCompra", cliente);
		
		venta.setTotal(0);
		venta.setCliente(cliente);

	    int ultId = ventaService.guardarVenta(venta);
	    
	    List<Carrito> listaCarro = carroService.buscarTodoCarrito();
	    
	    double totalVenta = 0.0;
	    
	    
	    for(int i=0; i<listaCarro.size(); i++) {
	    	//Obtenemos el producto de la pos actual
	    	Producto tmpProd = listaCarro.get(i).getProductoCompra();
	    	//El valor de la cantidad comprada lo volvemos a String para después double, son operaciones 
	    	//con doubles:
	    	double cantidad= Double.parseDouble(String.valueOf(listaCarro.get(i).getCantidadCompra()));
	    	
	    	//Calculamos el subtotal
	    	double subTotal = tmpProd.getPrecio() * cantidad;
	    	//Al total de la venta le vamos añadiendo poco a poco la cantidad de cada DetalleVenta
	    	totalVenta = totalVenta + subTotal;
	    	//Llamos al servicio para guardar la asignación en la Base de datos:
	    	detalleService.guardarDetalle( listaCarro.get(i).getProductoCompra().getId(),
	    			ultId, 
	    			listaCarro.get(i).getCantidadCompra(), 
	    			subTotal);
	    	
	    }
	    
	    cliente.setId(idCliente);
	    Venta ventaAct = new Venta(ultId, venta.getFecha(), venta.getFolio(), totalVenta, cliente);
	    
	    ventaService.guardarVenta(ventaAct);

	    redirectAttributes.addFlashAttribute("mensajeExito", "Venta agregada con éxito");
	    
	    //Limpia el carrito para otra venta
	    carroService.limpiarCarrito();
	    
	    return "redirect:/venta/selecCliente";
	}
		
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@GetMapping("/listaV")
	public String mostrarListaVentas(Model model) {
		//1. Obtener todos los productos (recuperarlos con la clase Service) 
		List<Venta> lista = ventaService.buscarTodasVentas();
		

		
		//2. Agregar al model el listado de los productos 
		model.addAttribute("listaVentas", lista);
		
		
		return "venta/listaVentas";
		
	}	

	
	@GetMapping("/verDetalle/{id}")
	public String verDetalleVenta(@PathVariable("id") int idVenta, Model model) {
		
		/*
		Venta venta = serviceProducto.buscarPorId(idProducto);
		
		System.out.println("El producto es: "+producto);
		model.addAttribute("listaDetalleVenta", producto);*/
		//Para los datos como tal de la venta
		Venta venta = ventaService.buscarPorIdVenta(idVenta);
		model.addAttribute("venta", venta);
		
		//Para el listado de sus detalles de venta:
		List<DetalleVenta> lista = detalleService.buscarVentasId(idVenta); 
		model.addAttribute("listaDetalleVenta", lista);
		
		
		return "venta/detalleVenta";
	}	

}
