package carlos.mejia.proyectohoteles.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Reserva;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.DetalleMantenimientoService;
import carlos.mejia.proyectohoteles.service.EmpleadoService;
import carlos.mejia.proyectohoteles.service.HabitacionService;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.MantenimientoService;
import carlos.mejia.proyectohoteles.service.PerfilService;
import carlos.mejia.proyectohoteles.service.ReservaService;
import carlos.mejia.proyectohoteles.service.RolService;
import carlos.mejia.proyectohoteles.service.UsuarioService;

@Controller
@RequestMapping("/reserva")
public class ReservaController {
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
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Autowired
	PerfilService perfilService;	
	
	@GetMapping("/admin/listaReserva")
	public String mostrarLista(Model model) {
		//Para los hoteles 
		List<Reserva> lista = reservaService.todasReservas();
		model.addAttribute("listaReservas", lista);
		
		//Para su lista de teléfonos:
		
		
		
		return "admin/reserva/panelAdminReserva";
		
	}		

	//Seleccionar un hotel
	@GetMapping("/admin/selecHotel/{idReserva}")
	public String seleccionarHotelHabitacion(@PathVariable("idReserva") int idReserva, Model model) {
		
		/*List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);*/		
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		model.addAttribute("idReserva", idReserva);
		
		return "admin/reserva/selecHotelReserva";		
		
	}		
	
	
	//Buscador de hoteles
	@PostMapping("/admin/buscarSelecHotel")
	public String buscarSeleccionarHotel(
			@RequestParam(value = "idReserva", required = false) int idReserva,
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
	    
	    model.addAttribute("idReserva", idReserva);
	    
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

	    return "admin/reserva/selecHotelReserva"; 
	}		

	
	
	//Seleccionar una habitación
	@GetMapping("/admin/selecHabitacion/{idHotel}/{idReserva}")
	public String mostrarListaHabitaciones(@PathVariable("idHotel") int idHotel,
			@PathVariable("idReserva") int idReserva,
			Model model) {
		//El hotel específico
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		//El Título
		model.addAttribute("tituloLista", "Seleccionar habitación de " + hotel.getNombre() + " para ver el historial de reservas");
		
		//El listado de todas las habitaciones
		List<Habitacion> lista = hotel.getHabitaciones();
		
		model.addAttribute("listaHabitaciones", lista);
		
		//Para el hotel como tal:
		model.addAttribute("hotel", hotel);
		
		
		model.addAttribute("idReserva", idReserva);
		
		
		return "admin/reserva/selecHabiReserva";		
		
		
	}	
	
	
	
	
	
	//Filtrar habitaciones
	@PostMapping("/admin/buscarListaHabitaciones")
	public String buscarHabitaciones(
	        @RequestParam("idHotelPertenece") int idHotel, // Recibimos el idHotel
	        @RequestParam("idReserva") int idReserva,
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

	    model.addAttribute("idReserva", idReserva);
	    
	    
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
	    
	    
	    
	    
	    return "admin/reserva/selecHabiReserva";
	}	
	
	//Abrir el historial
	@GetMapping("/admin/historialReserva/{idHabitacion}/{idReserva}")
	public String abrirFormHabitacion(@PathVariable("idHabitacion") int idHabitacion, 
			@PathVariable("idReserva") int idReserva,Model model, Reserva reserva) {
		
		//La acción y a qué hotel se le está agreganda:
		Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
		//Lista de reserva
		
		List<Reserva> listaReservas = habitacion.getReservasHabitacion();
		model.addAttribute("listaReservas", listaReservas);
		
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("habitacion", habitacion);
		
		
		model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
		
		
		
		//Para indicar que es guardado
		model.addAttribute("act", 0);
		
		return "admin/reserva/historialReserva";
	}	
	
	//Mostrar para atender reserva
	@GetMapping("/admin/atenderReservaMostrar")
	public String mostrarAtenderReserva(Model model) {
		 
	
		
		
		return "admin/reserva/atenderReserva";
		
	}		
	
