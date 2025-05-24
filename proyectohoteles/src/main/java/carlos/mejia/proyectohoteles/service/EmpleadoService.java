package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Empleado;

public interface EmpleadoService {

	int guardarEmpleado(Empleado empleado);
	List<Empleado> todosEmpleados();
	List<Empleado> todosEmpleadosActivos();
	
	Empleado buscarEmpleadoID(Integer idEmpleado);	
	
	List<Empleado> filtrarEmpleados(Integer idHotel, String campoFiltro, String valorFiltro, Integer rolId);
	List<Empleado> filtrarEmpleadosEstado(Integer idHotel, String campoFiltro, String valorFiltro, Integer rolId, Integer estado);
}
