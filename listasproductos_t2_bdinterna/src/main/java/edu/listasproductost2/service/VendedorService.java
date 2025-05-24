package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Vendedor;

public interface VendedorService {
	List<Vendedor> buscarTodosVendedores();
	
	Vendedor buscarPorIdVendedor(Integer idVendedor);
	
	void guardarV(Vendedor vendedor);

	void eliminarV(Integer idVendedor);
}
