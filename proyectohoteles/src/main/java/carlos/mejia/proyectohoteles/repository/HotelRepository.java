package carlos.mejia.proyectohoteles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Hotel;
import org.springframework.data.domain.Sort;



public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	List<Hotel> findByEstado(int estado);
	
	List<Hotel> findByNombreContainingIgnoreCase(String nombre, Sort sort);
	
	List<Hotel> findByDireccionContainingIgnoreCase(String direccion, Sort sort);
	
	//Para el promedio (necesitamos habitaciones, aún no llegamos a eso)
	
	List<Hotel> findByPrecioPromedioBetween(double precioMin, double precioMax, Sort sort);

	
	List<Hotel> findByEstadoAndPrecioPromedioBetween(int estado, double precioMin, double precioMax, Sort sort);
}
