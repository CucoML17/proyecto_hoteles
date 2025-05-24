package edu.listasproductost2.service;

import java.util.List;

import edu.listasproductost2.model.Categoria;

public interface CategoriaService {
	void guardarCategoria(Categoria categoria);
	List<Categoria> buscarTodasCates();
	Categoria buscarPorIDCat(Integer idCategoria);
	
	void eliminarCateId(Integer id);
}
