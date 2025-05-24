package edu.listasproductost2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.listasproductost2.model.Categoria;
import edu.listasproductost2.model.Producto;

@Service
public class ProductoServiceImpl implements ProductoService {

	private List<Producto> lista = null;
	//Constructor
	public ProductoServiceImpl() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yy");
		
		lista = new LinkedList<Producto>();
		
		try {
			Producto producto1 = new Producto();
			producto1.setId(1);
			producto1.setNombre("Sopa de tomate");
			producto1.setDescripcion("Sopa de tomate de 500ml");
			producto1.setPrecio(32.2);
			producto1.setFechaPublicacion(sdf.parse("15-02-25"));
			producto1.setStatus("Activo");
			producto1.setImagen("sopat.png");
			producto1.setDestacado(1);
			
			
			Producto producto2 = new Producto();
			producto2.setId(2);
			producto2.setNombre("Lápiz");
			producto2.setDescripcion("Una lápiz negro");
			producto2.setPrecio(17.8);
			producto2.setFechaPublicacion(sdf.parse("04-02-25"));
			producto2.setStatus("Existencia");
			producto2.setDestacado(0);
			
			Producto producto3 = new Producto();
			producto3.setId(3);
			producto3.setNombre("Carbón");
			producto3.setDescripcion("Carbón activo para tus parrilladas");
			producto3.setPrecio(125.0);
			producto3.setFechaPublicacion(sdf.parse("07-02-25"));
			
			producto3.setStatus("Eliminado");
			producto3.setImagen("carbon.png");
			producto3.setDestacado(1);
			
			
			Producto producto4 = new Producto();
			producto4.setId(4);
			producto4.setNombre("Agua embotellada");
			producto4.setDescripcion("Agua epura");
			producto4.setPrecio(10.5);
			producto4.setFechaPublicacion(sdf.parse("07-02-25"));
			
			producto4.setStatus("Activo");
			producto4.setImagen("aguaE.png");
			producto4.setDestacado(1);
			
			Producto producto5 = new Producto();
			producto5.setId(5);
			producto5.setNombre("Sandías alocadas");
			producto5.setDescripcion("Sandías alocadas al por mayor");
			producto5.setPrecio(30.0);
			producto5.setFechaPublicacion(sdf.parse("13-02-25"));
			
			producto5.setStatus("Activo");
			producto5.setImagen("sanda.jpg");
			producto5.setDestacado(0);
			
			//
			lista.add(producto1);
			lista.add(producto2);
			lista.add(producto3);
			lista.add(producto4);
			lista.add(producto5);
			
			
		}catch(ParseException e) {
			e.printStackTrace();
		}
		
				
		
	}
	
	
	@Override
	public List<Producto> buscarTodos() {
		// TODO Auto-generated method stub
		return lista;
	}


	@Override
	public Producto buscarPorId(Integer idPro) {
		
		for(Producto p : lista)
			if(p.getId()==idPro) {
				return p;
			}
		return null;
	}


	@Override
	public void guardarProducto(Producto producto) {
		lista.add(producto);
		
	}


	@Override
	public List<Producto> buscarDestacadoDisponible() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void eliminarProdId(Integer id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Producto> buscarPorCate(int cate) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Producto> buscarPorCateNombre(int cate, String nombre) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Producto> soloDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
	
}
