package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Venta;


public interface VentaService {
	
	List<Venta> buscarTodasVentas();
	
	Venta buscarPorIdVenta(Integer idVenta);
	
	int guardarVenta(Venta venta);

		

}
