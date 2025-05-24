package edu.listasproductost2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.DetalleVenta;
import edu.listasproductost2.model.DetalleVentaId;




public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {

	List<DetalleVenta> findByVentaId(Integer idVenta);	
}
