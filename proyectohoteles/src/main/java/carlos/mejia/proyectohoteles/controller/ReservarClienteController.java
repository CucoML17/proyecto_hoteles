package carlos.mejia.proyectohoteles.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Mantenimiento;
import carlos.mejia.proyectohoteles.model.Reserva;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.service.ClienteService;
import carlos.mejia.proyectohoteles.service.HabitacionService;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.MantenimientoService;
import carlos.mejia.proyectohoteles.service.PerfilService;
import carlos.mejia.proyectohoteles.service.ReservaService;
import carlos.mejia.proyectohoteles.service.UsuarioService;

@Controller
@RequestMapping("/user/reservar")
public class ReservarClienteController {
	
	@Autowired
	HabitacionService habitacionService;
	
	@Autowired
	HotelService hotelService;
	
	@Autowired
	ReservaService reservaService;
	
	//Para lo que viene siendo reservación:
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilService perfilService;
	
	@Autowired
	MantenimientoService mantenimientoService;	
	

	//Seleccionar un hotel
	@GetMapping("/selecHotel/{idReserva}")
	public String seleccionarHotelHabitacion(@PathVariable("idReserva") int idReserva, Model model) {
		
		/*List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);*/		
		List<Hotel> lista = hotelService.todosHotelesEstado(1);
		model.addAttribute("listaHoteles", lista);
		
		model.addAttribute("idReserva", idReserva);
		
		return "user/reservar/selecHotelReservaR";		
		
	}		
	
	
	//Buscador de hoteles
	@PostMapping("/buscarSelecHotel")
    public String buscarSeleccionarHotel(
            @RequestParam(value = "idReserva", required = false) int idReserva,
            @RequestParam(value = "tipoBusca", required = false) String tipoBusca,
            @RequestParam(value = "valorBusca", required = false) String valorBusca,
            @RequestParam(value = "orden", required = false) String orden,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max,
            Model model
    ) {
        System.out.println("Tipo de búsqueda: " + tipoBusca);
        System.out.println("Valor de búsqueda: " + valorBusca);
        System.out.println("Orden: " + orden);
        System.out.println("Precio mínimo: " + min);
        System.out.println("Precio máximo: " + max);

        model.addAttribute("idReserva", idReserva);
        model.addAttribute("min", min); // Para mantener los valores en el formulario
        model.addAttribute("max", max); // Para mantener los valores en el formulario

        List<Hotel> lista = hotelService.hotelesActivos(); // Inicialmente obtener solo hoteles activos

        if (tipoBusca != null) {
            if (tipoBusca.equalsIgnoreCase("nombre") || tipoBusca.equalsIgnoreCase("direccion")) {
                System.out.println("Filtrado por nombre o dirección");
                lista = hotelService.filtrarHotelPor(tipoBusca, orden, valorBusca);
                // Filtrar la lista resultante para mantener solo los hoteles activos
                lista.removeIf(hotel -> hotel.getEstado() != 1);
            } else if (tipoBusca.equalsIgnoreCase("precioPromedio")) {
                System.out.println("Filtrado por precio promedio");
                if (min != null && max != null) {
                    lista = hotelService.filtrarHotelesActivosPorPrecioPromedio(min, max, orden);
                } else {
                    model.addAttribute("mensajeError", "Por favor, ingrese un rango de precios.");
                    lista = List.of(); // Si no hay rango, la lista estará vacía
                }
            }
        }

        model.addAttribute("listaHoteles", lista);

        return "user/reservar/selecHotelReservaR";
    }	

	
	
	//Seleccionar una habitación
	@GetMapping("/selecHabitacion/{idHotel}/{idReserva}")
	public String mostrarListaHabitaciones(@PathVariable("idHotel") int idHotel,
			@PathVariable("idReserva") int idReserva,
			Model model) {
		//El hotel específico
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		//El Título
		model.addAttribute("tituloLista", "Seleccionar habitación de " + hotel.getNombre() + " para ver el historial de reservas");

		//El listado de todas las habitaciones del hotel
		List<Habitacion> todasLasHabitaciones = hotel.getHabitaciones();
		// Creamos una nueva lista para almacenar solo las habitaciones activas
		List<Habitacion> habitacionesActivas = new ArrayList<>();

		// Iteramos sobre todas las habitaciones y agregamos solo las que tienen estado 1
		for (Habitacion habitacion : todasLasHabitaciones) {
			if (habitacion.getEstado() == 1) {
				habitacionesActivas.add(habitacion);
			}
		}

		// Pasamos la lista de habitaciones activas al modelo
		model.addAttribute("listaHabitaciones", habitacionesActivas);

		//Para el hotel como tal:
		model.addAttribute("hotel", hotel);

		model.addAttribute("idReserva", idReserva);

		return "user/reservar/selecHabiReservaR";
	}
	
	
	
	
	
	//Filtrar habitaciones
		@PostMapping("/buscarListaHabitaciones")
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


