package edu.listasproductost2.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Producto;
import edu.listasproductost2.service.CategoriaService;
import edu.listasproductost2.service.ProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {
	//La inyección 
	@Autowired
	private ProductoService serviceProducto;
	
	@Autowired
	private CategoriaService serviceCate;
	
	
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable("id") int idProducto, Model model) {
		
		Producto producto = serviceProducto.buscarPorId(idProducto);
		
		System.out.println("El producto es: "+producto);
		model.addAttribute("producto", producto);
		
		return "producto/detalle";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		System.out.println("El producto eliminado es " + id);
		
		
		serviceProducto.eliminarProdId(id);
		redirectAttributes.addFlashAttribute("mensajeExitoBorra", "Producto borrado con éxito");
		
		
		return "redirect:/producto/listaP";		
		
	}
	
	@GetMapping("/agregarProd")
	public String eliminarProductoS(Model model, Producto producto) {
		
		List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);		
		
		return "producto/agregarProducto";		
		
	}	
	
	/*
	@RequestMapping(value="/guardar", method=RequestMethod.POST)
	public String guardar(
			@RequestParam("nombre") String nombre, 
			@RequestParam("descripcion") String descripcion,
			@RequestParam("categoria") String categoria,
			@RequestParam("estatus") String estatus,
			@RequestParam("fecha") String fecha,
			@RequestParam("destacado") int destacado,
			@RequestParam("precio") double precio,
			@RequestParam("imagen") String imagen,
			@RequestParam("detalles") String detalles
			
			) {
			
	    System.out.println("Nombre: " + nombre);
	    System.out.println("Descripción: " + descripcion);
	    System.out.println("Categoría: " + categoria);
	    System.out.println("Estatus: " + estatus);
	    System.out.println("Fecha: " + fecha);
	    System.out.println("Destacado: " + destacado);
	    System.out.println("Precio: " + precio);
	    System.out.println("Imagen: " + imagen);
	    System.out.println("Detalles: " + detalles);
		
		return "/tabla";
	}	
	*/
	//Ahora con el dato control (Databinding)
	

	@PostMapping("/guardar")
	public String guardar(Producto producto, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                      @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	        }

	        List<Categoria> listaC = serviceCate.buscarTodasCates();
	        model.addAttribute("listaC", listaC);
	        return "/producto/agregarProducto";
	    }	    
	    
	    Path rutaDirectorio = Paths.get("/app/imagenesTiendaSpring/productos");

	    // Verificar si el directorio existe, y si no, crearlo
	    if (!Files.exists(rutaDirectorio)) {
	        try {
	            Files.createDirectories(rutaDirectorio);
	            System.out.println("Directorio creado: " + rutaDirectorio);
	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("mensajeError", "Error al crear el directorio de imágenes.");
	            return "redirect:/producto/listaP";
	        }
	    }

	    if (imagenFile != null && !imagenFile.isEmpty()) {
	        try {
	            String nombreArchivo = imagenFile.getOriginalFilename();
	            Path rutaArchivo = rutaDirectorio.resolve(nombreArchivo).normalize();
	            Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
	            producto.setImagen(nombreArchivo); // Guarda el nombre del archivo en la BD
	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen.");
	            return "redirect:/producto/listaP";
	        }
	    } else {
	        if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
	            producto.setImagen(producto.getImagen().replace(",", "").trim());
	        } else {
	            Producto productoExistente = serviceProducto.buscarPorId(producto.getId());
	            if (productoExistente != null) {
	                producto.setImagen(productoExistente.getImagen());
	            }
	        }
	    }

	    serviceProducto.guardarProducto(producto);
	    System.out.println(producto);

	    redirectAttributes.addFlashAttribute("mensajeExito", "Producto agregado con éxito");
	    return "redirect:/producto/listaP";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	//Actividad 1 del día "19/02/2025"
	@GetMapping("/listaP")
	public String mostrarLista(Model model) {
		//1. Obtener todos los productos (recuperarlos con la clase Service) 
		List<Producto> lista = serviceProducto.buscarTodos();
		

		
		//2. Agregar al model el listado de los productos 
		model.addAttribute("listaProduc", lista);
		
		
		return "producto/listaProductos";
		
	}
	
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") int idProducto, Model model, RedirectAttributes redirectAttributes) {
		Producto producto = serviceProducto.buscarPorId(idProducto);
		model.addAttribute("producto", producto);
		
		List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);
		
		
		return "producto/agregarProducto";
	}
	
	

	
	
	
}
