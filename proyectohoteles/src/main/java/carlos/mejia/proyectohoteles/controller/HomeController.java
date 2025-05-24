package carlos.mejia.proyectohoteles.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Perfil;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.PerfilService;
import carlos.mejia.proyectohoteles.service.RolService;
import carlos.mejia.proyectohoteles.service.UsuarioService;




@Controller
public class HomeController {
	@Autowired
	HotelService hotelService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String menuPrinci(@RequestParam(value = "errorAcceso", required = false) String errorAcceso, 
			Model model) {
		
	    if (errorAcceso != null) {
	        model.addAttribute("mensajeAccesoDenegado", "Acceso denegado. No tiene los permisos necesarios.");
	    }			

        Hotel hotelMasEconomico = hotelService.buscarHotelMasEconomico();
        List<Hotel> otrosHotelesEconomicos = hotelService.buscarOtrosHotelesEconomicos();

        model.addAttribute("hotelMasEconomico", hotelMasEconomico);
        model.addAttribute("otrosHotelesEconomicos", otrosHotelesEconomicos);
        
        
        //Provicional
       /* Usuario usuario = usuarioService.buscarUsuarioID(7);
        
        String encodedPassword = passwordEncoder.encode("123");
        usuario.setPassword(encodedPassword);
        
        usuarioService.guardarUsuario(usuario);
		*/
        
        
		if(usuarioService.todosUsuarios().size()==0) {
			//Crear los Perfiles
			Perfil p1 = new Perfil();
			p1.setPerfil("Cliente");
			perfilService.guardarPerfil(p1);
			
			Perfil p2 = new Perfil();
			p2.setPerfil("Recepcionista");
			perfilService.guardarPerfil(p2);
			
			Perfil p3 = new Perfil();
			p3.setPerfil("Administrador");
			perfilService.guardarPerfil(p3);
			
			Perfil p4 = new Perfil();
			p4.setPerfil("Mantenimiento");
			perfilService.guardarPerfil(p4);
			
			
			Rol rol1 = new Rol();
			rol1.setTipo("Recepcionista");
			rolService.guardarRol(rol1);
			
			Rol rol2 = new Rol();
			rol2.setTipo("Mantenimiento");
			rolService.guardarRol(rol2);
			
			Rol rol3 = new Rol();
			rol3.setTipo("Limpieza");
			rolService.guardarRol(rol3);
			
			Rol rol4 = new Rol();
			rol4.setTipo("Administrador");
			rolService.guardarRol(rol4);
			
			
			Usuario userJefe = new Usuario();
			userJefe.setUsername("cuco");
			userJefe.setEstatus(1);
			userJefe.setFechaRegistro(new Date());
		    
		    //Guardar la contraseña encriptada
		    String passwordEncriptado = passwordEncoder.encode("123");
		    userJefe.setPassword(passwordEncriptado);	    
		    
		    //Para el perfil de CONSULTA
			Perfil perUsuario = new Perfil();
			perUsuario = perfilService.buscarPerfilID(3);
			
			
			userJefe.agregarPerfilUsuario(perUsuario);	    

		    usuarioService.guardarUsuario(userJefe);
		    System.out.println("Patrón creado");			
			
			
			
		}        
        
        
        
		return "user/home";
	}	
	
	@GetMapping("/sobreNosotros")
	public String sobreNosotros(Model model) {
		
		return "user/sobreNosotros";
	}
	

}
