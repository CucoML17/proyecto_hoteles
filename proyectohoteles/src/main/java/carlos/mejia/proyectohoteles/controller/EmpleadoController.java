package carlos.mejia.proyectohoteles.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Perfil;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.EmpleadoService;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.PerfilService;
import carlos.mejia.proyectohoteles.service.RolService;
import carlos.mejia.proyectohoteles.service.UsuarioService;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Autowired
	PerfilService perfilService;	
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;	
	
	@GetMapping("/admin/listaEmpleados")
	public String mostrarLista(Model model) {
		//Para los empleados 
		List<Empleado> lista = empleadoService.todosEmpleados();
		model.addAttribute("listaEmpleados", lista);
		
		List<Rol> listaRoles = rolService.todosRoles();
		model.addAttribute("listaRoles", listaRoles);
		
		
		return "admin/empleado/panelAdminEmpleado";
		
	}		
	
	
	@GetMapping("/admin/selecHotelEmpleado")
	public String seleccionarHotelHabitacion(Model model) {
		
	
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		return "admin/empleado/selectHotelEmpleado";		
		
	}	
	
	
	//Filtrar seleccion
	@PostMapping("/admin/buscarSelecHotel")
	public String buscarSeleccionarHotel(
	        @RequestParam(value = "tipoBusca", required = false) String tipoBusca,
	        @RequestParam(value = "valorBusca", required = false) String valorBusca,
	        @RequestParam(value = "orden", required = false) String orden,
	        @RequestParam(value = "min", required = false) double min,
	        @RequestParam(value = "max", required = false) double max,
	        Model model
	) {
	    System.out.println("Tipo de búsqueda: " + tipoBusca);
	    System.out.println("Valor de búsqueda: " + valorBusca);
	    System.out.println("Orden: " + orden);
	    System.out.println("Precio mínimo: " + min);
	    System.out.println("Precio máximo: " + max);
	    
	    List<Hotel> lista = hotelService.todosHoteles();
	    if(tipoBusca.equalsIgnoreCase("id")) {
	    	System.out.println("Filtrado por ID");
	        try {
	            int idBusca = Integer.parseInt(valorBusca);
	            Hotel hotel = hotelService.buscarHotelID(idBusca);
	            lista.clear(); //Limpiamos la lista inicial
	            if (hotel != null) {
	                lista.add(hotel); //Agregamos el hotel encontrado
	            } else {
	                model.addAttribute("mensajeError", "No se encontró ningún hotel con la clave: " + valorBusca);
	                lista.clear(); //Aseguramos que la lista esté vacía si no se encuentra el hotel
	            }
	        } catch (NumberFormatException e) {
	            model.addAttribute("mensajeError", "Por favor, ingrese un valor numérico para la clave.");
	            lista.clear(); //Aseguramos que la lista esté vacía si hay un error de formato
	        }
	    	
	    }else {
	    	
	    	if(tipoBusca.equalsIgnoreCase("precio")) {
	    		System.out.println("Filtrado por precio");
	    		lista = hotelService.filtrarHotelPrecioPromedio(min, max, orden);
	    	}else {
	    		System.out.println("Filtrado por no precio");
	    		lista = hotelService.filtrarHotelPor(tipoBusca, orden, valorBusca);
	    	}
	    	
	    	
	    }

	    
	    model.addAttribute("listaHoteles", lista);

	    return "admin/empleado/selectHotelEmpleado"; // Volvemos a la página con los resultados filtrados
	}	
	
	
	@GetMapping("/admin/agregarEmpleado/{idHotel}")
	public String agregarEmpleado(@PathVariable("idHotel") int idHotel, Model model, Empleado empleado) {
		
		//La acción y a qué hotel se le está agreganda:
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		model.addAttribute("accion","Agrando un empleado a " + hotel.getNombre());
		
		//Para los datos del hotel:
		model.addAttribute("hotel", hotel);
		
		
		//Para indicar que es guardado
		model.addAttribute("act", 0);
		
		List<Rol> listaRoles = rolService.todosRoles();
		
		model.addAttribute("listaRoles", listaRoles);
		
		//Para el usuario, como es nuevo, entonces va vacío
		Usuario usuario  = new Usuario();
		model.addAttribute("usuario", usuario);
		
		
		return "admin/empleado/formEmpleado";
	}	
	
	//Para guardar el empleado:
    @PostMapping("/admin/guardarEmpleado")
    public String guardarEmpleado(Empleado empleado, BindingResult result, Model model, RedirectAttributes redirectAttributes,
                                   @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
                                   @RequestParam(value = "hotelAsignado", required = false) Integer hotelAsignado,
                                   @RequestParam(value = "rolesSeleccionados", required = false) List<Integer> rolesSeleccionados,
                                   @RequestParam(value = "act", required = false) Integer act,
                                   @RequestParam(value = "iduser", required = false) Integer iduser,
                                   @RequestParam(value = "username", required = false) String username,
                                   @RequestParam(value = "password", required = false) String password) {

        //Validar no usuarios repetidos:
        Usuario usuarioExistente = usuarioService.buscarPorUsername(username);
        if (usuarioExistente != null && (iduser == null || !usuarioExistente.getId().equals(iduser))) {
            model.addAttribute("errorUsername", "Nombre de usuario inválido, escoja otro por favor.");
            Usuario usuarioModelo = new Usuario();
            if (iduser != null) {
                usuarioModelo = usuarioService.buscarUsuarioID(iduser);
            }
            usuarioModelo.setPassword(password);
            model.addAttribute("usuario", usuarioModelo);

            Hotel hotel = hotelService.buscarHotelID(hotelAsignado);
            model.addAttribute("accion","Agrando un empleado a " + hotel.getNombre());

            String accionMensaje;
            if (iduser != null) {
                accionMensaje = "Editando empleado";
            } else {
                accionMensaje = "Agrando un empleado a " + hotel.getNombre();

            }
            model.addAttribute("accion", accionMensaje);


            model.addAttribute("hotel", hotel);


            //Para indicar que es guardado o editado
            model.addAttribute("act", act);

            List<Rol> listaRoles = rolService.todosRoles();

            model.addAttribute("listaRoles", listaRoles);


            return "admin/empleado/formEmpleado";
        }


        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Ocurrio un error: " + error.getDefaultMessage());
            }
            Hotel hotel = hotelService.buscarHotelID(hotelAsignado);
            model.addAttribute("accion", "Agregando un empleado a " + hotel.getNombre());
            model.addAttribute("hotel", hotel);
            model.addAttribute("listaRoles", rolService.todosRoles());
            model.addAttribute("act", act);
            return "admin/empleado/formEmpleado";
        }

        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String nombreArchivo = imagenFile.getOriginalFilename();
                Path rutaArchivo = Paths.get("/app/imagenesHotel/empleados").resolve(nombreArchivo).normalize();
                Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
                empleado.setFotoEmpleado(nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen del empleado.");
                return "redirect:/empleado/admin/listaEmpleados/" + hotelAsignado; // Redirige a la lista de empleados del hotel
            }
        } else {
            if (empleado.getFotoEmpleado() != null && !empleado.getFotoEmpleado().isEmpty()) {
                empleado.setFotoEmpleado(empleado.getFotoEmpleado().replace(",", "").trim());
            } else {
                if (empleado.getId() != null) {
                    Empleado empleadoExistente = empleadoService.buscarEmpleadoID(empleado.getId());
                    if (empleadoExistente != null) {
                        empleado.setFotoEmpleado(empleadoExistente.getFotoEmpleado());
                    }
                }
            }
        }

        empleado.setEstado(1); // Establecer el estado por defecto
        Hotel hotelAsignadoObj = hotelService.buscarHotelID(hotelAsignado);
        empleado.setIdHotelEmpleado(hotelAsignadoObj);

        // Asignar los roles seleccionados al empleado
        if (rolesSeleccionados != null && !rolesSeleccionados.isEmpty()) {
            empleado.getRoles().clear();
            for (Integer rolId : rolesSeleccionados) {
                Rol rol = rolService.buscarRolID(rolId);
                if (rol != null) {
                    empleado.agregarRolEmpleado(rol);
                }
            }
        }

        empleadoService.guardarEmpleado(empleado);

        //Guardar el usuario
        Usuario usuario;
        if (iduser != null) {
            usuario = usuarioService.buscarUsuarioID(iduser);
            usuario.setUsername(username);
            
            if (act == 0) {
                String encodedPassword = passwordEncoder.encode(password);
                usuario.setPassword(encodedPassword);
            }
            usuario.getPerfiles().clear(); // Limpiar perfiles existentes
        } else {
            usuario = new Usuario();
            usuario.setUsername(username);
            String encodedPassword = passwordEncoder.encode(password);
            usuario.setPassword(encodedPassword);
            usuario.setEstatus(1);
            usuario.setFechaRegistro(new Date());
        }

        // Asignar perfiles basados en los roles seleccionados
        if (rolesSeleccionados != null && !rolesSeleccionados.isEmpty()) {
            for (Integer rolId : rolesSeleccionados) {
                Perfil perfilAsignar = null;
                if (rolId == 1) { // Recepcionista
                    perfilAsignar = perfilService.buscarPerfilID(2); // Perfil Recepcionista
                } else if (rolId == 2 || rolId == 3) { // Mantenimiento o Limpieza
                    perfilAsignar = perfilService.buscarPerfilID(4); // Perfil Mantenimiento
                } else if (rolId == 4) { // Administrador
                    perfilAsignar = perfilService.buscarPerfilID(3); // Perfil Administrador
                }
                if (perfilAsignar != null && !usuario.getPerfiles().contains(perfilAsignar)) {
                    usuario.agregarPerfilUsuario(perfilAsignar);
                }
            }
            
            if (usuario.getPerfiles().isEmpty()) {
               
            }
        }

        usuarioService.guardarUsuario(usuario);

        // Asignar el usuario al empleado
        empleado.setIdEmpleadoUser(usuario);
        empleadoService.guardarEmpleado(empleado); //Guardar nuevamente para persistir la relación con el usuario

        redirectAttributes.addFlashAttribute("mensajeExito", "Empleado guardado con éxito");
        return "redirect:/empleado/admin/listaEmpleados";
    }   
    
    
    //Para editar
    @GetMapping("/admin/editar/{idHotel}/{idEmpleado}")
    public String editarEmpleado(@PathVariable("idHotel") int idHotel,
                                 @PathVariable("idEmpleado") int idEmpleado, Model model, RedirectAttributes redirectAttributes) {

        //El empleado a editar
        Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);
        model.addAttribute("empleado", empleado);

        //Los roles actuales del empleado
        model.addAttribute("rolesEmpleado", empleado.getRoles());

        //Obtener el usuario asociado al empleado
        Usuario usuario = empleado.getIdEmpleadoUser();
        if (usuario != null) {
            //Quitar el {noop} de la contraseña para mostrar en el formulario
            String passwordNoop = usuario.getPassword();
            if (passwordNoop.startsWith("{noop}")) {
                passwordNoop = passwordNoop.substring(6);
                usuario.setPassword(passwordNoop);
            }
            model.addAttribute("usuario", usuario);
        } else {
            // Manejar el caso en que el empleado no tenga un usuario asociado (opcional)
            model.addAttribute("usuario", new Usuario()); // O podrías redirigir con un error
        }

        //La acción y a qué hotel se le está agregando:
        Hotel hotel = hotelService.buscarHotelID(idHotel);
        model.addAttribute("accion","Editando empleado del " + hotel.getNombre());

        //Para los datos del hotel:
        model.addAttribute("hotel", hotel);

        //Para indicar que es editado
        model.addAttribute("act", 1);

        List<Rol> listaRoles = rolService.todosRoles();
        model.addAttribute("listaRoles", listaRoles);

        return "admin/empleado/formEmpleado";
    }
    
    //Cambiar de hotel
	@GetMapping("/admin/cambiarHotel/{act}/{empleadoC}")
	public String cambiarHotel(@PathVariable("act") int act,
            @PathVariable("empleadoC") int empleadoC, 
            Model model) {
		
		/*List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);*/		
		/*
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);*/
		
		
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		
		if(act==1) {
			
			Empleado empleado = empleadoService.buscarEmpleadoID(empleadoC);
			model.addAttribute("empleado", empleado);
			
			return "admin/empleado/cambiarHotel";
			
		}else {
			
			return "admin/empleado/selectHotelEmpleado";	
		}
		
				
		
	}    
	
	//Para mostrar el form de edicion pero con el nuevo hotel
    @GetMapping("/admin/agregarEmpleadoNuevoHotel/{idHotel}/{idEmpleado}")
    public String editarEmpleadoHotelCambiado(@PathVariable("idHotel") int idHotel,
                                 @PathVariable("idEmpleado") int idEmpleado, Model model, RedirectAttributes redirectAttributes) {

        //El empleado a editar
        Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);
        model.addAttribute("empleado", empleado);

        //Los roles actuales del empleado
        model.addAttribute("rolesEmpleado", empleado.getRoles()); //
        
        //EL USUARIO CUIDADO:
        
        
        //Fin del usuario
        
        Usuario usuario = empleado.getIdEmpleadoUser();
        if (usuario != null) {
            //Quitar el {noop} de la contraseña para mostrar en el formulario
            String passwordNoop = usuario.getPassword();
            if (passwordNoop.startsWith("{noop}")) {
                passwordNoop = passwordNoop.substring(6);
                usuario.setPassword(passwordNoop);
            }
            model.addAttribute("usuario", usuario);
        } else {
            // Manejar el caso en que el empleado no tenga un usuario asociado (opcional)
            model.addAttribute("usuario", new Usuario()); // O podrías redirigir con un error
        }
        

        //La acción y a qué hotel se le está agregando:
        Hotel hotel = hotelService.buscarHotelID(idHotel);
        model.addAttribute("accion","Editando empleado del " + hotel.getNombre());

        //Para los datos del hotel:
        model.addAttribute("hotel", hotel);

        //Para indicar que es editado
        model.addAttribute("act", 1);

        List<Rol> listaRoles = rolService.todosRoles();
        model.addAttribute("listaRoles", listaRoles);
        
        model.addAttribute("cambioHotel", "Empleado cambiado de hotel con éxito");

        return "admin/empleado/formEmpleado";
    } 	
    
    
	//Para ver más
	@GetMapping("/admin/verMas/{id}")
	public String verDetalle(@PathVariable("id") int idEmpleado, Model model) {
		
		Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);
	
		model.addAttribute("empleado", empleado);
		//model.addAttribute("listaHabitaciones", hotel.getHabitaciones());
		
		
		return "admin/empleado/detalleEmpleado";
	}    

	//Cambiar de estado
	@GetMapping("/admin/cambiarEstado/{id}")
	public String cambiarEstado(@PathVariable("id") int idEmpleado, RedirectAttributes redirectAttributes) {

		Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);

		int estadoEmpleado = empleado.getEstado();
		int nuevoEstadoEmpleado;

		if (estadoEmpleado == 1) {
			nuevoEstadoEmpleado = 0;
		} else {
			nuevoEstadoEmpleado = 1;
		}

		empleado.setEstado(nuevoEstadoEmpleado);
		empleadoService.guardarEmpleado(empleado);

		Usuario usuarioEmpleado = empleado.getIdEmpleadoUser();
		if (usuarioEmpleado != null) {
			System.out.println("MUAAAAA");
			usuarioEmpleado.setEstatus(nuevoEstadoEmpleado);
			usuarioService.guardarUsuario(usuarioEmpleado);
		}

		redirectAttributes.addFlashAttribute("mensajeExito", "" + empleado.getNombre() + " ha cambiado de estado.");

		return "redirect:/empleado/admin/listaEmpleados";
	}
	
	
	//Filtros cambio de hotel
	@PostMapping("/admin/buscarHotelNuevo/{idEmpleado}")
	public String buscarSeleccionarCambioHotel(
			@PathVariable("idEmpleado") int idEmpleado,
	        @RequestParam(value = "tipoBusca", required = false) String tipoBusca,
	        @RequestParam(value = "valorBusca", required = false) String valorBusca,
	        @RequestParam(value = "orden", required = false) String orden,
	        @RequestParam(value = "min", required = false) double min,
	        @RequestParam(value = "max", required = false) double max,
	        Model model
	) {
	    System.out.println("Tipo de búsqueda: " + tipoBusca);
	    System.out.println("Valor de búsqueda: " + valorBusca);
	    System.out.println("Orden: " + orden);
	    System.out.println("Precio mínimo: " + min);
	    System.out.println("Precio máximo: " + max);
	    
	    List<Hotel> lista = hotelService.todosHoteles();
	    if(tipoBusca.equalsIgnoreCase("id")) {
	    	System.out.println("Filtrado por ID");
	        try {
	            int idBusca = Integer.parseInt(valorBusca);
	            Hotel hotel = hotelService.buscarHotelID(idBusca);
	            lista.clear(); //Limpiamos la lista inicial
	            if (hotel != null) {
	                lista.add(hotel); //Agregamos el hotel encontrado
	            } else {
	                model.addAttribute("mensajeError", "No se encontró ningún hotel con la clave: " + valorBusca);
	                lista.clear(); //Aseguramos que la lista esté vacía si no se encuentra el hotel
	            }
	        } catch (NumberFormatException e) {
	            model.addAttribute("mensajeError", "Por favor, ingrese un valor numérico para la clave.");
	            lista.clear(); //Aseguramos que la lista esté vacía si hay un error de formato
	        }
	    	
	    }else {
	    	
	    	if(tipoBusca.equalsIgnoreCase("precio")) {
	    		System.out.println("Filtrado por precio");
	    		lista = hotelService.filtrarHotelPrecioPromedio(min, max, orden);
	    	}else {
	    		System.out.println("Filtrado por no precio");
	    		lista = hotelService.filtrarHotelPor(tipoBusca, orden, valorBusca);
	    	}
	    	
	    	
	    }

	    
	    model.addAttribute("listaHoteles", lista);
	    
	    Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);
		model.addAttribute("empleado", empleado);	    

		return "admin/empleado/cambiarHotel"; // Volvemos a la página con los resultados filtrados
	}		
	
	//Filtros empleado
	@PostMapping("/admin/buscar")
    public String buscarEmpleados(
            @RequestParam(value = "filtrarPorHotel", required = false) String filtrarPorHotel,
            @RequestParam(value = "idHotelEmpleado", required = false) Integer idHotelEmpleado,
            @RequestParam(value = "campoFiltro", required = false) String campoFiltro,
            @RequestParam(value = "activarFiltroTexto", required = false) String activarFiltroTexto,
            @RequestParam(value = "valorFiltroTexto", required = false) String valorFiltroTexto,
            @RequestParam(value = "rolId", required = false) Integer rolId,
            Model model
    ) {
        System.out.println("Filtrar por hotel: " + filtrarPorHotel);
        System.out.println("ID del hotel: " + idHotelEmpleado);
        System.out.println("Campo de filtro: " + campoFiltro);
        System.out.println("Activar filtro texto: " + activarFiltroTexto);
        System.out.println("Valor del filtro texto: " + valorFiltroTexto);
        System.out.println("ID del rol: " + rolId);

        List<Empleado> listaEmpleados = empleadoService.filtrarEmpleados(
                (filtrarPorHotel != null && filtrarPorHotel.equals("on")) ? idHotelEmpleado : null,
                (activarFiltroTexto != null && activarFiltroTexto.equals("on")) ? campoFiltro : null,
                (activarFiltroTexto != null && activarFiltroTexto.equals("on")) ? valorFiltroTexto : null,
                rolId
        );

        model.addAttribute("listaEmpleados", listaEmpleados);
        model.addAttribute("listaRoles", rolService.todosRoles()); // Para el desplegable de filtro de puesto
        
        String mensajeFiltro = "Se filtró por: ";
        boolean seFiltro = false;

        if (filtrarPorHotel != null && filtrarPorHotel.equals("on") && idHotelEmpleado != null) {
            mensajeFiltro += "Hotel ID: " + idHotelEmpleado + ", ";
            seFiltro = true;
        }

        if (activarFiltroTexto != null && activarFiltroTexto.equals("on") && campoFiltro != null && !campoFiltro.isEmpty() && valorFiltroTexto != null && !valorFiltroTexto.isEmpty()) {
            String campoMostrar = "";
            switch (campoFiltro.toLowerCase()) {
                case "rfc":
                    campoMostrar = "RFC";
                    break;
                case "nombre":
                    campoMostrar = "Nombre";
                    break;
                case "apellido":
                    campoMostrar = "Apellido";
                    break;
                case "telefono":
                    campoMostrar = "Teléfono";
                    break;
            }
            mensajeFiltro += campoMostrar + ": " + valorFiltroTexto + ", ";
            seFiltro = true;
        }

        if (rolId != null) {
            Rol rol = rolService.buscarRolID(rolId);
            if (rol != null) {
                mensajeFiltro += "Puesto: " + rol.getTipo() + ", ";
                seFiltro = true;
            } else {
                mensajeFiltro += "Puesto ID: " + rolId + " (no encontrado), ";
                seFiltro = true;
            }
        }

        if (!seFiltro) {
            model.addAttribute("mensajeFiltro", "Mostrando todos los empleados.");
        } else {
            // Eliminar la coma y el espacio final si se agregaron filtros
            if (mensajeFiltro.endsWith(", ")) {
                mensajeFiltro = mensajeFiltro.substring(0, mensajeFiltro.length() - 2);
            }
            model.addAttribute("mensajeFiltro", mensajeFiltro);
        }

        
        
        return "admin/empleado/panelAdminEmpleado";
    }	
	
	
}
