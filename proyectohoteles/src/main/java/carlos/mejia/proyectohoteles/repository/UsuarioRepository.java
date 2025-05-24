package carlos.mejia.proyectohoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByUsername(String username);
}
