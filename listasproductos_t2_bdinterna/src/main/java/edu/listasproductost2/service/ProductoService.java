package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Producto;

public interface ProductoService {
	
	List<Producto> buscarTodos();
	
	Producto buscarPorId(Integer idPro);
	
	void guardarProducto(Producto producto);
	
	List<Producto> buscarDestacadoDisponible();
	
	void eliminarProdId(Integer id);
	
	//Actividad parte 1
	
	List<Producto> buscarPorNombre(String nombre);
	
	List<Producto> buscarPorCate(int cate);
	
	List<Producto> buscarPorCateNombre(int cate, String nombre);
	
	//Actividad 5
	List<Producto> soloDisponibles();
	

}
