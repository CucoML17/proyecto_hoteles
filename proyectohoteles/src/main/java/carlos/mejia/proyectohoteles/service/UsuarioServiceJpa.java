package carlos.mejia.proyectohoteles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Empleado;
import carlos.mejia.proyectohoteles.model.Usuario;
import carlos.mejia.proyectohoteles.repository.UsuarioRepository;

@Service
public class UsuarioServiceJpa implements UsuarioService{
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public int guardarUsuario(Usuario usuario) {
		Usuario usuarioTmp = usuarioRepository.save(usuario);
	       
        return usuarioTmp.getId();
	}

	@Override
	public List<Usuario> todosUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscarUsuarioID(Integer idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
