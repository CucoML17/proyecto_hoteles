package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Habitacion;



public interface HabitacionService {
	
	int guardarHabitacion(Habitacion habitacion);
	List<Habitacion> todasHabitaciones();
	List<Habitacion> habitacionesActivas();
	
	Habitacion buscarHabitacionID(Integer idHabitacion);	
	
	List<Habitacion> filtrarHabitaciones
	(int idHotel, String numeroHabitacionStr, String tipo, Double minPrecio, Double maxPrecio, Integer capacidad, String ordenCampo, String ordenDireccion);
}
