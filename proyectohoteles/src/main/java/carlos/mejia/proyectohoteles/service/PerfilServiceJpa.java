package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Perfil;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.repository.PerfilRepository;

@Service
public class PerfilServiceJpa implements PerfilService {
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Override
	public int guardarPerfil(Perfil perfil) {
		Perfil perfilTmp = perfilRepository.save(perfil);
	       
        return perfilTmp.getId();
	}

	@Override
	public List<Perfil> todosPerfiles() {
		// TODO Auto-generated method stub
		return perfilRepository.findAll();
	}

	@Override
	public Perfil buscarPerfilID(Integer idPerfil) {
		Optional<Perfil> optional = perfilRepository.findById(idPerfil);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
