package edu.listasproductost2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.ProductoVendedor;
import edu.listasproductost2.model.ProductoVendedorId;

public interface ProductoVendedorRepository extends JpaRepository<ProductoVendedor, ProductoVendedorId> {

	List<ProductoVendedor> findByVendedorId(Integer vendedorId);
	
	List<ProductoVendedor> findByProductoId(Integer productoId);
	
}
