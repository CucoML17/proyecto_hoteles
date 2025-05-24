package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Perfil;

public interface PerfilService {
	int guardarPerfil(Perfil perfil);
	List<Perfil> todosPerfiles();
	Perfil buscarPerfilID(Integer idPerfil);
}
