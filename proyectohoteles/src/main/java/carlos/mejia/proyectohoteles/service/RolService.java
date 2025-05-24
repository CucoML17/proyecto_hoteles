package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Hotel;
import carlos.mejia.proyectohoteles.model.Rol;
import carlos.mejia.proyectohoteles.model.Telefono;

public interface RolService {
	void guardarRol(Rol rol);
	
	List<Rol> todosRoles();
	
	Rol buscarRolID(Integer idRol);

}
