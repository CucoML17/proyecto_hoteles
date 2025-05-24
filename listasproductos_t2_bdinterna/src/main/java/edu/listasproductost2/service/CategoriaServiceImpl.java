package edu.listasproductost2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Categoria;



@Service
public class CategoriaServiceImpl implements CategoriaService {
	

	private LinkedList<Categoria> listaC = null;
	//Constructor
	public CategoriaServiceImpl() {
		
		listaC = new LinkedList<Categoria>();
		
		try {

			
			Categoria cate1 = new Categoria();
			cate1.setId(1);
			cate1.setNombre("Alimentos");
			cate1.setDescripcion("Categoría donde van los alimentos.");
			cate1.setImagen("alimento.png");
			cate1.setEstado(1);

			Categoria cate2 = new Categoria();
			cate2.setId(2);
			cate2.setNombre("Productos de limpieza");
			cate2.setDescripcion("Maestro limpio, puro producto.");
			cate2.setImagen("limpieza.png");
			cate2.setEstado(0);
			
			Categoria cate3 = new Categoria();
			cate3.setId(3);
			cate3.setNombre("Lacteos");
			cate3.setDescripcion("Leche, queso, yogurt, etc.");
			cate3.setImagen("lacteo.png");
			cate3.setEstado(1);			

			
			//
			listaC.add(cate1);
			listaC.add(cate2);
			listaC.add(cate3);

			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
				
		
	}	
	
	

	@Override
	public void guardarCategoria(Categoria categoria) {
		listaC.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodasCates() {
		
		return listaC;
	}

	@Override
	public Categoria buscarPorIDCat(Integer idCategoria) {
		
		for(Categoria c : listaC)
			if(c.getId()==idCategoria) {
				return c;
			}
		return null;
	}



	@Override
	public void eliminarCateId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
}
