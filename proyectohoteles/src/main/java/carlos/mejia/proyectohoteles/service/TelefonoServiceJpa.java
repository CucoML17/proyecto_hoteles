package carlos.mejia.proyectohoteles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Telefono;
import carlos.mejia.proyectohoteles.repository.TelefonoRepository;

@Service
public class TelefonoServiceJpa implements TelefonoService {
	
	@Autowired
	TelefonoRepository telefonoRepo;

	@Override
	public void guardarTelefono(Telefono telefono) {
		telefonoRepo.save(telefono);
		
	}

	@Override
	public List<Telefono> telefonosHotel(int idHotel) {
		// TODO Auto-generated method stub
		return telefonoRepo.findAll();
	}

	@Override
	public void guardarListaTel(List<Telefono> listaTelefonos) {
		telefonoRepo.saveAll(listaTelefonos);
		
	}

	@Override
	public void borrarTel(int idTel) {
		// TODO Auto-generated method stub
		telefonoRepo.deleteById(idTel);
	}
	
	@Override
	public void eliminarTelefonosPorHotel(Hotel hotel) {
	    telefonoRepo.deleteByIdHotelTelefono(hotel);
	}

}
