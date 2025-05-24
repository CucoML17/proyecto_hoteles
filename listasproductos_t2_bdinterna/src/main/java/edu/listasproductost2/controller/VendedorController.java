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
import edu.listasproductost2.model.Vendedor;
import edu.listasproductost2.service.VendedorService;

@Controller

@RequestMapping("/vendedor")
public class VendedorController {

	//La inyección 
	@Autowired
	private VendedorService vendedorService;
	
	//Ver detalle
	@GetMapping("/ver/{id}")
	public String verDetalle(@PathVariable("id") int idVendedor, Model model) {
		
		Vendedor vendedor = vendedorService.buscarPorIdVendedor(idVendedor);
		
		
		model.addAttribute("vendedor", vendedor);
		
		return "vendedor/detalleV";
	}
	
	//Eliminar
	@GetMapping("/eliminar/{id}")
	public String eliminarVendedor(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		
		
		
		vendedorService.eliminarV(id);
		redirectAttributes.addFlashAttribute("mensajeExitoBorra", "Vendedor borrado con éxito");
		
		
		return "redirect:/vendedor/listaV";		
		
	}
	
	//Formulario para agregar
	@GetMapping("/agregarVendedor")
	public String agregarVendedor(Model model, Vendedor vendedor) {
			
		return "vendedor/formVendedor";		
		
	}	
	
	//Agregar
	@PostMapping("/guardarVendedor")
	public String guardar(Vendedor vendedor, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	        }

	        return "/vendedor/agregarVendedor";
	    }

	    vendedorService.guardarV(vendedor);
	    

	    redirectAttributes.addFlashAttribute("mensajeExito", "Vendedor agregado con éxito");
	    return "redirect:/vendedor/listaV";
	}
	
	//El formato de fecha
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	//Ver todo
	@GetMapping("/listaV")
	public String mostrarLista(Model model) {
		//1. Obtener todos los productos (recuperarlos con la clase Service) 
		List<Vendedor> listaV = vendedorService.buscarTodosVendedores();
		

		
		//2. Agregar al model el listado de los productos 
		model.addAttribute("listaV", listaV);
		
		
		return "vendedor/listaVendedores";
		
	}
	
	
	//Editar
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable("id") int idVendedor, Model model, RedirectAttributes redirectAttributes) {
		Vendedor vendedor = vendedorService.buscarPorIdVendedor(idVendedor);
		model.addAttribute("vendedor", vendedor);
	
		
		
		return "vendedor/formVendedor";	
	}
		
	
}
