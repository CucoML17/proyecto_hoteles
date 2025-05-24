package carlos.mejia.proyectohoteles.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.repository.HabitacionRepository;

@Service
public class HabitacionServiceJpa implements HabitacionService{

	@Autowired
	HabitacionRepository habiRepo;	
	
	@Override
	public int guardarHabitacion(Habitacion habitacion) {
		// TODO Auto-generated method stub
		Habitacion habiImp = habiRepo.save(habitacion);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        return habiImp.getId();
	}

	@Override
	public List<Habitacion> todasHabitaciones() {
		// TODO Auto-generated method stub
		return habiRepo.findAll();
	}

	@Override
	public List<Habitacion> habitacionesActivas() {
		// TODO Auto-generated method stub
		return habiRepo.findByEstado(1);
	}

	@Override
	public Habitacion buscarHabitacionID(Integer idHabitacion) {
		Optional<Habitacion> optional = habiRepo.findById(idHabitacion);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}
	
	
    @Override
    public List<Habitacion> filtrarHabitaciones(
            int idHotel, String numeroHabitacionStr, String tipo, Double minPrecio, Double maxPrecio, Integer capacidad, String ordenCampo, String ordenDireccion
    ) {
        Sort sort = null;
        if (ordenCampo != null && !ordenCampo.isEmpty()) {
            sort = Sort.by(ordenCampo);
            if ("desc".equalsIgnoreCase(ordenDireccion)) {
                sort = sort.descending();
            }
        }

        Integer numeroHabitacion = null;
        if (numeroHabitacionStr != null && !numeroHabitacionStr.isEmpty()) {
            try {
                numeroHabitacion = Integer.parseInt(numeroHabitacionStr);
                return habiRepo.findByIdHotelHabitacion_IdAndNumeroHabitacion(idHotel, numeroHabitacion);
            } catch (NumberFormatException e) {
                // Manejar error si el número de habitación no es válido
                return new ArrayList<>(); // O lanzar una excepción
            }
        }

        // Si no se proporcionó número de habitación, aplicar los demás filtros
        if (tipo != null && minPrecio != null && maxPrecio != null && capacidad != null) {
        	System.out.println("1");
            return habiRepo.findByIdHotelHabitacion_IdAndTipoHabitacionAndPrecioNocheBetweenAndCapacidad(
                    idHotel, tipo, minPrecio, maxPrecio, capacidad, sort
            );
        } else if (tipo != null && minPrecio != null && maxPrecio != null) {
        	System.out.println("2");
            return habiRepo.findByIdHotelHabitacion_IdAndTipoHabitacionAndPrecioNocheBetween(
                    idHotel, tipo, minPrecio, maxPrecio, sort
            );
        } else if (tipo != null && capacidad != null) {
        	System.out.println("3");
            return habiRepo.findByIdHotelHabitacion_IdAndTipoHabitacionAndCapacidad(
                    idHotel, tipo, capacidad, sort
            );
        } else if (minPrecio != null && maxPrecio != null && capacidad != null) {
        	System.out.println("4");
            return habiRepo.findByIdHotelHabitacion_IdAndPrecioNocheBetweenAndCapacidad(
                    idHotel, minPrecio, maxPrecio, capacidad, sort
            );
        } else if (tipo != null) {
        	System.out.println("5");
            return habiRepo.findByIdHotelHabitacion_IdAndTipoHabitacion(idHotel, tipo, sort);
        } else if (minPrecio != null && maxPrecio != null) {
        	System.out.println("6");
            return habiRepo.findByIdHotelHabitacion_IdAndPrecioNocheBetween(idHotel, minPrecio, maxPrecio, sort);
        } else if (capacidad != null) {
        	System.out.println("7");
            return habiRepo.findByIdHotelHabitacion_IdAndCapacidad(idHotel, capacidad, sort);
        } else {

            return new ArrayList<>(); // O manejar el caso de hotel no encontrado
        }
    }
    
 
	

}
