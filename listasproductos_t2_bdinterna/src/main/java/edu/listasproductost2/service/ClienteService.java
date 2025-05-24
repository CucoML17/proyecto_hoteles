package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Cliente;



public interface ClienteService {

	void guardarCliente(Cliente cliente);
	List<Cliente> buscarTodosClientes();
	Cliente buscarPorIDCliente(Integer idCliente);
	
	void eliminarId(Integer id);
	
	
}
