package carlos.mejia.proyectohoteles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import carlos.mejia.proyectohoteles.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);	
	

}
