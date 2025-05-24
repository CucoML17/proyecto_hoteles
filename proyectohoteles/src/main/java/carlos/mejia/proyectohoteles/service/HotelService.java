package carlos.mejia.proyectohoteles.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import carlos.mejia.proyectohoteles.model.Hotel;



public interface HotelService {

	int guardarHotel(Hotel hotel);
	List<Hotel> todosHoteles();
	List<Hotel> hotelesActivos();
	
	Hotel buscarHotelID(Integer idHotel);	
	
	List<Hotel> filtrarHotelPor(String filtro, String orden, String valor);

	List<Hotel> filtrarHotelPrecioPromedio(double min, double max, String orden);
	
	List<Hotel> filtrarHotelPorEstado(String orden, String estado);
	
	List<Hotel> filtrarHotelPorPrecioPromedio(double precioMin, double precioMax, String orden);
	
	
	Hotel buscarHotelMasEconomico();
    List<Hotel> buscarOtrosHotelesEconomicos();	
    List<Hotel> todosHotelesEstado(int estado);
    
    List<Hotel> filtrarHotelesActivosPorPrecioPromedio(double precioMin, double precioMax, String orden);
}
