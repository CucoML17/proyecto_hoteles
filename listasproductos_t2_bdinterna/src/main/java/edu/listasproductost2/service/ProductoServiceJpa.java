package edu.listasproductost2.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Producto;
import edu.listasproductost2.repository.ProductoRepository;

@Service
@Primary
public class ProductoServiceJpa implements ProductoService{

	@Autowired
	ProductoRepository productoRepository;
	
	@Override
	public List<Producto> buscarTodos() {
		
		return productoRepository.findAll();	
		
	}

	@Override
	public Producto buscarPorId(Integer idPro) {
		Optional<Producto> optional = productoRepository.findById(idPro);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void guardarProducto(Producto producto) {
		// TODO Auto-generated method stub
		productoRepository.save(producto);
	}

	@Override
	public List<Producto> buscarDestacadoDisponible() {
		return productoRepository.findByDestacadoAndStatusOrderByIdDesc(1, "Disponible");
	}

	@Override
	public void eliminarProdId(Integer id) {
		
		productoRepository.deleteById(id);
	}

	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepository.findByDestacadoAndStatusAndNombreContainingIgnoreCaseOrderByIdDesc(1, "Disponible", nombre);
	}

	@Override
	public List<Producto> buscarPorCate(int cate) {
		// TODO Auto-generated method stub
		return productoRepository.findByDestacadoAndStatusAndCategoria_IdOrderByIdDesc(1, "Disponible", cate);
	}

	@Override
	public List<Producto> buscarPorCateNombre(int cate, String nombre) {
		// TODO Auto-generated method stub
		return productoRepository.findByDestacadoAndStatusAndNombreContainingIgnoreCaseAndCategoria_IdOrderByIdDesc(1, "Disponible", nombre, cate);
	}

	@Override
	public List<Producto> soloDisponibles() {
		// TODO Auto-generated method stub
		return productoRepository.findByStatus("Disponible");
	}



}
