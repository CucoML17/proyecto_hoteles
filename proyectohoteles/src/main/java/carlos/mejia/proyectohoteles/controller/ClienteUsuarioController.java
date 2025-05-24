package carlos.mejia.proyectohoteles.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Perfil;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.ClienteService;
import carlos.mejia.proyectohoteles.service.PerfilService;
import carlos.mejia.proyectohoteles.service.UsuarioService;

@Controller
@RequestMapping("/cliente/user")
public class ClienteUsuarioController {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/registrarUsuario/{idUsuario}")
	public String mostrarLista(@PathVariable("idUsuario") int idUsuario, Model model, Cliente cliente) {

		//Si es igual a cero significa que se trata un nuevo cliente/usuario
		Usuario usuario = new Usuario();
		if(idUsuario!=0) {
			usuario= usuarioService.buscarUsuarioID(idUsuario);
			
		}
		
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("accion","Registrando nuevo cliente");
		
		//Un flag act, este si act si vale 0 indica que es nuevo usuario/cliente
		//1 para edición
		model.addAttribute("act", 0);
		
		return "user/usuariouser/registrarUsuario";
		
	}
	
	//Para guardar el cliente con su nuevo usuario
	   @PostMapping("/guardar")
	    public String guardarEmpleado(Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                                   @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
	                                   @RequestParam(value = "iduser", required = false) Integer iduser,
	                                   @RequestParam(value = "username", required = false) String username,
	                                   @RequestParam(value = "password", required = false) String password,
	                                   @RequestParam(value = "act", required = false) Integer act) {

	        Usuario usuarioExistente = usuarioService.buscarPorUsername(username);
	        if (usuarioExistente != null && (iduser == null || !usuarioExistente.getId().equals(iduser))) {
	            model.addAttribute("errorUsername", "Nombre de usuario inválido, escoja otro por favor.");
	            Usuario usuarioModelo = new Usuario();
	            if (iduser != null) {
	                usuarioModelo = usuarioService.buscarUsuarioID(iduser);
	            }
	            usuarioModelo.setPassword(password);
	            model.addAttribute("usuario", usuarioModelo);
	            
	            String accionMensaje;
	            if (iduser != null) {
	                accionMensaje = "Editando cliente";
	            } else {
	                accionMensaje = "Registrando nuevo cliente";
	            }
	            model.addAttribute("accion", accionMensaje);
	            
	            model.addAttribute("act", act);
	            
	            return "user/usuariouser/registrarUsuario";
	        }		   
		   
	        if (result.hasErrors()) {
	            for (ObjectError error : result.getAllErrors()) {
	                System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	            }
	    		Usuario usuario = new Usuario();
	    		if(iduser!=null) {
	    			usuario= usuarioService.buscarUsuarioID(iduser);
	    			
	    		}
	    		
	    		model.addAttribute("usuario", usuario);
	    		
	    		model.addAttribute("accion","Registrando nuevo cliente");
	    		
	    		model.addAttribute("act", act);
	    		
	    		return "user/usuariouser/registrarUsuario";
	        }

	        if (imagenFile != null && !imagenFile.isEmpty()) {
	            try {
	                String nombreArchivo = imagenFile.getOriginalFilename();
	                Path rutaArchivo = Paths.get("/app/imagenesHotel/clientes").resolve(nombreArchivo).normalize();
	                Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
	                cliente.setFotoCliente(nombreArchivo);
	            } catch (IOException e) {
	                e.printStackTrace();
	                redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen del empleado.");
	                return "redirect:/"; // Redirige a la lista de empleados del hotel
	            }
	        } else {
	            if (cliente.getFotoCliente() != null && !cliente.getFotoCliente().isEmpty()) {
	                cliente.setFotoCliente(cliente.getFotoCliente().replace(",", "").trim());
	            } else {
	                if (cliente.getId() != null) {
	                    Cliente clienteExistente = clienteService.buscarClienteID(cliente.getId());
	                    if (clienteExistente != null) {
	                    	cliente.setFotoCliente(clienteExistente.getFotoCliente());
	                    }
	                }
	            }
	        }

	        //Colocando los datos que no pueden llegar del formulario
	        cliente.setEstado(1); // Establecer el estado por defecto
	        
	
	        //Crear su usuario
	        Usuario usuario = new Usuario();
	        int idUsuarioAsignar = 0;
	        
	        if(iduser!=null) {
	        	usuario = usuarioService.buscarUsuarioID(iduser);
	        	usuario.setUsername(username);
	        	//Solo actualizar la contraseña si es un nuevo registro
	        	if (act == 0) {
	        		String encodedPassword = passwordEncoder.encode(password);
	        		usuario.setPassword(encodedPassword);
	        	}
	        	usuarioService.guardarUsuario(usuario);
	        	idUsuarioAsignar = iduser;
	        	
	        }else {
	        	usuario = new Usuario();
	        	usuario.setUsername(username);
	        	String encodedPassword = passwordEncoder.encode(password);
	        	usuario.setPassword(encodedPassword);
	        	usuario.setEstatus(1);
	        	usuario.setFechaRegistro(new Date());
	        	//Su perfil
	        	Perfil perfil = perfilService.buscarPerfilID(1);
	        	usuario.agregarPerfilUsuario(perfil);
	        	idUsuarioAsignar = usuarioService.guardarUsuario(usuario);
	        	
	        }
	        

	        //Asignar su usuario
	        cliente.setIdClienteUser(usuarioService.buscarUsuarioID(idUsuarioAsignar));
	        
	        clienteService.guardarCliente(cliente);
	        
	        redirectAttributes.addFlashAttribute("mensajeExito", "Te has registrado con éxito");
	        return "redirect:/";
	    }	
	   
	   
	    @GetMapping("/verPerfil")
	    public String mostrarPerfil(Principal principal, Model model) {
	        String username = principal.getName(); //Obtiene el username del usuario logueado
	        Usuario usuarioLogueado = usuarioService.buscarPorUsername(username); // Busca el Usuario completo por su username

	        model.addAttribute("usuario", usuarioLogueado);
	        
	        Cliente cliente = usuarioLogueado.getClienteligado().get(0);
	        
	        model.addAttribute("cliente", cliente);
	        
	        return "user/usuariouser/verPerfil";
	    }	   
	    	
	    
	    @GetMapping("/editarPerfil")
		public String editarEmpleado(Principal principal, Model model) {

			String username = principal.getName();
			Usuario usuarioLogueado = usuarioService.buscarPorUsername(username);


			model.addAttribute("usuario", usuarioLogueado);

			Cliente cliente = usuarioLogueado.getClienteligado().get(0);

			model.addAttribute("cliente", cliente);

			model.addAttribute("accion", "Estás editando tu perfil " + cliente.getNombre());

			//El act en 1 para indicar que es una edición
			model.addAttribute("act", 1);

			return "user/usuariouser/registrarUsuario";
		}    

}
