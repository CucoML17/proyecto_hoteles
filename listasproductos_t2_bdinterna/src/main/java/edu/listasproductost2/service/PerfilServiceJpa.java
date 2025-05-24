package edu.listasproductost2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Perfil;
import edu.listasproductost2.model.Usuario;
import edu.listasproductost2.repository.PerfilRepository;

@Service
public class PerfilServiceJpa implements PerfilService{

	@Autowired
	PerfilRepository perfilRepository;
	
	@Override
	public List<Perfil> buscarTodosPerfiles() {
		return perfilRepository.findAll();
	}

	@Override
	public Perfil buscarPorIdPerfil(Integer idPerfil) {
		Optional<Perfil> optional = perfilRepository.findById(idPerfil);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void guardarPerfil(Perfil perfil) {
		perfilRepository.save(perfil);
		
	}

}
