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

import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Telefono;
import carlos.mejia.proyectohoteles.service.HabitacionService;
import carlos.mejia.proyectohoteles.service.HotelService;

@Controller
@RequestMapping("/habitacion")
public class HabitacionController {
	
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HabitacionService habitacionService;
	
	@GetMapping("/admin/agregarHabitacion/{idHotel}")
	public String editarHotel(@PathVariable("idHotel") int idHotel, Model model, Habitacion habitacion) {
		
		//La acción y a qué hotel se le está agreganda:
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		model.addAttribute("accion","Agregando una habitación a " + hotel.getNombre());
		
		//Para los datos del hotel:
		model.addAttribute("hotel", hotel.getId());
		
		//Para indicar que es guardado
		model.addAttribute("act", 0);
		
		return "admin/habitacion/formHabitacion";
	}
	
	//Para guardar
	@PostMapping("/admin/guardarHabitacion")
	public String guardar(Habitacion habitacion, BindingResult result, Model model, RedirectAttributes redirectAttributes,
	                      @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
	                      @RequestParam(value = "hotelAsignado", required = false) Integer hotelAsignado,
	                      @RequestParam(value = "act", required = false) Integer act) {	

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			Hotel hotel = hotelService.buscarHotelID(hotelAsignado);
			model.addAttribute("accion","Agrando una habitación a " + hotel.getNombre());
			//Para los datos del hotel:
			model.addAttribute("hotel", hotel);
			return "admin/habitacion/formHabitacion";
		}

		if (imagenFile != null && !imagenFile.isEmpty()) {
			try {
				String nombreArchivo = imagenFile.getOriginalFilename();
				Path rutaArchivo = Paths.get("/app/imagenesHotel/habitaciones").resolve(nombreArchivo).normalize();
				Files.copy(imagenFile.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
				habitacion.setFotoHabitacion(nombreArchivo);
			} catch (IOException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar la imagen.");
				
				//Cuidado aquí
				return "redirect:/hotel/admin/listaHoteles";
			}
		} else {
			if (habitacion.getFotoHabitacion() != null && !habitacion.getFotoHabitacion().isEmpty()) {
				habitacion.setFotoHabitacion(habitacion.getFotoHabitacion().replace(",", "").trim());
			} else {
				Habitacion habitacionExistente = habitacionService.buscarHabitacionID(habitacion.getId());
				if (habitacionExistente != null) {
					habitacion.setFotoHabitacion(habitacionExistente.getFotoHabitacion());
				}
			}
		}

		habitacion.setEstado(1);
		Hotel hotelasignado = hotelService.buscarHotelID(hotelAsignado);
		habitacion.setIdHotelHabitacion(hotelasignado);
		
		habitacionService.guardarHabitacion(habitacion);


		//Editemos el precio promedio de dicho hotel
		double precioProme=0;
		
		for(int i = 0; i<hotelasignado.getHabitaciones().size(); i++) {
			
			precioProme = precioProme + hotelasignado.getHabitaciones().get(i).getPrecioNoche();
		}
		
		if(act==0) {
			precioProme = precioProme + habitacion.getPrecioNoche();
			
			precioProme = precioProme/(hotelasignado.getHabitaciones().size()+1);		
		}else {
			
			precioProme = precioProme/(hotelasignado.getHabitaciones().size());				
			
		}

		hotelasignado.setPrecioPromedio(precioProme);
		
		hotelService.guardarHotel(hotelasignado);
		
	    redirectAttributes.addFlashAttribute("mensajeExito", "Habitación guardada con éxito");
	    return "redirect:/habitacion/admin/listaHabitaciones/" + hotelAsignado;
	}		
	
	//Lista de habitaciones de un hotel
	@GetMapping("/admin/listaHabitaciones/{idHotel}")
	public String mostrarLista(@PathVariable("idHotel") int idHotel, Model model) {
		//El hotel específico
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		//El Título
		model.addAttribute("tituloLista", "Lista de habitaciones de " + hotel.getNombre());
		
		//El listado de todas las habitaciones
		List<Habitacion> lista = hotel.getHabitaciones();
		
		model.addAttribute("listaHabitaciones", lista);
		
		//Para el hotel como tal:
		model.addAttribute("hotel", hotel);
		
		
		
		return "admin/habitacion/listaHabitaciones";		
		
		
	}
		
	//Para editar
	@GetMapping("/admin/editar/{idHotel}/{idHabitacion}")
	public String editarHotel(@PathVariable("idHotel") int idHotel, 
			@PathVariable("idHabitacion") int idHabitacion, Model model, RedirectAttributes redirectAttributes) {
		
		//La habitacion a editar
		Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
		model.addAttribute("habitacion", habitacion);
			
	    
		//La acción y a qué hotel se le está agreganda:
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		model.addAttribute("accion","Editando habitación del " + hotel.getNombre());
		
		//Para los datos del hotel:
		model.addAttribute("hotel", hotel.getId());
		
		
		model.addAttribute("act", 1);
		
		
		return "admin/habitacion/formHabitacion";
	}		
	
	
	
	//B U S Q U E D A S ---------------------------------------------------------------------------
	@PostMapping("/admin/buscarListaHabitaciones")
	public String buscarHabitaciones(
	        @RequestParam("idHotelPertenece") int idHotel, // Recibimos el idHotel
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
	    
	    
	    
	    
	    return "admin/habitacion/listaHabitaciones";
	}
	
	//Cambiar de estado
	
	@GetMapping("/admin/cambiarEstado/{idHotel}/{idHabitacion}")
	public String buscarSeleccionarHotel(@PathVariable("idHotel") int idHotel, 
			@PathVariable("idHabitacion") int idHabitacion, RedirectAttributes redirectAttributes) {
		
		Hotel hotel = hotelService.buscarHotelID(idHotel);
		Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
		
		int estado = habitacion.getEstado();
		
		if(estado == 1) {
			habitacion.setEstado(0);
			
		}else {
			
			habitacion.setEstado(1);
		}
		
		habitacionService.guardarHabitacion(habitacion);
		
		redirectAttributes.addFlashAttribute("mensajeExito", "La habitación de " + hotel.getNombre()+" ha cambiado de estado.");
		
		return "redirect:/habitacion/admin/listaHabitaciones/" + idHotel;
	}	
	
	
	//Para ver más
	@GetMapping("/admin/verMas/{id}")
	public String verDetalle(@PathVariable("id") int idHabitacion, Model model) {
		
		Habitacion habitacion = habitacionService.buscarHabitacionID(idHabitacion);
	
		Hotel hotel = habitacion.getIdHotelHabitacion();
		
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("habitacion", habitacion);
		
		model.addAttribute("titulo", "Habitación número "+ habitacion.getNumeroHabitacion());
		
		
		
		return "admin/habitacion/detalleHabitacion";
	}		
	

}
