package edu.listasproductost2.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.service.CategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
	
	//La Inyección:
	@Autowired
	private CategoriaService serviceCate;
	
	
	
	@GetMapping("/listaC")
	public String motrarIndex(Model model) {
		
		return "categorias/listaCategorias";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear() {
		
		return "categorias/nuevaCate";
	}	

	@RequestMapping(value="/saveC", method=RequestMethod.POST)
	public String guardar(@RequestParam("nombreCategoria") String nombreC, 
			@RequestParam("descripcionCategoria") String descC) {
			
		System.out.println("Categoria: " + nombreC);
		System.out.println("Descripción " + descC);
		
		return "categorias/listaCategorias";
	}
	
	
	@GetMapping("mostrarInicio")
	public String mostrarListaCategorias(Model model) {
		
		List<Categoria> listaC = serviceCate.buscarTodasCates();
		
		model.addAttribute("listaCategorias", listaC);
		
		return "categorias/listaCategorias";
		
	}
	
	@GetMapping("formularioCate")
	public String verFormCate(Model model, Categoria categoria) {

		return "categorias/guardarCate";
		
	}	
	
	
	//Para guardar categorías
	@PostMapping("/guardarC")
	public String guardarCategoria(Categoria categoria, BindingResult result, RedirectAttributes redirectAttributes,
	        @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {

	    if (result.hasErrors()) {
	        for (ObjectError error : result.getAllErrors()) {
	            System.out.println("Ocurrieron unos errores: " + error.getDefaultMessage());
	        }
	        return "/categorias/guardarCate";
	    }

	    Path rutaDirectorio = Paths.get("/app/imagenesTiendaSpring/categorias");
	    
	    
	    // Verificar si el directorio existe, y si no, crearlo
	    if (!Files.exists(rutaDirectorio)) {
	        try {
	            Files.createDirectories(rutaDirectorio);
	            System.out.println("Directorio creado: " + rutaDirectorio);
	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("mensajeError", "Error al crear el directorio de imágenes de categorías.");
	            return "redirect:/categorias/mostrarInicio";
	        }
	    }

	    if (imagenFile != null && !imagenFile.isEmpty()) {
	        try {
	            String nombreArchivo = imagenFile.getOriginalFilename();
	            Path rutaArchivo = rutaDirectorio.resolve(nombreArchivo).normalize(); // Usar la ruta del directorio creada/existente
	            Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
	            categoria.setImagen(nombreArchivo); // Guarda el nombre del archivo en la BD
	        } catch (IOException e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen de la categoría.");
	            return "redirect:/categorias/mostrarInicio";
	        }
	    } else {
	        if (categoria.getImagen() != null && !categoria.getImagen().isEmpty()) {
	            categoria.setImagen(categoria.getImagen().replace(",", "").trim());
	        } else {
	            Categoria cateExistente = serviceCate.buscarPorIDCat(categoria.getId());
	            if (cateExistente != null) {
	                categoria.setImagen(cateExistente.getImagen());
	            }
	        }
	    }

	    serviceCate.guardarCategoria(categoria);
	    System.out.println(categoria);

	    redirectAttributes.addFlashAttribute("mensajeExito", "Categoría agregada con éxito");

	    return "redirect:/categorias/mostrarInicio";

	}
	
	
	
	
	//Para las actividades 4
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") int idCate, Model model, RedirectAttributes redirectAttributes) {
		Categoria categoria = serviceCate.buscarPorIDCat(idCate);
		model.addAttribute("categoria", categoria);
		
		return "categorias/guardarCate";
	}	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCategoria(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		System.out.println("La categoría eliminada es " + id);
		
		
		serviceCate.eliminarCateId(id);
		redirectAttributes.addFlashAttribute("mensajeExitoBorra", "Categoría borrada con éxito");
		
		
		return "redirect:/categorias/mostrarInicio";	
		
	}	
	
}
 