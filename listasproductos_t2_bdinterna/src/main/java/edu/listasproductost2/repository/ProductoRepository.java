package edu.listasproductost2.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	List<Producto> findByStatus(String status);
	
	List<Producto> findByDestacado(int destacado);
	
	List<Producto> findByFechaPublicacion(Date fecha);
	
	List<Producto> findByDestacadoAndStatusOrderByIdDesc(int destacado, String status);
	
	//Ejercicio 1
	List<Producto> findByPrecioBetween(double precioMin, double precioMax);
	
	//Ejercicio2
	List<Producto> findByPrecioBetweenOrderByPrecioDesc(double precioMin, double precioMax);
	
	
	//Ejercicio3
	List<Producto> findByStatusIn(Collection<String> listaStatus);
	
	//Ejercicio 4 múltiple
	List<Producto> findByStatusAndDestacadoAndFechaPublicacion(String status, int destacado, Date fecha);
	
	//Ejercicio 5
	List<Producto> findByFechaPublicacionBetween(Date fechaIni, Date fechaFin);
	
	
	//Ejercicio 6
	List<Producto> findByCategoria_Nombre(String nombreCate);
	
	//Ejercicio 7
	List<Producto> findByNombreEndingWith(String finNombre);
	
	
	
	//Actividad 
	 List<Producto> findByDestacadoAndStatusAndNombreContainingIgnoreCaseOrderByIdDesc(int destacado, String status, String nombre);

	 List<Producto> findByDestacadoAndStatusAndCategoria_IdOrderByIdDesc(int destacado, String status, int categoria);
	 
	 List<Producto> findByDestacadoAndStatusAndNombreContainingIgnoreCaseAndCategoria_IdOrderByIdDesc(int destacado, String status, String nombre, int categoria);
	 
	 
	 //Actividad 5
	 
	 
	
	
}
