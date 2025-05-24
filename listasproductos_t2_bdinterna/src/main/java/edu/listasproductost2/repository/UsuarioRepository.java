package edu.listasproductost2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);
	

}
