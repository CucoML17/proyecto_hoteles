package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Carrito;

public interface CarritoService {
	void guardarACarrito(Carrito carrito);
	List<Carrito> buscarTodoCarrito();
	
	void limpiarCarrito();
}
