package edu.listasproductost2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.listasproductost2.model.Usuario;
import edu.listasproductost2.repository.UsuarioRepository;
import edu.listasproductost2.service.IUsuarioService;
import edu.listasproductost2.service.PerfilService;

@Controller

@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@GetMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		System.out.println("El usuario eliminado es " + id);
		
		
		usuarioService.eliminarU(id);
		
		redirectAttributes.addFlashAttribute("mensajeExitoBorra", "Usuario borrado con éxito");
		
		
		return "redirect:/usuario/listaUsuarios";		
		
	}	
	
	@GetMapping("/listaUsuarios")
	public String mostrarIndex(Model model) {
		//Recuperar toda la lista de Usuarios
		List<Usuario> lista = usuarioService.buscarTodosUsuarios();
		
		//Renderizado con modelo 
		model.addAttribute("listaU", lista);
		
		
		return "usuario/listaUsuario";
		
	}

}