		    if(numeroHabitacion != null && numeroHabitacion.isEmpty()) {
		    	numeroHabitacion=null;
		    }
		    if(tipoHabitacion != null && tipoHabitacion.isEmpty()) {
		    	tipoHabitacion=null;
		    }
		    if(ordenCampo != null && ordenCampo.isEmpty()) {
		    	ordenCampo="numeroHabitacion";
		    }

		    List<Habitacion> listaHabitaciones = habitacionService.filtrarHabitaciones(
		            idHotel, numeroHabitacion, tipoHabitacion, minPrecio, maxPrecio, capacidad, ordenCampo, ordenDireccion
		    );

		    // Filtrar la lista de habitaciones para mantener solo las que tienen estado 1
		    List<Habitacion> habitacionesActivas = new ArrayList<>();
		    for (Habitacion habitacion : listaHabitaciones) {
		        if (habitacion.getEstado() == 1) {
		            habitacionesActivas.add(habitacion);
		        }
		    }
		    listaHabitaciones = habitacionesActivas; // Reemplazamos la lista original con la filtrada

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
	            model.addAttribute("mensajeFiltro", "Mostrando todas las habitaciones activas.");
	        } else {
	            // Eliminar la coma y el espacio final
	            if (mensajeFiltro.endsWith(", ")) {
	                mensajeFiltro = mensajeFiltro.substring(0, mensajeFiltro.length() - 2);
	            }
	            model.addAttribute("mensajeFiltro", mensajeFiltro);
	        }


		    return "user/reservar/selecHabiReservaR";
		}
	
	//Abrir el form para reservar
		@GetMapping("/abrirFormReserva/{idHabitacion}/{idReserva}")
		public String abrirFormReserva(@PathVariable("idHabitacion") int idHabitacion, 
				@PathVariable("idReserva") int idReserva, Model model, Reserva reserva) {
			
			if(idReserva!=0) {
				reserva = reservaService.buscarReservaId(idReserva);
				
				model.addAttribute("idReserva", idReserva);
				model.addAttribute("reserva", reserva);
			}else{
				model.addAttribute("idMante", 0);

			}
			
			
			//La acción y a qué hotel se le está reservando:
			Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
			Hotel hotel = habitacion.getIdHotelHabitacion();
			
			model.addAttribute("hotel", hotel);
			model.addAttribute("habitacion", habitacion);
			
			
			model.addAttribute("accion","Reservando una habitación");
			
			
			
			//Para indicar que es guardado
			model.addAttribute("act", 0);
			
			model.addAttribute("cantDias", 1);
			
			return "user/reservar/hacerReservacionForm";
		}	
		
		
		//Para guardar dicha reserva:
	    // Método auxiliar para verificar la disponibilidad de un rango de fechas
		// Método auxiliar para verificar la disponibilidad de un rango de fechas
		private boolean esRangoFechasDisponible(Integer habitacionId, Date fechaInicioPropuesta, Date fechaFinPropuesta, Integer idReservaExcluir) {
		    // Normalizar las fechas propuestas a inicio del día
		    Calendar calInicioPropuesta = Calendar.getInstance();
		    calInicioPropuesta.setTime(fechaInicioPropuesta);
		    calInicioPropuesta.set(Calendar.HOUR_OF_DAY, 0);
		    calInicioPropuesta.set(Calendar.MINUTE, 0);
		    calInicioPropuesta.set(Calendar.SECOND, 0);
		    calInicioPropuesta.set(Calendar.MILLISECOND, 0);
		    Date normalizedInicioPropuesta = calInicioPropuesta.getTime();

		    Calendar calFinPropuesta = Calendar.getInstance();
		    calFinPropuesta.setTime(fechaFinPropuesta);
		    calFinPropuesta.set(Calendar.HOUR_OF_DAY, 0);
		    calFinPropuesta.set(Calendar.MINUTE, 0);
		    calFinPropuesta.set(Calendar.SECOND, 0);
		    calFinPropuesta.set(Calendar.MILLISECOND, 0);
		    //Date normalizedFinPropuesta = calFinPropuesta.getTime();
		    calFinPropuesta.add(Calendar.DAY_OF_YEAR, 1);
		    Date normalizedFinPropuesta = calFinPropuesta.getTime();		    
		    

		    // Tu findReservasSolapadas ya hace un trabajo de búsqueda amplio.
		    // Pasaremos las fechas normalizadas.
		    List<Reserva> reservasPosiblementeSolapadas = reservaService.findReservasSolapadas(habitacionId, normalizedInicioPropuesta, normalizedFinPropuesta);

		    for (Reserva r : reservasPosiblementeSolapadas) {
		        if (idReservaExcluir == 0 || !r.getId().equals(idReservaExcluir)) {
		            // Normalizar las fechas de la reserva existente a inicio del día
		            Calendar calRInicio = Calendar.getInstance();
		            calRInicio.setTime(r.getFechainicio());
		            calRInicio.set(Calendar.HOUR_OF_DAY, 0);
		            calRInicio.set(Calendar.MINUTE, 0);
		            calRInicio.set(Calendar.SECOND, 0);
		            calRInicio.set(Calendar.MILLISECOND, 0);
		            Date normalizedRInicio = calRInicio.getTime();

		            Calendar calRFin = Calendar.getInstance();
		            calRFin.setTime(r.getFechafin());
		            calRFin.set(Calendar.HOUR_OF_DAY, 0);
		            calRFin.set(Calendar.MINUTE, 0);
		            calRFin.set(Calendar.SECOND, 0);
		            calRFin.set(Calendar.MILLISECOND, 0);
		            Date normalizedRFin = calRFin.getTime();

		            // Condición de solapamiento (A: 20-27, B: 27-30 se solapan)
		            // (Inicio_nueva <= Fin_existente) AND (Fin_nueva >= Inicio_existente)
		            if (!r.getEstado().equalsIgnoreCase("Terminado") && !r.getEstado().equalsIgnoreCase("Cancelado")) {
		                 if ( (normalizedInicioPropuesta.before(normalizedRFin) || normalizedInicioPropuesta.equals(normalizedRFin)) &&
		                      (normalizedFinPropuesta.after(normalizedRInicio) || normalizedFinPropuesta.equals(normalizedRInicio)) ) {
		                     return false; // Hay solapamiento con otra reserva activa
		                 }
		            }
		        }
		    }

		    // Validar solapamiento con mantenimientos
		    // Los mantenimientos son fechas puntuales, no rangos, entonces la lógica es diferente.
		    // Un mantenimiento se solapa si su fecha cae dentro del rango de la nueva reserva (inclusive)
		    List<Mantenimiento> mantenimientosEnRango = mantenimientoService.findMantenimientosSolapados(habitacionId, normalizedInicioPropuesta, normalizedFinPropuesta);
		    for (Mantenimiento m : mantenimientosEnRango) {
		        // Normalizar la fecha de mantenimiento
		        Calendar calM = Calendar.getInstance();
		        calM.setTime(m.getFecha());
		        calM.set(Calendar.HOUR_OF_DAY, 0);
		        calM.set(Calendar.MINUTE, 0);
		        calM.set(Calendar.SECOND, 0);
		        calM.set(Calendar.MILLISECOND, 0);
		        Date normalizedMFecha = calM.getTime();

		        if ((normalizedMFecha.after(normalizedInicioPropuesta) || normalizedMFecha.equals(normalizedInicioPropuesta)) &&
		            (normalizedMFecha.before(normalizedFinPropuesta) || normalizedMFecha.equals(normalizedFinPropuesta))) {
		            return false; // Hay solapamiento con mantenimiento
		        }
		    }
		    return true; // El rango de fechas está disponible
		}		

	    @PostMapping("/guardarReserva")
	    public String guardarReserva(Principal principal, Reserva reserva, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                                  @RequestParam("idReserva") int idReserva,
	                                  @RequestParam(value = "idHotelReservado", required = false) Integer hotelReservado,
	                                  @RequestParam(value = "idHabitacionReservado", required = false) Integer habitacionReservado,
	                                  @RequestParam(value = "cantidadDias", required = false) Integer cantidadDias,
	                                  @RequestParam(value = "act", required = false) Integer act) {

	        if (result.hasErrors()) {
	            for (ObjectError error : result.getAllErrors()) {
	                System.out.println("Ocurrio un error: " + error.getDefaultMessage());
	            }
	            if(idReserva!=0) {
	                model.addAttribute("idReserva", idReserva);
	            }else{
	                model.addAttribute("idReserva", 0);
	            }
	            Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionReservado);
	            Hotel hotel = habitacion.getIdHotelHabitacion();

	            model.addAttribute("hotel", hotel);
	            model.addAttribute("habitacion", habitacion);
	            model.addAttribute("accion","Reservando una habitación");
	            model.addAttribute("act", 0);
	            model.addAttribute("cantDias", cantidadDias);
	            return "user/reservar/hacerReservacionForm";
	        }

	        SimpleDateFormat formatoFechaUsuario = new SimpleDateFormat("dd 'de' MMMM 'del 'YYYY", new Locale("es", "MX"));

	        // 1.- Validar que la fecha de inicio no sea anterior a la fecha actual
	        Calendar calActual = Calendar.getInstance();
	        calActual.setTime(new Date());
	        calActual.set(Calendar.HOUR_OF_DAY, 0);
	        calActual.set(Calendar.MINUTE, 0);
	        calActual.set(Calendar.SECOND, 0);
	        calActual.set(Calendar.MILLISECOND, 0);
	        Date fechaActualInicioDia = calActual.getTime();

	        Calendar calReservaInicio = Calendar.getInstance();
	        calReservaInicio.setTime(reserva.getFechainicio());
	        calReservaInicio.set(Calendar.HOUR_OF_DAY, 0);
	        calReservaInicio.set(Calendar.MINUTE, 0);
	        calReservaInicio.set(Calendar.SECOND, 0);
	        calReservaInicio.set(Calendar.MILLISECOND, 0);

	        if (calReservaInicio.getTime().before(fechaActualInicioDia)) {
	            model.addAttribute("error", "La fecha de reservación no puede ser una fecha anterior a hoy.");

	            if(idReserva!=0) {
	                model.addAttribute("idReserva", idReserva);
	            }else{
	                model.addAttribute("idReserva", 0);
	            }
	            Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionReservado);
	            Hotel hotel = habitacion.getIdHotelHabitacion();

	            model.addAttribute("hotel", hotel);
	            model.addAttribute("habitacion", habitacion);
	            model.addAttribute("accion","Reservando una habitación");
	            model.addAttribute("act", 0);
	            model.addAttribute("cantDias", cantidadDias);
	            return "user/reservar/hacerReservacionForm";
	        }

	        // Obtener la habitación para las validaciones
	        Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionReservado);

	        // Calcular la fecha de fin de la NUEVA reserva de manera estricta
	        Calendar calendarFinNuevaReserva = Calendar.getInstance();
	        calendarFinNuevaReserva.setTime(reserva.getFechainicio());
	        
	        calendarFinNuevaReserva.add(Calendar.DAY_OF_YEAR, cantidadDias);
	        Date fechaFinNuevaReserva = calendarFinNuevaReserva.getTime();
	        calendarFinNuevaReserva.add(Calendar.DAY_OF_YEAR, 1);
	        Date fechaFinNuevaReservaPlus = calendarFinNuevaReserva.getTime();

	        // --- LÓGICA DE VALIDACIÓN Y SUGERENCIA DE FECHA ---
	        StringBuilder errorMessage = new StringBuilder();
	        boolean isSolapamiento = false;

	        // 2.- Validar solapamiento con reservas existentes en la misma habitación
	        List<Reserva> reservasPosiblementeSolapadas = reservaService.findReservasSolapadas(habitacionReservado, reserva.getFechainicio(), fechaFinNuevaReservaPlus);
	        Set<String> fechasReservadasUnicas = new HashSet<>();
	        List<Reserva> otrasReservasSolapadas = new ArrayList<>();

	        for (Reserva r : reservasPosiblementeSolapadas) {
	            if (idReserva == 0 || !r.getId().equals(idReserva)) { // Excluir la reserva actual en edición
	                if ( (r.getFechainicio().before(fechaFinNuevaReservaPlus) || r.getFechainicio().equals(fechaFinNuevaReservaPlus)) &&
	                     (r.getFechafin().after(reserva.getFechainicio()) || r.getFechafin().equals(reserva.getFechainicio())) &&
	                     !r.getEstado().equalsIgnoreCase("Terminado") && !r.getEstado().equalsIgnoreCase("Cancelado") ) {
	                    otrasReservasSolapadas.add(r);
	                    fechasReservadasUnicas.add(formatoFechaUsuario.format(r.getFechainicio()) + " al " + formatoFechaUsuario.format(r.getFechafin()));
	                    isSolapamiento = true;
	                }
	            }
	        }
	        List<String> fechasReservadasLista = new ArrayList<>(fechasReservadasUnicas);

	        if (!otrasReservasSolapadas.isEmpty()) {
	            errorMessage.append("Lo sentimos, la fecha escogida es inválida. Las siguientes fechas ya están reservadas:\n").append(String.join(", ", fechasReservadasLista));
	        }

	        // 3.- Validar solapamiento con mantenimientos en la misma habitación
	        List<Mantenimiento> mantenimientosEnRango = mantenimientoService.findMantenimientosSolapados(habitacionReservado, reserva.getFechainicio(), fechaFinNuevaReserva);
	        List<String> fechasMantenimiento = new ArrayList<>();
	        for (Mantenimiento m : mantenimientosEnRango) {
	            if ((m.getFecha().after(reserva.getFechainicio()) || m.getFecha().equals(reserva.getFechainicio())) &&
	                (m.getFecha().before(fechaFinNuevaReservaPlus) || m.getFecha().equals(fechaFinNuevaReservaPlus))) {
	                fechasMantenimiento.add(formatoFechaUsuario.format(m.getFecha()));
	                isSolapamiento = true;
	            }
	        }
	        fechasMantenimiento = fechasMantenimiento.stream().distinct().collect(Collectors.toList());

	        if (!fechasMantenimiento.isEmpty()) {
	            if (errorMessage.length() > 0) { // Si ya hay un error de reserva, añadir un salto de línea
	                errorMessage.append("\n");
	            }
	            errorMessage.append("La fecha de reservación es inválida. Las siguientes fechas tienen mantenimiento programado: ").append(String.join(", ", fechasMantenimiento));
	        }


	        if (isSolapamiento) {
	            // Buscar una fecha de sugerencia
	            Calendar suggestedStartDate = Calendar.getInstance();
	            suggestedStartDate.setTime(reserva.getFechainicio()); // Empezar desde la fecha original
	            suggestedStartDate.add(Calendar.DAY_OF_YEAR, 1); // Sumar un día para la primera sugerencia

	            Date tempSuggestedEndDate;
	            boolean foundSuggestion = false;
	            int maxAttempts = 365; // Límite para evitar bucles infinitos, buscar hasta un año en el futuro

	            for (int i = 0; i < maxAttempts; i++) {
	                // Calcular la fecha de fin para la sugerencia actual
	                Calendar tempCalendar = Calendar.getInstance();
	                tempCalendar.setTime(suggestedStartDate.getTime());
	                tempCalendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
	                tempSuggestedEndDate = tempCalendar.getTime();

	                // Verificar si la fecha sugerida es posterior a la fecha actual (importante para evitar sugerir fechas pasadas)
	                if (suggestedStartDate.getTime().before(fechaActualInicioDia)) {
	                    suggestedStartDate.add(Calendar.DAY_OF_YEAR, 1); // Si la sugerencia se vuelve una fecha pasada, avanza al siguiente día
	                    continue;
	                }

	                if (esRangoFechasDisponible(habitacionReservado, suggestedStartDate.getTime(), tempSuggestedEndDate, idReserva)) {
	                    errorMessage.append("\nSe te ha sugerido la siguiente fecha disponible: ");
	                    errorMessage.append(formatoFechaUsuario.format(suggestedStartDate.getTime()));
	                    errorMessage.append(" al ");
	                    errorMessage.append(formatoFechaUsuario.format(tempSuggestedEndDate));
	                    foundSuggestion = true;
	                    break;
	                }
	                suggestedStartDate.add(Calendar.DAY_OF_YEAR, 1); // Si no está disponible, probar el día siguiente
	            }

	            if (!foundSuggestion) {
	                errorMessage.append("\nNo se pudo encontrar una fecha disponible en un futuro cercano.");
	            }

	            model.addAttribute("error", errorMessage.toString());

	            // Volver a cargar atributos necesarios para el formulario
	            if(idReserva!=0) {
	                model.addAttribute("idReserva", idReserva);
	            }else{
	                model.addAttribute("idReserva", 0);
	            }
	            Hotel hotel = habitacion.getIdHotelHabitacion();

	            model.addAttribute("hotel", hotel);
	            model.addAttribute("habitacion", habitacion);
	            model.addAttribute("accion","Reservando una habitación");
	            model.addAttribute("act", 0);
	            model.addAttribute("cantDias", cantidadDias);

	            return "user/reservar/hacerReservacionForm";
	        }
	        // --- FIN LÓGICA DE VALIDACIÓN Y SUGERENCIA DE FECHA ---


	        // Si no hay solapamiento, proceder con el guardado
	        Hotel hotel = hotelService.buscarHotelID(hotelReservado);

	        String username = principal.getName();
	        Usuario usuarioLogueado = usuarioService.buscarPorUsername(username);

	        Cliente clienteReserva = null;
	        if (usuarioLogueado.getClienteligado() != null && !usuarioLogueado.getClienteligado().isEmpty()) {
	            clienteReserva = usuarioLogueado.getClienteligado().get(0);
	        } else {
	            redirectAttributes.addFlashAttribute("mensajeError", "Error: No se encontró un perfil de cliente asociado a su usuario.");
	            return "redirect:/";
	        }

	        reserva.setFechafin(fechaFinNuevaReserva); //La fechas corregidas
	        reserva.setIdReservaCliente(clienteReserva);
	        reserva.setIdReservaHabitacion(habitacion);
	        reserva.setEstado("Reservado");

	        //Guardado inicial para obtener el ID
	        reservaService.guardarReserva(reserva);

	        //Generar el código de reserva con el ID generado
	        String codigoReserva = "HO" + hotel.getId() + "A" + habitacion.getId() + reserva.getId() + "C" +  clienteReserva.getId();
	        reserva.setCodigoReserva(codigoReserva);

	        //Guardar la reserva nuevamente con el código
	        reservaService.guardarReserva(reserva);


	        return "redirect:/user/reservar/detalleReserva/1/"+reserva.getId();
	    }	
		
		//Para no repetir la carga de datos:
		private void cargarDatosFormulario(Model model, Integer idReserva, Integer habitacionReservado) {
			if(idReserva != null && idReserva != 0) {
				Reserva reservaExistente = reservaService.buscarReservaId(idReserva);
				model.addAttribute("idReserva", idReserva);
				model.addAttribute("reserva", reservaExistente);
			} else {
				model.addAttribute("idMante", 0);
				model.addAttribute("reserva", new Reserva());
			}

			if (habitacionReservado != null) {
				Habitacion habitacion = habitacionService.buscarHabitacionID(habitacionReservado);
				Hotel hotel = habitacion.getIdHotelHabitacion();
				model.addAttribute("hotel", hotel);
				model.addAttribute("habitacion", habitacion);
				model.addAttribute("accion","Reservando una habitación");
				model.addAttribute("act", 0);
			}
		}		
		//-----
		
		@InitBinder
		public void initBinder(WebDataBinder webDataBinder) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		}		
		
		//Para mostrar el detalle de la reserva
		@GetMapping("/detalleReserva/{tipoHistorial}/{idReserva}")
		public String detalleReserva(Principal principal, @PathVariable("idReserva") int idReserva,
				@PathVariable("tipoHistorial") int tipoHistorial, Model model, RedirectAttributes redirectAttributes) {
			
			Reserva reserva = reservaService.buscarReservaId(idReserva);
			
			model.addAttribute("titulo", "Reservación con código: " + reserva.getCodigoReserva());
			
			model.addAttribute("reserva", reserva);
			
			//Para el cliente
			Cliente cliente = reserva.getIdReservaCliente();
			model.addAttribute("cliente", cliente);
			
			
			//Para hotel y habitacion
			Habitacion habitacion = reserva.getIdReservaHabitacion();
			model.addAttribute("habitacion", habitacion);
			
			Hotel hotel = habitacion.getIdHotelHabitacion();
			model.addAttribute("hotel", hotel);
			
			model.addAttribute("tipoHistorial", tipoHistorial);
			
			
			return "user/reservar/detalleReserva";
		}	
		
		
		@GetMapping("/seleccionarHistorial")
		public String mostrarSeleccionHistorial(Model model) {
			
			return "user/reservar/selecOpc";
		}
		

		//Me mandará al historial de reservas, pero solo mostrará aquellas reservas pasadas, vaya ya con su estado "Terminado" o "Cancelado"
		@GetMapping("/verHistorialReservas")
		public String verHistorialReservas(Principal principal, Model model) {
	        String username = principal.getName(); //Obtiene el username del usuario logueado
	        Usuario usuarioLogueado = usuarioService.buscarPorUsername(username); // Busca el Usuario completo por su username
	        
	        Cliente clienteReserva = usuarioLogueado.getClienteligado().get(0);	
	        
	        List<Reserva> reservas = reservaService.obtenerHistorialReservasCliente(clienteReserva.getId());

	        model.addAttribute("reservas", reservas);
	        
	        //0 significa que vamos a filtrar los que tengas los estados "Terminado" o "Cancelado", mientras que 1 los estados "Reservado" o "Proceso"
	        model.addAttribute("tipoHistorial", 0);
	        
	        model.addAttribute("cliente", clienteReserva);
	        
			
			return "user/reservar/historialReservas";
		}
		
		
		
		@GetMapping("/verReservasPendientes")
		public String verReservasPendientes(Principal principal, Model model) {
			
	        String username = principal.getName(); //Obtiene el username del usuario logueado
	        Usuario usuarioLogueado = usuarioService.buscarPorUsername(username); // Busca el Usuario completo por su username
	        
	        Cliente clienteReserva = usuarioLogueado.getClienteligado().get(0);	
	        
	        List<Reserva> reservas = reservaService.obtenerReservasPendientesCliente(clienteReserva.getId());

	        model.addAttribute("reservas", reservas);
	        
	        //0 significa que vamos a filtrar los que tengas los estados "Terminado" o "Cancelado", mientras que 1 los estados "Reservado" o "Proceso"
	        model.addAttribute("tipoHistorial", 1);
	        
	        model.addAttribute("cliente", clienteReserva);
	        
			
			return "user/reservar/historialReservas";			
			
			
		}
				
		//Para los filtrados:
		@PostMapping("/filtrarHistorialReservas")
		public String filtrarHistorialReservas(Principal principal,
											   @RequestParam("tipoHistorial") int tipoHistorial,
											   @RequestParam(value = "filterType", required = false) String filterType,
											   @RequestParam(value = "hotel", required = false) String hotel,
											   @RequestParam(value = "numeroHabitacion", required = false) Integer numeroHabitacion,
											   @RequestParam(value = "precioMinimo", required = false) Double precioMinimo,
											   @RequestParam(value = "precioMaximo", required = false) Double precioMaximo,
											   @RequestParam(value = "fechaInicio", required = false) String fechaInicioStr,
											   @RequestParam(value = "fechaFin", required = false) String fechaFinStr,
											   @RequestParam(value = "ordenPrecio", required = false) String ordenPrecio,
											   @RequestParam(value = "ordenFecha", required = false) String ordenFecha,
											   @RequestParam(value = "codigoReserva", required = false) String codigoReserva,
											   Model model) {

	        String username = principal.getName();
	        Usuario usuarioLogueado = usuarioService.buscarPorUsername(username);
	        Cliente clienteReserva = usuarioLogueado.getClienteligado().get(0);
	        Integer clienteId = clienteReserva.getId();
	        List<String> estadosFiltrar = (tipoHistorial == 0) ? Arrays.asList("Terminado", "Cancelado") : Arrays.asList("Reservado", "Proceso");
	        List<Reserva> reservasFiltradas = new ArrayList<>();
	        StringBuilder criteriosFiltro = new StringBuilder("Filtrado por: ");
	        boolean filtroAplicado = false;

	        Date fechaInicio = null;
	        Date fechaFin = null;
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
	            try {
	                fechaInicio = dateFormat.parse(fechaInicioStr);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }

	        if (fechaFinStr != null && !fechaFinStr.isEmpty()) {
	            try {
	                fechaFin = dateFormat.parse(fechaFinStr);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }


	        if (filterType != null) {
	            switch (filterType) {
	                case "hotel":
	                    reservasFiltradas = reservaService.filtrarHistorialReservasPorHotel(clienteId, estadosFiltrar, hotel);
	                    if (hotel != null && !hotel.isEmpty()) {
	                        criteriosFiltro.append("Hotel: ").append(hotel).append(", ");
	                        filtroAplicado = true;
	                    }
	                    break;
	                case "habitacion":
	                    reservasFiltradas = reservaService.filtrarHistorialReservasPorHabitacion(clienteId, estadosFiltrar, numeroHabitacion);
	                    if (numeroHabitacion != null) {
	                        criteriosFiltro.append("Habitación: ").append(numeroHabitacion).append(", ");
	                        filtroAplicado = true;
	                    }
	                    break;
	                case "precio":
	                    reservasFiltradas = reservaService.filtrarHistorialReservasPorPrecio(clienteId, estadosFiltrar, precioMinimo, precioMaximo, ordenPrecio);
	                    if (precioMinimo != null && precioMaximo != null) {
	                        criteriosFiltro.append("Precio entre ").append(precioMinimo).append(" y ").append(precioMaximo);
	                        if (ordenPrecio != null && !ordenPrecio.isEmpty()) {
	                            criteriosFiltro.append(" (orden ").append(ordenPrecio).append("), ");
	                        } else {
	                            criteriosFiltro.append(", ");
	                        }
	                        filtroAplicado = true;
	                    }
	                    break;
	                case "fecha":
	                    reservasFiltradas = reservaService.filtrarHistorialReservasPorFecha(clienteId, estadosFiltrar, fechaInicio, fechaFin, ordenFecha);
	                    if (fechaInicio != null && fechaFin != null) {
	                        criteriosFiltro.append("Fecha entre ").append(dateFormat.format(fechaInicio)).append(" y ").append(dateFormat.format(fechaFin));
	                        if (ordenFecha != null && !ordenFecha.isEmpty()) {
	                            criteriosFiltro.append(" (orden ").append(ordenFecha).append("), ");
	                        } else {
	                            criteriosFiltro.append(", ");
	                        }
	                        filtroAplicado = true;
	                    }
	                    break;
	                case "codigo":
	                    reservasFiltradas = reservaService.filtrarHistorialReservasPorCodigo(clienteId, estadosFiltrar, codigoReserva);
	                    if (codigoReserva != null && !codigoReserva.isEmpty()) {
	                        criteriosFiltro.append("Código de reserva: ").append(codigoReserva).append(", ");
	                        filtroAplicado = true;
	                    }
	                    break;
	                default:
	                    reservasFiltradas = reservaService.obtenerHistorialReservasClientePorEstados(clienteId, estadosFiltrar);
	                    break;
	            }
	        } else {
	            reservasFiltradas = reservaService.obtenerHistorialReservasClientePorEstados(clienteId, estadosFiltrar);
	        }

	        if (!filtroAplicado) {
	            criteriosFiltro = new StringBuilder("Mostrando todas las reservas ");
	            if (tipoHistorial == 0) {
	                criteriosFiltro.append("con estado Terminado o Cancelado.");
	            } else {
	                criteriosFiltro.append("con estado Reservado o Proceso.");
	            }
	        } else {
	            // Eliminar la coma y el espacio final si se aplicó algún filtro
	            if (criteriosFiltro.toString().endsWith(", ")) {
	                criteriosFiltro.delete(criteriosFiltro.length() - 2, criteriosFiltro.length());
	            }
	        }

	        model.addAttribute("mensajeFiltro", criteriosFiltro.toString());
	        model.addAttribute("reservas", reservasFiltradas);
	        model.addAttribute("tipoHistorial", tipoHistorial);
	        model.addAttribute("cliente", clienteReserva);
	        return "user/reservar/historialReservas";
		}
		
		//Quita filtros
		@GetMapping("/limpiar/{tipoHistorial}")
		public String limpiarFiltros(Principal principal, Model model,
				@PathVariable("tipoHistorial") int tipoHistorial) {
			
			if(tipoHistorial==0) {
				return "redirect:/user/reservar/verHistorialReservas";
			}else {
				return "redirect:/user/reservar/verReservasPendientes";
				
			}
			
		
			
			
		}		
		
		//Para cancelar reservación
		@GetMapping("/cancelarReserva/{idReserva}/{tipoHistorial}")
		public String cancelarReserva(Principal principal, @PathVariable("idReserva") int idReserva, Model model, RedirectAttributes redirectAttributes) {

			Reserva reserva = reservaService.buscarReservaId(idReserva);

			if (reserva == null) {
				redirectAttributes.addFlashAttribute("mensajeError", "No se encontró la reserva con el ID proporcionado.");
				return "redirect:/user/reservar/historialReservas?tipoHistorial=1"; // Redirigir a reservas activas
			}

			Date fechaInicioReserva = reserva.getFechainicio();
			Date hoy = new Date();

			// Calcular la diferencia en milisegundos
			long diferenciaMillis = fechaInicioReserva.getTime() - hoy.getTime();

			// Convertir la diferencia a días
			long diferenciaDias = diferenciaMillis / (24 * 60 * 60 * 1000);

			// Verificar si faltan más de dos días para la fecha de inicio
			if (diferenciaDias < 2) {
				redirectAttributes.addFlashAttribute("mensajeError", "No se puede cancelar la reserva. Debe hacerlo al menos con dos días de anticipación.");
				return "redirect:/user/reservar/detalleReserva/1/" + idReserva; // Redirigir al detalle de la reserva
			}

			if (!reserva.getEstado().equalsIgnoreCase("Cancelado") && !reserva.getEstado().equalsIgnoreCase("Terminado")) {
				reserva.setEstado("Cancelado");
				reservaService.guardarReserva(reserva);
				redirectAttributes.addFlashAttribute("mensajeExito", "La reservación con código: " + reserva.getCodigoReserva() + " ha sido cancelada.");
			} else {
				redirectAttributes.addFlashAttribute("mensajeInfo", "La reservación con código: " + reserva.getCodigoReserva() + " ya estaba cancelada o terminada.");
			}

			return "redirect:/user/reservar/detalleReserva/1/" + idReserva;
		}
		
		
		//Para editar una reservación
		@GetMapping("/cambiarReservacion/{idReserva}/{tipoHistorial}")
		public String cambiarReserva(Principal principal, @PathVariable("idReserva") int idReserva, Model model, RedirectAttributes redirectAttributes) {

			Reserva reserva = reservaService.buscarReservaId(idReserva);

			if (reserva == null) {
				redirectAttributes.addFlashAttribute("mensajeError", "No se encontró la reserva con el ID proporcionado.");
				return "redirect:/user/reservar/historialReservas?tipoHistorial=1"; // Redirigir a reservas activas
			}

			Date fechaInicioReserva = reserva.getFechainicio();
			Date hoy = new Date();

			// Calcular la diferencia en milisegundos
			long diferenciaMillis = fechaInicioReserva.getTime() - hoy.getTime();

			// Convertir la diferencia a días
			long diferenciaDias = diferenciaMillis / (24 * 60 * 60 * 1000);

			// Verificar si faltan más de dos días para la fecha de inicio
			if (diferenciaDias < 2) {
				redirectAttributes.addFlashAttribute("mensajeError", "No se puede cambiar la reservación. Debe hacerlo al menos con dos días de anticipación.");
				return "redirect:/user/reservar/detalleReserva/1/" + idReserva; // Redirigir al detalle de la reserva
			}

			if (!reserva.getEstado().equalsIgnoreCase("Cancelada") && !reserva.getEstado().equalsIgnoreCase("Terminado")) {
				//Acá se abre el formulario para editar
				if(idReserva!=0) {
					reserva = reservaService.buscarReservaId(idReserva);

					model.addAttribute("idReserva", idReserva);
					model.addAttribute("reserva", reserva);

					// Calcular la diferencia de días
					Date fechaInicio = reserva.getFechainicio();
					Date fechaFin = reserva.getFechafin();

					if (fechaInicio != null && fechaFin != null) {
						long diferenciaMillis_ = fechaFin.getTime() - fechaInicio.getTime();
						long diferenciaDias_ = diferenciaMillis_ / (24 * 60 * 60 * 1000);
						model.addAttribute("cantDias", diferenciaDias_);
					} else {
						model.addAttribute("cantDias", 1); // Valor por defecto si las fechas son nulas
					}

				}else{
					model.addAttribute("idMante", 0);
					model.addAttribute("cantDias", 1); // Valor por defecto para una nueva reserva
				}


				//La acción y a qué hotel se le está reservando:
				Habitacion habitacion = reserva.getIdReservaHabitacion();
				Hotel hotel = habitacion.getIdHotelHabitacion();

				model.addAttribute("hotel", hotel);
				model.addAttribute("habitacion", habitacion);


				model.addAttribute("accion","Editando reservación " + reserva.getCodigoReserva());


				//Para indicar que es editado
				model.addAttribute("act", 1);


				return "user/reservar/hacerReservacionForm";
				
				
			} else {
				redirectAttributes.addFlashAttribute("mensajeInfo", "La reservación con código: " + reserva.getCodigoReserva() + " ya estaba cancelada o terminada.");
			}

			return "redirect:/user/reservar/detalleReserva/1/" + idReserva;
		}		
		
	

}
