package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Telefono;



public interface TelefonoService {

	void guardarTelefono(Telefono telefono);
	
	List<Telefono> telefonosHotel(int idHotel);
	
	void guardarListaTel(List<Telefono> listaTelefonos);
	
	void borrarTel(int idTel);
	
	void eliminarTelefonosPorHotel(Hotel hotel);
		
}
