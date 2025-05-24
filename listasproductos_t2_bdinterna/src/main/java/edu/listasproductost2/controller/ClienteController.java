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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Cliente;
import edu.listasproductost2.model.Vendedor;
import edu.listasproductost2.service.ClienteService;
import edu.listasproductost2.service.VendedorService;

@Controller

@RequestMapping("/cliente")
public class ClienteController {

	//La inyección 
	@Autowired
	private ClienteService clienteService;
	
	@Autowired 
	private VendedorService vendedorService;
	
	//Ver detalle
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable("id") int idCliente, Model model) {
		
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		
		
		
		model.addAttribute("cliente", cliente);
		
		
		
		return "cliente/detalleC";
	}
	
	//Eliminar
	@GetMapping("/eliminar/{id}")
	public String eliminarCliente(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		
		
		
		clienteService.eliminarId(id);
		redirectAttributes.addFlashAttribute("mensajeExitoBorra", "Cliente borrado con éxito");
		
		
		return "redirect:/cliente/listaC";		
		
	}
	
	//Formulario para agregar
	@GetMapping("/agregarCliente")
	public String agregarVendedor(Model model, Cliente cliente) {
			
		List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
		model.addAttribute("listaV", listaV);		
		
		return "cliente/formCliente";		
		
	}	
	
	//Agregar
	@PostMapping("/guardarCliente")
	public String guardar(Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	        }
			List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
			model.addAttribute("listaV", listaV);

	        return "/cliente/formCliente";
	    }

	    clienteService.guardarCliente(cliente);
	    

	    redirectAttributes.addFlashAttribute("mensajeExito", "Cliente agregado con éxito");
	    return "redirect:/cliente/listaC";
	}
	
	//El formato de fecha
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	//Ver todo
	@GetMapping("/listaC")
	public String mostrarLista(Model model) {
		 
		List<Cliente> listaC = clienteService.buscarTodosClientes();
		model.addAttribute("listaC", listaC);
		
		
		List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
		model.addAttribute("listaV", listaV);
		
		
		
		
		return "cliente/listaCliente";
		
	}
	
	
	//Editar
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") int idCliente, Model model, RedirectAttributes redirectAttributes) {
		Cliente cliente = clienteService.buscarPorIDCliente(idCliente);
		model.addAttribute("cliente", cliente);
	
		List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
		model.addAttribute("listaV", listaV);
		
		
		return "cliente/formCliente";	
	}
		
}
