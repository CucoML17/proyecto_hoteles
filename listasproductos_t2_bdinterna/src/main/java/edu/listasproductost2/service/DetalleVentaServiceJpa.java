package edu.listasproductost2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.DetalleVenta;
import edu.listasproductost2.model.DetalleVentaId;
import edu.listasproductost2.repository.DetalleVentaRepository;
import edu.listasproductost2.repository.ProductoRepository;
import edu.listasproductost2.repository.VentaRepository;

@Service
public class DetalleVentaServiceJpa implements DetalleVentaService {
	@Autowired
	DetalleVentaRepository detalleRepo;
	
	@Autowired
	ProductoRepository prodRepo;
	
	@Autowired
	VentaRepository ventaRepo;

	@Autowired
	DetalleVentaRepository detRepo;
	@Override
	public List<DetalleVenta> buscarTodos() {
		// TODO Auto-generated method stub
		return detalleRepo.findAll();
	}

	@Override
	public void guardarDetalle(Integer idProducto, Integer idVenta, int cantidad, double subtotal) {
    	//Creamos una llave
        DetalleVentaId id = new DetalleVentaId(idProducto, idVenta);
        
        //Creamos un DetalleVenta
        DetalleVenta detalleVenta = new DetalleVenta();
        //Le asignamos nuestra llave
        detalleVenta.setId(id);
        //Le guardamos la relación
        detalleVenta.setProductoDetalle(prodRepo.findById(idProducto).get());
        detalleVenta.setVenta(ventaRepo.findById(idVenta).get());
        
        //Otros atributos
        detalleVenta.setCantidad(cantidad);
        detalleVenta.setSubtotal(subtotal);
        //Guardarmos con el repositorio
        detalleRepo.save(detalleVenta);
		
	}

	@Override
	public List<DetalleVenta> buscarVentasId(int idVenta) {
		// TODO Auto-generated method stub
		return detRepo.findByVentaId(idVenta);
	}
	

}
