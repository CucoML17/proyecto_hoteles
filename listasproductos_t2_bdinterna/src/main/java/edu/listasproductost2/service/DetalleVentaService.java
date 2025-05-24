package edu.listasproductost2.service;

import java.util.Date;
import java.util.List;

import edu.listasproductost2.model.DetalleVenta;



public interface DetalleVentaService {
	List<DetalleVenta> buscarTodos();
	
	void guardarDetalle(Integer idProducto, Integer idVenta, int cantidad, double subtotal);

	List<DetalleVenta> buscarVentasId(int idVenta);
	
}
