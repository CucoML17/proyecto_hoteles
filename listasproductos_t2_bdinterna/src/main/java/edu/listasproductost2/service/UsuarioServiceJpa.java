package edu.listasproductost2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Usuario;
import edu.listasproductost2.repository.PerfilRepository;
import edu.listasproductost2.repository.UsuarioRepository;

@Service
public class UsuarioServiceJpa implements IUsuarioService {
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	PerfilRepository perfilRepository;
	
	@Override
	public List<Usuario> buscarTodosUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscarPorIdUsuario(Integer idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void guardarU(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioRepository.save(usuario);
	}

	@Override
	public void eliminarU(Integer idUsuario) {
		
		usuarioRepository.deleteById(idUsuario);
	}
	
	@Override
    public Usuario encontrarUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }	

}
