package carlos.mejia.proyectohoteles.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Telefono;
import carlos.mejia.proyectohoteles.service.HotelService;
import carlos.mejia.proyectohoteles.service.TelefonoService;





@Controller
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private TelefonoService telefonoService;	
	
	@GetMapping("/admin/listaHoteles")
	public String mostrarLista(Model model) {
		//Para los hoteles 
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		//Para su lista de teléfonos:
		
		
		
		return "admin/hotel/panelAdminHotel";
		
	}	
	
	
	@GetMapping("/admin/agregarHotel")
	public String agregarHotelForm(Model model, Hotel hotel) {
	
		
		model.addAttribute("accion","Agregando hotel");
		
		return "admin/hotel/formHotel";		
		
	}
	
	@PostMapping("/admin/guardar")
	public String guardar(Hotel hotel, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                      @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
	                      @RequestParam(value = "numerosTelefono[]", required = false) List<String> telefonosStr) {	// Recibimos la lista de teléfonos

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			model.addAttribute("accion", "Agregando hotel");
			return "admin/hotel/formHotel";
		}

		if (imagenFile != null && !imagenFile.isEmpty()) {
			try {
				String nombreArchivo = imagenFile.getOriginalFilename();
				Path rutaArchivo = Paths.get("/app/imagenesHotel/hoteles").resolve(nombreArchivo).normalize();
				Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
				hotel.setFotoHotel(nombreArchivo);
			} catch (IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen.");
				return "redirect:/hotel/admin/listaHoteles";
			}
		} else {
			if (hotel.getFotoHotel() != null && !hotel.getFotoHotel().isEmpty()) {
				hotel.setFotoHotel(hotel.getFotoHotel().replace(",", "").trim());
			} else {
				Hotel hotelExistente = hotelService.buscarHotelID(hotel.getId());
				if (hotelExistente != null) {
					hotel.setFotoHotel(hotelExistente.getFotoHotel());
				}
			}
		}

		
		if (hotel.getId() == null) { //Si el ID es null, es un hotel nuevo
			hotel.setEstado(1); 
		} else { 
			if (hotel.getEstado() == 0) {
				hotel.setEstado(0); 
			} else {
				hotel.setEstado(1); 
			}
		}
		
		
		
		hotelService.guardarHotel(hotel); //Primero guardamos el hotel para obtener su ID

		telefonoService.eliminarTelefonosPorHotel(hotel);

	    //Guardar los nuevos teléfonos (si hay alguno)
	    if (telefonosStr != null && !telefonosStr.isEmpty()) {
	        List<Telefono> telefonosParaGuardar = telefonosStr.stream()
	                .map(telefono -> {
	                    Telefono tel = new Telefono();
	                    tel.setTelefono(telefono.trim());
	                    tel.setIdHotelTelefono(hotel);
	                    return tel;
	                })
	                .collect(Collectors.toList());
	        telefonoService.guardarListaTel(telefonosParaGuardar);
	    }

	    redirectAttributes.addFlashAttribute("mensajeExito", "Hotel guardado con éxito");
	    return "redirect:/hotel/admin/listaHoteles";
	}	
	
	@GetMapping("/admin/editar/{id}")
	public String editarHotel(@PathVariable("id") int idHotel, Model model, RedirectAttributes redirectAttributes) {
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		model.addAttribute("hotel", hotel);
		
		model.addAttribute("accion","Editando hotel");
		
		
	    List<Telefono> telefonos = hotel.getTelefonos();

	    model.addAttribute("telefonosHotel", telefonos); // Pasar la lista de teléfonos al formulario	
		
		
		return "admin/hotel/formHotel";
	}	
	
	//Opciones de filtrado:
	@PostMapping("/admin/buscar")
	public String buscarHoteles(
	        @RequestParam(value = "tipoBusca", required = false) String tipoBusca,
	        @RequestParam(value = "valorBusca", required = false) String valorBusca,
	        @RequestParam(value = "ordenar", required = false) String ordenar,
	        @RequestParam(value = "min", required = false) double minPrecio,
	        @RequestParam(value = "max", required = false) double maxPrecio,
	        @RequestParam(value = "estado", required = false) String estadoBusqueda, // Este parámetro lo usaremos si el filtro es por estado
	        Model model
	) {
		
	    System.out.println("Tipo de búsqueda: " + tipoBusca);
	    System.out.println("Valor de búsqueda: " + valorBusca);
	    System.out.println("Ordenar por: " + ordenar);
	    System.out.println("Precio mínimo: " + minPrecio);
	    System.out.println("Precio máximo: " + maxPrecio);
	    System.out.println("Estado de búsqueda: " + estadoBusqueda);
	    
	    
	    
	    

	    //List<Hotel> resultados = hotelService.buscarHoteles(tipoBusca, valorBusca, ordenar, minPrecio, maxPrecio, estadoBusqueda);

	    //model.addAttribute("listaHoteles", resultados);
	    List<Hotel> lista = hotelService.todosHoteles();;
	    if(tipoBusca.equalsIgnoreCase("precio")) {
	    	lista = hotelService.filtrarHotelPorPrecioPromedio(minPrecio, maxPrecio, ordenar);
	    }else {
	    	if(tipoBusca.equalsIgnoreCase("estado")) {
	    		lista = hotelService.filtrarHotelPorEstado(ordenar, estadoBusqueda);
	    		System.out.println("Filtro estado");
	    		
	    	}else {
	    		lista = hotelService.filtrarHotelPor(tipoBusca, ordenar, valorBusca);
	    	}
			
				    	
	    }
	    
	    model.addAttribute("listaHoteles", lista);
	    
	    return "admin/hotel/panelAdminHotel"; // Volvemos a la página de la lista con los resultados
	}	
	
	//Seleccionar un hotel para añadir habitación
	@GetMapping("/admin/selecHotelHabi")
	public String seleccionarHotelHabitacion(Model model) {
		
		/*List<Categoria> listaC = serviceCate.buscarTodasCates();
		model.addAttribute("listaC", listaC);*/		
		List<Hotel> lista = hotelService.todosHoteles();
		model.addAttribute("listaHoteles", lista);
		
		return "admin/hotel/agregarHabiSelecHote";		
		
	}	
	
	//Filtrar la selección
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

	    return "admin/hotel/agregarHabiSelecHote"; // Volvemos a la página con los resultados filtrados
	}	
	
	//Para cambiarle el estado:
	
	
	@GetMapping("/admin/cambiarEstado/{id}")
	public String buscarSeleccionarHotel(@PathVariable("id") int idHotel, RedirectAttributes redirectAttributes) {
		
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		
		int estado = hotel.getEstado();
		
		if(estado == 1) {
			hotel.setEstado(0);
			
		}else {
			
			hotel.setEstado(1);
		}
		
		hotelService.guardarHotel(hotel);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "" + hotel.getNombre()+" ha cambiado de estado.");
		
		return "redirect:/hotel/admin/listaHoteles";
	}

	//Para ver más
	@GetMapping("/admin/verMas/{id}")
	public String verDetalle(@PathVariable("id") int idHotel, Model model) {
		
		Hotel hotel = hotelService.buscarHotelID(idHotel);
	
		model.addAttribute("hotel", hotel);
		model.addAttribute("listaHabitaciones", hotel.getHabitaciones());
		
		
		return "admin/hotel/detalleHotelAdmin";
	}	
	
}
