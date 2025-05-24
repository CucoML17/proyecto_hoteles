package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.repository.RolRepository;

@Service
public class RolServiceJpa implements RolService {
	@Autowired 
	RolRepository rolRepository;
	
	@Override
	public void guardarRol(Rol rol) {
		rolRepository.save(rol);
		
	}

	@Override
	public List<Rol> todosRoles() {
		// TODO Auto-generated method stub
		return rolRepository.findAll();
	}

	@Override
	public Rol buscarRolID(Integer idRol) {
		Optional<Rol> optional = rolRepository.findById(idRol);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

}
