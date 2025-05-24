package carlos.mejia.proyectohoteles.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Habitacion;
import carlos.mejia.proyectohoteles.model.Hotel;


public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

	List<Habitacion> findByEstado(int estado);
	
	//Para las búsquedas y filtrados:

    List<Habitacion> findByIdHotelHabitacion_IdAndTipoHabitacion(int idHotel, String tipoHabitacion, Sort orden);

    List<Habitacion> findByIdHotelHabitacion_IdAndPrecioNocheBetween(int idHotel, double precioMin, double precioMax, Sort sort);

    List<Habitacion> findByIdHotelHabitacion_IdAndCapacidad(int idHotel, int capacidad, Sort orden);

    List<Habitacion> findByIdHotelHabitacion_IdAndNumeroHabitacion(int idHotel, Integer numeroHabitacion);

    // Combinaciones de dos criterios (incluyendo el hotel):

    // 1. Tipo de habitación y rango de precio
    List<Habitacion> findByIdHotelHabitacion_IdAndTipoHabitacionAndPrecioNocheBetween(
            int idHotel, String tipoHabitacion, double precioMin, double precioMax, Sort sort
    );

    // 2. Tipo de habitación y capacidad
    List<Habitacion> findByIdHotelHabitacion_IdAndTipoHabitacionAndCapacidad(
            int idHotel, String tipoHabitacion, int capacidad, Sort sort
    );

    // 3. Rango de precio y capacidad
    List<Habitacion> findByIdHotelHabitacion_IdAndPrecioNocheBetweenAndCapacidad(
            int idHotel, double precioMin, double precioMax, int capacidad, Sort sort
    );

    // Combinación de tres criterios (incluyendo el hotel):

    //4.Tipo de habitación, rango de precio y capacidad
    List<Habitacion> findByIdHotelHabitacion_IdAndTipoHabitacionAndPrecioNocheBetweenAndCapacidad(
            int idHotel, String tipoHabitacion, double precioMin, double precioMax, int capacidad, Sort sort
    );
	
	
	
}
