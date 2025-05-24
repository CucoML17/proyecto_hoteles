package carlos.mejia.proyectohoteles.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carlos.mejia.proyectohoteles.model.Cliente;
import carlos.mejia.proyectohoteles.repository.ClienteRepository;

@Service
public class ClienteServiceJpa implements ClienteService{
	@Autowired 
	ClienteRepository clienteRepository;

	@Override
	public int guardarCliente(Cliente cliente) {
		Cliente clienteTmp = clienteRepository.save(cliente);
	       
        return clienteTmp.getId();
	}

	@Override
	public List<Cliente> todosClientes() {
		// TODO Auto-generated method stub
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarClienteID(Integer idCliente) {
		Optional<Cliente> optional = clienteRepository.findById(idCliente);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}
	
    @Override
    public List<Cliente> filtrarClientes(String campoFiltro, String valorFiltro) {
        List<Cliente> listaFiltrada = new ArrayList<>();
        if (valorFiltro != null && !valorFiltro.isEmpty()) {
            switch (campoFiltro.toLowerCase()) {
                case "id":
                    try {
                        Integer id = Integer.parseInt(valorFiltro);
                        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
                        if (clienteOptional.isPresent()) {
                            listaFiltrada.add(clienteOptional.get());
                        }
                        return listaFiltrada;
                    } catch (NumberFormatException e) {
                        return List.of(); // Devolver lista vacía si el ID no es un número válido
                    }
                case "nombre":
                    return clienteRepository.findByNombreContainingIgnoreCase(valorFiltro);
                case "apellido":
                    return clienteRepository.findByApellidoContainingIgnoreCase(valorFiltro);
                default:
                    return clienteRepository.findAll(); // Si el campo de filtro no coincide, devolver todos
            }
        } else {
            return clienteRepository.findAll(); // Si no hay valor de filtro, devolver todos
        }
    }
}
