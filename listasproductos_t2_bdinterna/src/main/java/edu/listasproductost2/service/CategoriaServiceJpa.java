package edu.listasproductost2.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.repository.CategoriaRepository;

@Service
@Primary
public class CategoriaServiceJpa implements CategoriaService {

	@Autowired
	CategoriaRepository categoriaRepositorio;
	
	
	@Override
	public void guardarCategoria(Categoria categoria) {
		
		categoriaRepositorio.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodasCates() {
		
		return categoriaRepositorio.findAll();
	}

	@Override
	public Categoria buscarPorIDCat(Integer idCategoria) {
	
		Optional<Categoria> optional = categoriaRepositorio.findById(idCategoria);
		
		if(optional.isPresent()) {
			return optional.get();
		}
				
		
		return null;
	}

	@Override
	public void eliminarCateId(Integer id) {
		categoriaRepositorio.deleteById(id);
		
	}

}
