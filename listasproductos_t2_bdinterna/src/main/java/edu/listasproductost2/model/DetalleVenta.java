package edu.listasproductost2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalleventa")
public class DetalleVenta {
	//La llave combinada:
    @EmbeddedId
    private DetalleVentaId id;

    @ManyToOne
    @MapsId("idproducto")
    @JoinColumn(name = "idproducto")
    private Producto productoDetalle;//Tiene que ser la entidad/modelo, ya no el atributo, por eso
    //se le coloca el MapsId Para saber a qué atributo se refiere

    @ManyToOne
    @MapsId("idventa")
    @JoinColumn(name = "idventa")
    private Venta venta;

    //Ahora sí, podemos colocar los demás atributos, porque esta sí es la relación en la BD.
    @Column(name="cantidad")
    private int cantidad;
    @Column(name="subtotal")
    private double subtotal;
    
	public DetalleVentaId getId() {
		return id;
	}
	public void setId(DetalleVentaId id) {
		this.id = id;
	}
	public Producto getProductoDetalle() {
		return productoDetalle;
	}
	public void setProductoDetalle(Producto productoDetalle) {
		this.productoDetalle = productoDetalle;
	}
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	@Override
	public String toString() {
		return "DetalleVenta [id=" + id + ", productoDetalle=" + productoDetalle + ", venta=" + venta + ", cantidad="
				+ cantidad + ", subtotal=" + subtotal + "]";
	}
	public DetalleVenta() {
		super();
	}
    
    

}
