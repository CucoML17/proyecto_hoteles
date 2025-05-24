package edu.listasproductost2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Cliente;

import edu.listasproductost2.repository.ClienteRepository;

@Service
public class ClienteServiceJpa implements ClienteService {

	@Autowired
	ClienteRepository clienteRepo;

	@Override
	public void guardarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		clienteRepo.save(cliente);
		
	}

	@Override
	public List<Cliente> buscarTodosClientes() {
		// TODO Auto-generated method stub
		return clienteRepo.findAll();
	}

	@Override
	public Cliente buscarPorIDCliente(Integer idCliente) {
		Optional<Cliente> optional = clienteRepo.findById(idCliente);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void eliminarId(Integer id) {
		// TODO Auto-generated method stub
		clienteRepo.deleteById(id);
	}
	
	
}