	//Atender reserva
    @PostMapping("/admin/atender")
    public String atenderReserva(@RequestParam("codigoReserva") String codigoReserva, Model model) {
        Reserva reserva = reservaService.buscarPorCodigoReserva(codigoReserva);

        if (reserva == null) {
            model.addAttribute("error", "No se encontró ninguna reserva con el código: " + codigoReserva);
            return "admin/reserva/atenderReserva";
        }

        String estado = reserva.getEstado();
        if (estado.equalsIgnoreCase("Proceso")) {
            model.addAttribute("error", "Esta reserva ya fue atendida.");
            return "admin/reserva/atenderReserva";
        } else if (estado.equalsIgnoreCase("Terminado")) {
            model.addAttribute("error", "Esta reserva ya fue completada y terminada.");
            return "admin/reserva/atenderReserva";
        } else if (estado.equalsIgnoreCase("Cancelado")) {
            model.addAttribute("error", "La reserva fue cancelada.");
            return "admin/reserva/atenderReserva";
        } else if (estado.equalsIgnoreCase("Reservado")) {
            Date fechaInicioReserva = reserva.getFechainicio();
            Date fechaFinReserva = reserva.getFechafin();
            Date fechaActual = new Date();

            
            fechaInicioReserva = java.sql.Date.valueOf(new java.sql.Date(fechaInicioReserva.getTime()).toString());
            fechaFinReserva = java.sql.Date.valueOf(new java.sql.Date(fechaFinReserva.getTime()).toString());
            fechaActual = java.sql.Date.valueOf(new java.sql.Date(fechaActual.getTime()).toString());

            if (fechaInicioReserva.after(fechaActual)) {
                model.addAttribute("error", "Lo siento, aún no es fecha para la reservación.");
                return "admin/reserva/atenderReserva";
            } else if (fechaFinReserva.before(fechaActual)) {
                model.addAttribute("error", "Lo sentimos, la reservación ya venció.");
                return "admin/reserva/atenderReserva";
            } else {
                model.addAttribute("reserva", reserva);
                
                Habitacion habitacion = reserva.getIdReservaHabitacion();
                Hotel hotel = habitacion.getIdHotelHabitacion();
                Cliente cliente = reserva.getIdReservaCliente();
                
                model.addAttribute("habitacion", habitacion);
                model.addAttribute("hotel", hotel);
                model.addAttribute("cliente", cliente);
                
                return "admin/reserva/detalleReserva";
            }
        }

        //Si el estado no coincide con ninguno de los anteriores (por si acaso)
        model.addAttribute("error", "Estado de reserva no válido.");
        return "admin/reserva/atenderReserva";
    }	
    
    //Atender
    @GetMapping("/admin/atender/{id}")
	public String atenderReserva(Principal principal, @PathVariable("id") int idReserva, RedirectAttributes redirectAttributes) {

		Reserva reserva = reservaService.buscarReservaId(idReserva);

		//Obtener el username del empleado logueado
		String usernameEmpleado = principal.getName();

		//Buscar el usuario del empleado por su username
		Usuario usuarioEmpleado = usuarioService.buscarPorUsername(usernameEmpleado);

		Empleado empleadoAtendiendo = null;
		if (usuarioEmpleado != null && !usuarioEmpleado.getEmpleadoligado().isEmpty()) {
			//El primer empleado de la lista
			empleadoAtendiendo = usuarioEmpleado.getEmpleadoligado().get(0);

			//Asignalo
			reserva.setIdReservaEmpleado(empleadoAtendiendo);
		} else {
			
			redirectAttributes.addFlashAttribute("mensajeError", "No se pudo asignar el empleado a la reserva.");
			return "redirect:/reserva/admin/listaReserva"; 
		}

		reserva.setEstado("Proceso");
		reservaService.guardarReserva(reserva);

		redirectAttributes.addFlashAttribute("mensajeExito", "Reserva atendida por " + empleadoAtendiendo.getNombre() + ".");

		return "redirect:/reserva/admin/listaReserva";
	}   
	
	@GetMapping("/admin/cancelar/{id}")
	public String cancerlarReserva(@PathVariable("id") int idReserva, RedirectAttributes redirectAttributes) {
		
		Reserva reserva = reservaService.buscarReservaId(idReserva);
		
		reserva.setEstado("Cancelado");
		
		reservaService.guardarReserva(reserva);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Reserva cancelada.");
		
		return "redirect:/reserva/admin/listaReserva";
	}		
	
	@GetMapping("/admin/terminar/{id}")
	public String terminarReserva(@PathVariable("id") int idReserva, RedirectAttributes redirectAttributes) {
		
		Reserva reserva = reservaService.buscarReservaId(idReserva);
		
		reserva.setEstado("Terminado");
		
		reservaService.guardarReserva(reserva);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "Reserva cancelada.");
		
