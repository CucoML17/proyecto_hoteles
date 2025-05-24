package edu.listasproductost2.model;

public class Carrito {
	//Para el producto que se está comprando
	private Producto productoCompra;
	//Para la cantidad de producto comprada
	private int cantidadCompra;
	@Override
	public String toString() {
		return "Carrito [productoCompra=" + productoCompra + ", cantidadCompra=" + cantidadCompra + "]";
	}
	public Carrito(Producto productoCompra, int cantidadCompra) {
		super();
		this.productoCompra = productoCompra;
		this.cantidadCompra = cantidadCompra;
	}
	public Producto getProductoCompra() {
		return productoCompra;
	}
	public void setProductoCompra(Producto productoCompra) {
		this.productoCompra = productoCompra;
	}
	public int getCantidadCompra() {
		return cantidadCompra;
	}
	public void setCantidadCompra(int cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}
	
	public Carrito() {
		super();
		
	}	
	
}
