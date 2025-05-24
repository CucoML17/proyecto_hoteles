package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Usuario;



public interface IUsuarioService {
	List<Usuario> buscarTodosUsuarios();
	
	Usuario buscarPorIdUsuario(Integer idUsuario);
	
	void guardarU(Usuario usuario);

	void eliminarU(Integer idUsuario);
	
	Usuario encontrarUsuario(String username);
}
