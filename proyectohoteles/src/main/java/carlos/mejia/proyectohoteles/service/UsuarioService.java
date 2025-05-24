package carlos.mejia.proyectohoteles.service;

import java.util.List;


import carlos.mejia.proyectohoteles.model.Usuario;

public interface UsuarioService {
	
	int guardarUsuario(Usuario usuario);
	List<Usuario> todosUsuarios();
	Usuario buscarUsuarioID(Integer idUsuario);
	
	Usuario buscarPorUsername(String username);
	
}
