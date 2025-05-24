package carlos.mejia.proyectohoteles.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import carlos.mejia.proyectohoteles.model.DetalleMantenimiento;
import carlos.mejia.proyectohoteles.model.DetalleMantenimientoId;
import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Mantenimiento;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.service.DetalleMantenimientoService;
import carlos.mejia.proyectohoteles.service.EmpleadoService;
import carlos.mejia.proyectohoteles.service.HabitacionService;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.MantenimientoService;
import carlos.mejia.proyectohoteles.service.ReservaService;
import carlos.mejia.proyectohoteles.service.RolService;


@Controller
@RequestMapping("/mantenimiento")
public class MantenimientoController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HabitacionService habitacionService;
	
	@Autowired
	private MantenimientoService mantenimientoService;	
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private DetalleMantenimientoService detalleMantenimientoService;
	
	@Autowired
	private ReservaService reservaService;
	
	
	
	
	
	@GetMapping("/admin/listaMantenimiento")
	public String mostrarLista(Model model) {
		//Para los hoteles 
		List<Mantenimiento> lista = mantenimientoService.todosMantes();
		model.addAttribute("listaMantenimientos", lista);
		
		//Para su lista de teléfonos:
		
		
		
		return "admin/mantenimiento/panelAdminMante";
		
	}		

	
	//Seleccionar un hotel
	@GetMapping("/admin/selecHotel/{idMante}")
	public String seleccionarHotelHabitacion(@PathVariable("idMante") int idMante, Model model) {
		
		/*List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);*/		
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		model.addAttribute("idMante", idMante);
		
		return "admin/mantenimiento/selecHotelMante";		
		
	}		
	
	
	//Buscador de hoteles
	@PostMapping("/admin/buscarSelecHotel")
	public String buscarSeleccionarHotel(
			@RequestParam(value = "idMante", required = false) int idMante,
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
	    
	    model.addAttribute("idMante", idMante);
	    
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

	    return "admin/mantenimiento/selecHotelMante"; 
	}		

	
	
	//Seleccionar una habitación
	@GetMapping("/admin/selecHabitacion/{idHotel}/{idMante}")
	public String mostrarListaHabitaciones(@PathVariable("idHotel") int idHotel,
			@PathVariable("idMante") int idMante,
			Model model) {
		//El hotel específico
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		//El Título
		model.addAttribute("tituloLista", "Seleccionar habitación de " + hotel.getNombre() + " para programar el mantenimiento");
		
		//El listado de todas las habitaciones
		List<Habitacion> lista = hotel.getHabitaciones();
		
		model.addAttribute("listaHabitaciones", lista);
		
		//Para el hotel como tal:
		model.addAttribute("hotel", hotel);
		
		
		model.addAttribute("idMante", idMante);
		
		
		return "admin/mantenimiento/selecHabiMante";		
		
		
	}	
	
	
	
	
	
	//Filtrar habitaciones
	@PostMapping("/admin/buscarListaHabitaciones")
	public String buscarHabitaciones(
	        @RequestParam("idHotelPertenece") int idHotel, // Recibimos el idHotel
	        @RequestParam("idMante") int idMante,
	        @RequestParam(value = "numeroHabitacion", required = false) String numeroHabitacion, // Cambiado a String para el servicio
	        @RequestParam(value = "tipoHabitacion", required = false) String tipoHabitacion,
	        @RequestParam(value = "filtrarPrecio", required = false) String filtrarPrecio,
	        @RequestParam(value = "minPrecio", required = false) Double minPrecio,
	        @RequestParam(value = "maxPrecio", required = false) Double maxPrecio,
	        @RequestParam(value = "capacidad", required = false) Integer capacidad,
	        @RequestParam(value = "ordenCampo", required = false) String ordenCampo,
	        @RequestParam(value = "ordenDireccion", required = false) String ordenDireccion,
	        Model model
	) {
	
	    System.out.println("ID Hotel Perteneciente: " + idHotel);
	    System.out.println("Número de habitación: " + numeroHabitacion);
	    System.out.println("Tipo de habitación: " + tipoHabitacion);
	    System.out.println("Filtrar precio: " + filtrarPrecio);
	    System.out.println("Precio mínimo: " + minPrecio);
	    System.out.println("Precio máximo: " + maxPrecio);
	    System.out.println("Capacidad: " + capacidad);
	    System.out.println("Ordenar por campo: " + ordenCampo);
	    System.out.println("Ordenar dirección: " + ordenDireccion);

	    model.addAttribute("idMante", idMante);
	    
	    
	    if(numeroHabitacion.isEmpty()) {
	    	numeroHabitacion=null;
	    	
	    }
	    if(tipoHabitacion.isEmpty()) {
	    	tipoHabitacion=null;
	    	
	    }	    
	    
	    if(ordenCampo.isEmpty()) {
	    	ordenCampo="numeroHabitacion";
	    	
	    }	 	    
	    
	    List<Habitacion> listaHabitaciones = habitacionService.filtrarHabitaciones(
	            idHotel, numeroHabitacion, tipoHabitacion, minPrecio, maxPrecio, capacidad, ordenCampo, ordenDireccion
	    );

	    Hotel hotel = hotelService.buscarHotelID(idHotel); // Para mostrar el nombre del hotel en el título
	    model.addAttribute("listaHabitaciones", listaHabitaciones);
	    model.addAttribute("tituloLista", "Lista de habitaciones de " + hotel.getNombre());

	    
	    model.addAttribute("hotel", hotel);
	    
	    String mensaje = "";
	    
	    
	    
        String mensajeFiltro = "Se filtró por: ";
        boolean seFiltro = false;
        
        

        if (numeroHabitacion != null) {
            mensajeFiltro += "Número de habitación: " + numeroHabitacion + ", ";
            seFiltro = true;
        }
        if (tipoHabitacion != null) {
            mensajeFiltro += "Tipo de habitación: " + tipoHabitacion + ", ";
            seFiltro = true;
        }
        if (filtrarPrecio != null && filtrarPrecio.equals("on")) {
            mensajeFiltro += "Precio entre $" + minPrecio + " y $" + maxPrecio + ", ";
            seFiltro = true;
        }
        if (capacidad != null) {
            mensajeFiltro += "Capacidad: " + capacidad + " personas, ";
            seFiltro = true;
        }
        
        
        mensajeFiltro += "Ordenado por: ";
        
	    if (ordenCampo.equals("numeroHabitacion")) {
	        mensajeFiltro+= "Número de habitación";
	    } else if (ordenCampo.equals("tipoHabitacion")) {
	    	 mensajeFiltro+= "Tipo de habitación";
	    } else if (ordenCampo.equals("precioNoche")) {
	    	 mensajeFiltro+= "Precio";
	    } else if (ordenCampo.equals("capacidad")) {
	    	 mensajeFiltro+= "Capacidad";
	    } else {
	    	 mensajeFiltro+= ordenCampo; // Si hay algún otro criterio inesperado
	    }        
        
        
        
        
        if (ordenDireccion != null && ordenDireccion.equalsIgnoreCase("desc")) {
            mensajeFiltro += " (Descendente)";
        } else {
            mensajeFiltro += " (Ascendente)";
        }

        if (!seFiltro) {
            model.addAttribute("mensajeFiltro", "Mostrando todas las habitaciones.");
        } else {
            // Eliminar la coma y el espacio final
            if (mensajeFiltro.endsWith(", ")) {
                mensajeFiltro = mensajeFiltro.substring(0, mensajeFiltro.length() - 2);
            }
            model.addAttribute("mensajeFiltro", mensajeFiltro);
        }
	    
	    
	    
	    
	    return "admin/mantenimiento/selecHabiMante";
	}	
	
	//Abrir el form
	@GetMapping("/admin/abrirFormMantenimiento/{idHabitacion}/{idMante}")
	public String abrirFormHabitacion(@PathVariable("idHabitacion") int idHabitacion, 
			@PathVariable("idMante") int idMante,Model model, Mantenimiento mantenimiento) {
		
		if(idMante!=0) {
			mantenimiento = mantenimientoService.buscarMantenimientoID(idMante);
			
			model.addAttribute("idMante", idMante);
			model.addAttribute("mantenimiento", mantenimiento);
		}else{
			model.addAttribute("idMante", 0);

		}
		
		
		//La acción y a qué hotel se le está agreganda:
		Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("habitacion", habitacion);
		
		
		model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
		
		
		
		//Para indicar que es guardado
		model.addAttribute("act", 0);
		
		return "admin/mantenimiento/formMantenimiento";
	}
	
	
	//Guardar mantenimiento
	@PostMapping("/admin/guardarMantenimiento")
	public String guardar(Mantenimiento mantenimiento, BindingResult result, Model model, RedirectAttributes redirectAttributes, @RequestParam("idMante") int idMante,	                     
	                      @RequestParam(value = "hotelAsignado", required = false) Integer hotelAsignado,
	                      @RequestParam(value = "habitacionAsignada", required = false) Integer habitacionAsignada,
	                      @RequestParam(value = "act", required = false) Integer act) {	

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionAsignada);
			Hotel hotel = habitacion.getIdHotelHabitacion();
			
			model.addAttribute("hotel", hotel);
			model.addAttribute("habitacion", habitacion);
			
			
			model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
			

			
			//Para indicar que es guardado
			model.addAttribute("act", 0);

			if(idMante!=0) {
				model.addAttribute("idMante", idMante);
			}else{
				model.addAttribute("idMante", 0);

			}			
			
			return "admin/mantenimiento/formMantenimiento";
		}

		//Revisar que no haya reservas que choquen en dicha fecha
		System.out.println("Fecha mante: " + mantenimiento.getFecha() + " Id habitacion: "+ habitacionAsignada);
		System.out.println(reservaService.todasReservasFechaMante(habitacionAsignada, mantenimiento.getFecha()).size());
		
		Date fechaMantenimiento = mantenimiento.getFecha();
		Date hoy = new Date();

		//Quitar la hora para comparar solo las fechas
		Calendar calHoy = Calendar.getInstance();
		calHoy.setTime(hoy);
		calHoy.set(Calendar.HOUR_OF_DAY, 0);
		calHoy.set(Calendar.MINUTE, 0);
		calHoy.set(Calendar.SECOND, 0);
		calHoy.set(Calendar.MILLISECOND, 0);
		Date hoyTruncado = calHoy.getTime();

		Calendar calMantenimiento = Calendar.getInstance();
		calMantenimiento.setTime(fechaMantenimiento);
		calMantenimiento.set(Calendar.HOUR_OF_DAY, 0);
		calMantenimiento.set(Calendar.MINUTE, 0);
		calMantenimiento.set(Calendar.SECOND, 0);
		calMantenimiento.set(Calendar.MILLISECOND, 0);
		Date fechaMantenimientoTruncada = calMantenimiento.getTime();

		if (fechaMantenimientoTruncada.before(hoyTruncado)) {
			Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionAsignada);
			Hotel hotel = habitacion.getIdHotelHabitacion();

			model.addAttribute("hotel", hotel);
			model.addAttribute("habitacion", habitacion);
			model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
			
			model.addAttribute("act", 0);
			model.addAttribute("errorFecha", "La fecha del mantenimiento no puede ser anterior al día actual.");
			if(idMante!=0) {
				
				model.addAttribute("idMante", idMante);
			}else{
				model.addAttribute("idMante", 0);

			}			
			return "admin/mantenimiento/formMantenimiento";
		}		
		
		
		if(reservaService.tieneReservasEnFecha(habitacionAsignada, mantenimiento.getFecha())) {
			Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionAsignada);
			Hotel hotel = habitacion.getIdHotelHabitacion();
			
			model.addAttribute("hotel", hotel);
			model.addAttribute("habitacion", habitacion);
			
			
			model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
			

			
			//Para indicar que es guardado
			model.addAttribute("act", 0);
			
			model.addAttribute("errorFecha", "Lo sentimos, ya hay una reservación en esa fecha, elegir otra por favor.");
			
			if(idMante!=0) {
				model.addAttribute("idMante", idMante);
			}else{
				model.addAttribute("idMante", 0);

			}

			return "admin/mantenimiento/formMantenimiento";			
			
			
		}


		mantenimiento.setEstado("Programada");
		Habitacion habitacionasignada = habitacionService.buscarHabitacionID(habitacionAsignada);
		mantenimiento.setIdMantenimientoHabitacion(habitacionasignada);
		
		int idManteG = mantenimientoService.guardarMantenimiento(mantenimiento);

		
		Mantenimiento mantenimientoG = mantenimientoService.buscarMantenimientoID(idManteG);
		Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionAsignada);
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
	    //Los modelos		
		model.addAttribute("mantenimiento", mantenimientoG);
		
		model.addAttribute("hotel", hotel);
		
		model.addAttribute("habitacion", habitacion);
		
		
		
	    //redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento programado con éxito");
	    
	    List<Empleado> listaEmpledos = empleadoService.filtrarEmpleadosEstado(hotelAsignado, null, null, null, 1);
	    System.out.println(listaEmpledos.size());
	    model.addAttribute("listaEmpleados", listaEmpledos);
	    
        List<Empleado> listaEmpleadosAsignados = mantenimientoG.getEmpleadosAsignados();
        model.addAttribute("listaEmpleadosAsignados", listaEmpleadosAsignados);	  
        
        //Roles
        model.addAttribute("listaRoles", rolService.todosRoles());
	    
	    return "admin/mantenimiento/elegirPersonal";
	}	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}	
	
	//Buscar personal para asignar
	@PostMapping("/admin/buscarEmpleado")
    public String buscarEmpleados(
            @RequestParam(value = "mantenimientoAsignado", required = false) Integer mantenimientoAsignado,
            @RequestParam(value = "habitacionAsignada", required = false) Integer habitacionAsignada,
    		
            @RequestParam(value = "filtrarPorHotel", required = false) String filtrarPorHotel,
            @RequestParam(value = "idHotelEmpleado", required = false) Integer idHotelEmpleado,
            @RequestParam(value = "campoFiltro", required = false) String campoFiltro,
            @RequestParam(value = "activarFiltroTexto", required = false) String activarFiltroTexto,
            @RequestParam(value = "valorFiltroTexto", required = false) String valorFiltroTexto,
            @RequestParam(value = "rolId", required = false) Integer rolId,
            Model model
    ) {
		filtrarPorHotel="on";
        System.out.println("Filtrar por hotel: " + filtrarPorHotel);
        System.out.println("ID del hotel: " + idHotelEmpleado);
        System.out.println("Campo de filtro: " + campoFiltro);
        System.out.println("Activar filtro texto: " + activarFiltroTexto);
        System.out.println("Valor del filtro texto: " + valorFiltroTexto);
        System.out.println("ID del rol: " + rolId);

        List<Empleado> listaEmpleados = empleadoService.filtrarEmpleadosEstado(
                (filtrarPorHotel != null && filtrarPorHotel.equals("on")) ? idHotelEmpleado : null,
                (activarFiltroTexto != null && activarFiltroTexto.equals("on")) ? campoFiltro : null,
                (activarFiltroTexto != null && activarFiltroTexto.equals("on")) ? valorFiltroTexto : null,
                rolId, 1
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

        
		Mantenimiento mantenimientoG = mantenimientoService.buscarMantenimientoID(mantenimientoAsignado);
		Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionAsignada);
		Hotel hotel = habitacion.getIdHotelHabitacion();
        
		model.addAttribute("mantenimiento", mantenimientoG);
		
		model.addAttribute("hotel", hotel);
		
		model.addAttribute("habitacion", habitacion);
		
		
		
	    //redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento programado con éxito");
	    
	    
	    
	    
        List<Empleado> listaEmpleadosAsignados = mantenimientoG.getEmpleadosAsignados();
        model.addAttribute("listaEmpleadosAsignados", listaEmpleadosAsignados);	  
        
        
        
        return "admin/mantenimiento/elegirPersonal";
    }		
	
	
	
	
	//Selección del personal
    @GetMapping("/admin/agregarEmpleado/{idEmpleado}/{idMantenimiento}")
    public String agregarEmpleado(@PathVariable Integer idEmpleado,
                                  @PathVariable Integer idMantenimiento,
                                  RedirectAttributes redirectAttributes, Model model) {

    	
    	
        Empleado empleado = empleadoService.buscarEmpleadoID(idEmpleado);
        Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);

		
		Habitacion habitacion = mantenimiento.getIdMantenimientoHabitacion();
		
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
	    //Los modelos		
		model.addAttribute("mantenimiento", mantenimiento);
		
		model.addAttribute("hotel", hotel);
		
		model.addAttribute("habitacion", habitacion);
		
		
		
	    //redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento programado con éxito");
	    
	    List<Empleado> listaEmpledos = empleadoService.filtrarEmpleadosEstado(hotel.getId(), null, null, null, 1);
	    model.addAttribute("listaEmpleados", listaEmpledos);
	    

        
        //Roles
        model.addAttribute("listaRoles", rolService.todosRoles());        
        
        
        
        if (empleado == null || mantenimiento == null) {
            
        	return "admin/mantenimiento/elegirPersonal";
        	
        }

        // Verificar si ya existe la asignación
        DetalleMantenimientoId detalleId = new DetalleMantenimientoId(idEmpleado, idMantenimiento);
        DetalleMantenimiento existente = detalleMantenimientoService.buscarPorId(detalleId);

        if (existente != null) {
        	model.addAttribute("mensajeError", "El empleado ya está asignado a este mantenimiento.");
        } else {
            DetalleMantenimiento nuevoDetalle = new DetalleMantenimiento();
            nuevoDetalle.setId(detalleId);
            nuevoDetalle.setEmpleadoDetalleMantenimiento(empleado);
            nuevoDetalle.setMantenimientoDetalleMantenimiento(mantenimiento);

            detalleMantenimientoService.guardarDetalleMantenimiento(nuevoDetalle);
            model.addAttribute("mensajeExito", "Empleado asignado al mantenimiento.");
        }
        
        mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
        
        List<Empleado> listaEmpleadosAsignados = mantenimiento.getEmpleadosAsignados();
        model.addAttribute("listaEmpleadosAsignados", listaEmpleadosAsignados);	          

        return "admin/mantenimiento/elegirPersonal";
    }	
	
	
	//Eliminar asignación
    @GetMapping("/admin/eliminarEmpleado/{idEmpleado}/{idMantenimiento}")
    public String eliminarEmpleado(@PathVariable Integer idEmpleado,
                                   @PathVariable Integer idMantenimiento,
                                   Model model) {

        // Buscar el mantenimiento para obtener los modelos necesarios
        Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);

        if (mantenimiento == null) {
            return "error"; 
        }

        Habitacion habitacion = mantenimiento.getIdMantenimientoHabitacion();
        Hotel hotel = habitacion.getIdHotelHabitacion();

        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("hotel", hotel);
        model.addAttribute("habitacion", habitacion);

        List<Empleado> listaEmpleadosDisponibles = empleadoService.filtrarEmpleadosEstado(hotel.getId(), null, null, null, 1);
        model.addAttribute("listaEmpleados", listaEmpleadosDisponibles);

        model.addAttribute("listaRoles", rolService.todosRoles());

        //Crear la clave primaria compuesta para buscar y eliminar la asignación
        DetalleMantenimientoId detalleId = new DetalleMantenimientoId(idEmpleado, idMantenimiento);
        DetalleMantenimiento existente = detalleMantenimientoService.buscarPorId(detalleId);

        if (existente != null) {
            detalleMantenimientoService.eliminarDetalleMantenimiento(detalleId);
            model.addAttribute("mensajeExitoBorra", "Empleado eliminado del mantenimiento.");
        } else {
            model.addAttribute("mensajeError", "No se encontró la asignación a eliminar.");
        }

        // Refrescar la lista de empleados asignados
        mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
        List<Empleado> listaEmpleadosAsignados = mantenimiento.getEmpleadosAsignados();
        model.addAttribute("listaEmpleadosAsignados", listaEmpleadosAsignados);

        return "admin/mantenimiento/elegirPersonal";
    }    
    
    
    //Lista de mantenimientos
	@GetMapping("/admin/listaMantenimientoGuardado")
	public String mostrarListaG(Model model) {
		//Para los mantenimientos
		List<Mantenimiento> lista = mantenimientoService.todosMantes();
		model.addAttribute("listaMantenimientos", lista);
		
		model.addAttribute("mensajeExito", "Mantenimiento programado con éxito");
		
		
		return "admin/mantenimiento/panelAdminMante";
		
	}    
	
	//Editar mantenimiento
	
	@GetMapping("/admin/editar/{id}")
	public String editarMantenimiento(@PathVariable("id") int idMantenimiento, Model model, RedirectAttributes redirectAttributes) {
		Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
		model.addAttribute("mantenimiento", mantenimiento);
		

		
		Habitacion habitacion = mantenimiento.getIdMantenimientoHabitacion();
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("habitacion", habitacion);
		
		
		model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
		
		
		
		//Para indicar que es guardado
		model.addAttribute("act", 1);

		model.addAttribute("idMante", idMantenimiento);			
		
		return "admin/mantenimiento/formMantenimiento";
	}	
	
	
	
	//Cancelar un  mantenimiento
	@GetMapping("/admin/cancelar/{id}")
	public String cambiarEstado(@PathVariable("id") int idMantenimiento, RedirectAttributes redirectAttributes) {
		
		Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
		
		mantenimiento.setEstado("Cancelada");
		
		mantenimientoService.guardarMantenimiento(mantenimiento);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento cancelado.");
		
		return "redirect:/mantenimiento/admin/listaMantenimiento";
	}		
	
	
	//Reactivar un mantenimiento
	@GetMapping("/admin/reactivar/{id}")
	public String activarMantenimiento(@PathVariable("id") int idMantenimiento, RedirectAttributes redirectAttributes) {
		
		Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
		
		mantenimiento.setEstado("Programada");
		
		mantenimientoService.guardarMantenimiento(mantenimiento);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento reactivado.");
		
		return "redirect:/mantenimiento/admin/listaMantenimiento";
	}
	
	//Culminar un mantenimiento
	@GetMapping("/admin/culminar/{id}")
	public String culminarMantenimiento(@PathVariable("id") int idMantenimiento, RedirectAttributes redirectAttributes) {
		
		Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
		
		mantenimiento.setEstado("Culminada");
		
		mantenimientoService.guardarMantenimiento(mantenimiento);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Mantenimiento reactivado.");
		
		return "redirect:/mantenimiento/admin/listaMantenimiento";
	}		
	
	
	
	//DetalleMantenimiento
	@GetMapping("/admin/ver/{id}")
	public String verDetalle(@PathVariable("id") int idMantenimiento, Model model) {
		
		Mantenimiento mantenimiento = mantenimientoService.buscarMantenimientoID(idMantenimiento);
		Habitacion habitacion = mantenimiento.getIdMantenimientoHabitacion();
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
		
		
		model.addAttribute("mantenimiento", mantenimiento);
		model.addAttribute("habitacion", habitacion);
		model.addAttribute("hotel", hotel);
		//model.addAttribute("listaHabitaciones", hotel.getHabitaciones());
		
		
        List<Empleado> listaEmpleadosAsignados = mantenimiento.getEmpleadosAsignados();
        model.addAttribute("listaEmpleadosAsignados", listaEmpleadosAsignados);
		
		return "admin/mantenimiento/detalleAdminMante";
	}  	
	
	//Cambiar de hotel y/o habitación
    @GetMapping("/admin/selecHotelCambio/{idMante}")
    public String seleccionarHotelCambio(@PathVariable Integer idMante, Model model) {
    	
        //Eliminar todas las asignaciones de empleados para este mantenimiento
        detalleMantenimientoService.eliminarPorIdMantenimiento(idMante);
        
        
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		model.addAttribute("idMante", idMante);
		
		return "admin/mantenimiento/selecHotelMante";	        
    }	
	
    
    //Filtros de mantenimiento
    @PostMapping("/admin/filtrarMantenimientos")
    public String filtrarMantenimientos(
            @RequestParam(value = "filtrarPorHotel", required = false) String filtrarPorHotel,
            @RequestParam(value = "idHotel", required = false) Integer idHotel,
            @RequestParam(value = "filtrarPorHabitacion", required = false) String filtrarPorHabitacion,
            @RequestParam(value = "numeroHabitacion", required = false) Integer numeroHabitacion,
            @RequestParam(value = "filtrarPorFecha", required = false) String filtrarPorFecha,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
            @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
            Model model
    ) {
        if (filtrarPorHabitacion != null && filtrarPorHabitacion.equals("on") && (filtrarPorHotel == null || !filtrarPorHotel.equals("on") || idHotel == null)) {
            model.addAttribute("errorFiltro", "Para filtrar por número de habitación, debes activar y especificar el ID del hotel.");
            model.addAttribute("listaMantenimientos", mantenimientoService.todosMantes()); //Mostrar todos los mantenimientos
            return "admin/mantenimiento/panelAdminMante";
        }

        Integer hotelIdParaFiltrar = null;
        if (filtrarPorHotel != null && filtrarPorHotel.equals("on")) {
            hotelIdParaFiltrar = idHotel;
        }

        Integer habitacionParaFiltrar = null;
        if (filtrarPorHabitacion != null && filtrarPorHabitacion.equals("on")) {
            habitacionParaFiltrar = numeroHabitacion;
        }

        String inicioFechaParaFiltrar = null;
        if (filtrarPorFecha != null && filtrarPorFecha.equals("on")) {
            inicioFechaParaFiltrar = fechaInicioStr;
        }

        String finFechaParaFiltrar = null;
        if (filtrarPorFecha != null && filtrarPorFecha.equals("on")) {
            finFechaParaFiltrar = fechaFinStr;
        }

        List<Mantenimiento> listaMantenimientos = mantenimientoService.filtrarMantenimientos(
                hotelIdParaFiltrar,
                habitacionParaFiltrar,
                inicioFechaParaFiltrar,
                finFechaParaFiltrar
        );


        model.addAttribute("listaMantenimientos", listaMantenimientos);
        return "admin/mantenimiento/panelAdminMante";
    }    
	

	
	
	
	
	
	
}
