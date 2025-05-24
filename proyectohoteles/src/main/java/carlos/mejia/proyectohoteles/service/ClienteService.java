package carlos.mejia.proyectohoteles.service;

import java.util.List;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.model.Empleado;

public interface ClienteService {
	int guardarCliente(Cliente cliente);
	List<Cliente> todosClientes();
	
	
	Cliente buscarClienteID(Integer idCliente);
	
	List<Cliente> filtrarClientes(String campoFiltro, String valorFiltro);
}
