package edu.listasproductost2.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Carrito;
import edu.listasproductost2.model.Categoria;

@Service
public class CarritoServiceImp implements CarritoService{

	
	private LinkedList<Carrito> listaCarrito = null;
	
	public CarritoServiceImp() {
		
		listaCarrito = new LinkedList<Carrito>();
		
	}
	
	@Override
	public void guardarACarrito(Carrito carrito) {
		// TODO Auto-generated method stub
		listaCarrito.add(carrito);
	}

	@Override
	public List<Carrito> buscarTodoCarrito() {
		// TODO Auto-generated method stub
		return listaCarrito;
	}

	@Override
	public void limpiarCarrito() {
		listaCarrito.clear();
		
	}

}
