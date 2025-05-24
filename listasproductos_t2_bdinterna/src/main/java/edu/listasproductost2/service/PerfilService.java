package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Perfil;

public interface PerfilService {
	List<Perfil> buscarTodosPerfiles();
	
	Perfil buscarPorIdPerfil(Integer idPerfil);
	
	void guardarPerfil(Perfil perfil);
}