		return "redirect:/reserva/admin/listaReserva";
	}			
	
	
	//Ver detalle de reserva
	@GetMapping("/admin/ver/{id}")
	public String verDetalleReserva(@PathVariable("id") int idReserva, Model model) {
		
		Reserva reserva = reservaService.buscarReservaId(idReserva);
		
        model.addAttribute("reserva", reserva);
        
        Habitacion habitacion = reserva.getIdReservaHabitacion();
        Hotel hotel = habitacion.getIdHotelHabitacion();
        Cliente cliente = reserva.getIdReservaCliente();
        
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("hotel", hotel);
        model.addAttribute("cliente", cliente);
        
        return "admin/reserva/detalleReserva";
	}	
	
	
	
	//FILTRADO 
    @PostMapping("/admin/filtrarReservas")
    public String filtrarReservas(
            @RequestParam(value = "filtrarPorCodigoReserva", required = false) String filtrarPorCodigoReserva,
            @RequestParam(value = "codigoReserva", required = false) String codigoReserva,
            @RequestParam(value = "filtrarPorFecha", required = false) String filtrarPorFecha,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
            @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
            @RequestParam(value = "filtrarPorPrecio", required = false) String filtrarPorPrecio,
            @RequestParam(value = "precioMinimo", required = false) String precioMinimoStr,
            @RequestParam(value = "precioMaximo", required = false) String precioMaximoStr,
            @RequestParam(value = "filtrarPorNombreCliente", required = false) String filtrarPorNombreCliente,
            @RequestParam(value = "nombreCliente", required = false) String nombreCliente,
            Model model
    ) {
        System.out.println("--- INICIO DE FILTRADO DE RESERVAS ---");
        System.out.println("filtrarPorCodigoReserva: " + filtrarPorCodigoReserva);
        System.out.println("codigoReserva: " + codigoReserva);
        System.out.println("filtrarPorFecha: " + filtrarPorFecha);
        System.out.println("fechaInicioStr: " + fechaInicioStr);
        System.out.println("fechaFinStr: " + fechaFinStr);
        System.out.println("filtrarPorPrecio: " + filtrarPorPrecio);
        System.out.println("precioMinimoStr: " + precioMinimoStr);
        System.out.println("precioMaximoStr: " + precioMaximoStr);
        System.out.println("filtrarPorNombreCliente: " + filtrarPorNombreCliente);
        System.out.println("nombreCliente: " + nombreCliente);
        System.out.println("--- FIN DE DATOS RECIBIDOS DEL FORMULARIO ---");    	
    	
        List<Reserva> listaReservas = List.of();
        String mensajeError = null;
        String mensajeFiltro = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;
        Double precioMinimo = null;
        Double precioMaximo = null;

        if (filtrarPorFecha != null && filtrarPorFecha.equals("on")) {
            try {
                if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                    fechaInicio = sdf.parse(fechaInicioStr);
                }
                if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                    fechaFin = sdf.parse(fechaFinStr);
                }

                if (fechaInicio != null && fechaFin != null && fechaInicio.after(fechaFin)) {
                    mensajeError = "La fecha de inicio no puede ser posterior a la fecha de fin.";
                }
            } catch (ParseException e) {
                mensajeError = "Formato de fecha inválido.";
            }
        }

        if (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) {
            try {
                if (precioMinimoStr != null && !precioMinimoStr.isEmpty()) {
                    precioMinimo = Double.parseDouble(precioMinimoStr);
                }
                if (precioMaximoStr != null && !precioMaximoStr.isEmpty()) {
                    precioMaximo = Double.parseDouble(precioMaximoStr);
                }

                if (precioMinimo != null && precioMaximo != null && precioMinimo > precioMaximo) {
                    mensajeError = (mensajeError == null ? "" : mensajeError + "<br>") + "El precio mínimo no puede ser mayor que el precio máximo.";
                }
            } catch (NumberFormatException e) {
                mensajeError = (mensajeError == null ? "" : mensajeError + "<br>") + "Formato de precio inválido.";
            }
        }

        if (mensajeError != null) {
            model.addAttribute("mensajeError", mensajeError);
            listaReservas = reservaService.todasReservas();
        } else if (filtrarPorCodigoReserva != null && filtrarPorCodigoReserva.equals("on") && codigoReserva != null && !codigoReserva.isEmpty()) {
            Reserva reserva = reservaService.buscarPorCodigoReserva(codigoReserva);
            if (reserva != null) {
                listaReservas = List.of(reserva);
                mensajeFiltro = "Filtrando por número de reserva: " + codigoReserva;
            } else {
                mensajeError = "No se encontró ninguna reserva con el número: " + codigoReserva;
                listaReservas = reservaService.todasReservas();
            }
        } else {
            listaReservas = reservaService.filtrarReservas(
                    null, // codigoReserva se maneja aparte
                    (filtrarPorFecha != null && filtrarPorFecha.equals("on")) ? fechaInicioStr : null,
                    (filtrarPorFecha != null && filtrarPorFecha.equals("on")) ? fechaFinStr : null,
                    (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) ? precioMinimo : null, // Pasar Double directamente
                    (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) ? precioMaximo : null, // Pasar Double directamente
                    (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on")) ? nombreCliente : null
            );
            StringBuilder mensajeFiltroBuilder = new StringBuilder("Filtrando por: ");
            boolean algunFiltroActivo = false;

            if (filtrarPorFecha != null && filtrarPorFecha.equals("on") && (fechaInicioStr != null && !fechaInicioStr.isEmpty() || fechaFinStr != null && !fechaFinStr.isEmpty())) {
                mensajeFiltroBuilder.append("Rango de Fechas (Inicio: ").append(fechaInicioStr).append(", Fin: ").append(fechaFinStr).append(") ");
                algunFiltroActivo = true;
            }

            if (filtrarPorPrecio != null && filtrarPorPrecio.equals("on") && (precioMinimo != null || precioMaximo != null)) {
                if (algunFiltroActivo) mensajeFiltroBuilder.append(", ");
                mensajeFiltroBuilder.append("Rango de Precios (Min: ").append(precioMinimoStr).append(", Max: ").append(precioMaximoStr).append(") ");
                algunFiltroActivo = true;
            }

            if (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
                if (algunFiltroActivo) mensajeFiltroBuilder.append(", ");
                mensajeFiltroBuilder.append("Nombre del Cliente que contiene: ").append(nombreCliente);
                algunFiltroActivo = true;
            }

            if (algunFiltroActivo) {
                mensajeFiltro = mensajeFiltroBuilder.toString();
            } else {
                mensajeFiltro = "Mostrando todas las reservas.";
            }
        }

        model.addAttribute("listaReservas", listaReservas);
        if (mensajeError != null) {
            model.addAttribute("mensajeError", mensajeError);
        }
        if (mensajeFiltro != null) {
            model.addAttribute("mensajeFiltro", mensajeFiltro);
        }

        return "admin/reserva/panelAdminReserva";
    }
    
    
    @PostMapping("/admin/filtrarReservasHistorial")
    public String filtrarReservasHistorial(
            @RequestParam("habitacionReserva") Integer habitacionReservaId,
            @RequestParam(value = "filtrarPorCodigoReserva", required = false) String filtrarPorCodigoReserva,
            @RequestParam(value = "codigoReserva", required = false) String codigoReserva,
            @RequestParam(value = "filtrarPorFecha", required = false) String filtrarPorFecha,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
            @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
            @RequestParam(value = "filtrarPorPrecio", required = false) String filtrarPorPrecio,
            @RequestParam(value = "precioMinimo", required = false) String precioMinimoStr,
            @RequestParam(value = "precioMaximo", required = false) String precioMaximoStr,
            @RequestParam(value = "filtrarPorNombreCliente", required = false) String filtrarPorNombreCliente,
            @RequestParam(value = "nombreCliente", required = false) String nombreCliente,
            Model model
    ) {
        System.out.println("--- INICIO DE FILTRADO DE HISTORIAL DE RESERVAS ---");
        // ... (logs como antes) ...

        List<Reserva> listaReservas = List.of();
        String mensajeError = null;
        String mensajeFiltro = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;
        Double precioMinimo = null;
        Double precioMaximo = null;

        if (filtrarPorFecha != null && filtrarPorFecha.equals("on")) {
            try {
                if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                    fechaInicio = sdf.parse(fechaInicioStr);
                }
                if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
                    fechaFin = sdf.parse(fechaFinStr);
                }

                if (fechaInicio != null && fechaFin != null && fechaInicio.after(fechaFin)) {
                    mensajeError = "La fecha de inicio no puede ser posterior a la fecha de fin.";
                }
            } catch (ParseException e) {
                mensajeError = "Formato de fecha inválido.";
            }
        }

        if (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) {
            try {
                if (precioMinimoStr != null && !precioMinimoStr.isEmpty()) {
                    precioMinimo = Double.parseDouble(precioMinimoStr);
                }
                if (precioMaximoStr != null && !precioMaximoStr.isEmpty()) {
                    precioMaximo = Double.parseDouble(precioMaximoStr);
                }

                if (precioMinimo != null && precioMaximo != null && precioMinimo > precioMaximo) {
                    mensajeError = (mensajeError == null ? "" : mensajeError + "<br>") + "El precio mínimo no puede ser mayor que el precio máximo.";
                }
            } catch (NumberFormatException e) {
                mensajeError = (mensajeError == null ? "" : mensajeError + "<br>") + "Formato de precio inválido.";
            }
        }

        if (mensajeError != null) {
            model.addAttribute("mensajeError", mensajeError);
            listaReservas = reservaService.buscarReservasPorHabitacion(habitacionReservaId);
        } else if (filtrarPorCodigoReserva != null && filtrarPorCodigoReserva.equals("on") && codigoReserva != null && !codigoReserva.isEmpty()) {
            Reserva reserva = reservaService.buscarReservaPorCodigoReservaYHabitacion(codigoReserva, habitacionReservaId);
            if (reserva != null) {
                listaReservas = List.of(reserva);
                mensajeFiltro = "Filtrando por número de reserva: " + codigoReserva;
            } else {
                mensajeError = "No se encontró ninguna reserva con el número: " + codigoReserva + " para esta habitación.";
                listaReservas = reservaService.buscarReservasPorHabitacion(habitacionReservaId);
            }
        } else {
        	listaReservas = reservaService.filtrarReservasPorHabitacion(
        		    habitacionReservaId,
        		    null, // filtrarPorCodigoReserva
        		    null, // codigoReserva
        		    (filtrarPorFecha != null && filtrarPorFecha.equals("on")) ? fechaInicioStr : null,
        		    (filtrarPorFecha != null && filtrarPorFecha.equals("on")) ? fechaFinStr : null,
        		    (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) ? precioMinimo : null,
        		    (filtrarPorPrecio != null && filtrarPorPrecio.equals("on")) ? precioMaximo : null,
        		    (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on")) ? nombreCliente : null,
        		    (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on")) ? nombreCliente : null // ¡Este era el que faltaba!
        		);
            StringBuilder mensajeFiltroBuilder = new StringBuilder("Filtrando por: ");
            boolean algunFiltroActivo = false;

            if (filtrarPorFecha != null && filtrarPorFecha.equals("on") && (fechaInicioStr != null && !fechaInicioStr.isEmpty() || fechaFinStr != null && !fechaFinStr.isEmpty())) {
                mensajeFiltroBuilder.append("Rango de Fechas (Inicio: ").append(fechaInicioStr).append(", Fin: ").append(fechaFinStr).append(") ");
                algunFiltroActivo = true;
            }

            if (filtrarPorPrecio != null && filtrarPorPrecio.equals("on") && (precioMinimo != null || precioMaximo != null)) {
                if (algunFiltroActivo) mensajeFiltroBuilder.append(", ");
                mensajeFiltroBuilder.append("Rango de Precios (Min: ").append(precioMinimoStr).append(", Max: ").append(precioMaximoStr).append(") ");
                algunFiltroActivo = true;
            }

            if (filtrarPorNombreCliente != null && filtrarPorNombreCliente.equals("on") && nombreCliente != null && !nombreCliente.isEmpty()) {
                if (algunFiltroActivo) mensajeFiltroBuilder.append(", ");
                mensajeFiltroBuilder.append("Nombre del Cliente que contiene: ").append(nombreCliente);
                algunFiltroActivo = true;
            }

            if (algunFiltroActivo) {
                mensajeFiltro = mensajeFiltroBuilder.toString();
            } else {
                mensajeFiltro = "Mostrando todas las reservas para la habitación con ID: " + habitacionReservaId;
            }
        }

        model.addAttribute("listaReservas", listaReservas);
        if (mensajeError != null) {
            model.addAttribute("mensajeError", mensajeError);
        }
        if (mensajeFiltro != null) {
            model.addAttribute("mensajeFiltro", mensajeFiltro);
        }
        // model.addAttribute("habitacion", /* Obtener la habitación por ID */);
        Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionReservaId);
        Hotel hotel = habitacion.getIdHotelHabitacion();
        
		model.addAttribute("hotel", hotel);
		model.addAttribute("habitacion", habitacion);
		
		
		model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
		
		
		
		//Para indicar que es guardado
		model.addAttribute("act", 0);        

        return "admin/reserva/historialReserva";
    }
}
