package edu.listasproductost2.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.listasproductost2.model.Vendedor;
import edu.listasproductost2.repository.VendedorRepository;


@Service
public class VendedorServiceJpa implements VendedorService{

	@Autowired
	VendedorRepository vendedorRepo;
	
	@Override
	public List<Vendedor> buscarTodosVendedores() {
		// TODO Auto-generated method stub
		return vendedorRepo.findAll();
	}

	@Override
	public Vendedor buscarPorIdVendedor(Integer idVendedor) {
		Optional<Vendedor> optional = vendedorRepo.findById(idVendedor);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void guardarV(Vendedor vendedor) {
		// TODO Auto-generated method stub
		vendedorRepo.save(vendedor);
	}

	@Override
	public void eliminarV(Integer idVendedor) {
		vendedorRepo.deleteById(idVendedor);
		
	}

}
