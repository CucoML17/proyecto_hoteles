package edu.listasproductost2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Vendedor;
import edu.listasproductost2.model.Venta;
import edu.listasproductost2.repository.VentaRepository;

@Service
public class VentaServiceJpa implements VentaService {

	@Autowired
	VentaRepository ventaRepo;
	
	
	@Override
	public List<Venta> buscarTodasVentas() {
		// TODO Auto-generated method stub
		return ventaRepo.findAll();
	}

	@Override
	public Venta buscarPorIdVenta(Integer idVenta) {
		Optional<Venta> optional = ventaRepo.findById(idVenta);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public int guardarVenta(Venta venta) {
		// TODO Auto-generated method stub
        //Guarda la venta, pero también lo guarda en un objeto de Venta
        Venta ventatmp = ventaRepo.save(venta);
        //retorna el id generado automático, esto será útil para las relaciones de DETALLEVENTA.
        return ventatmp.getId();
	}
	

}
