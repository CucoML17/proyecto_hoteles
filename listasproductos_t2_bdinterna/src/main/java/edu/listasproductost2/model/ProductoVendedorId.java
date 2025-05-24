package edu.listasproductost2.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductoVendedorId implements Serializable {
	private Integer id_producto;
	private Integer id_vendedor;

	public Integer getId_producto() {
		return id_producto;
	}
	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
	}
	public Integer getId_vendedor() {
		return id_vendedor;
	}
	public void setId_vendedor(Integer id_vendedor) {
		this.id_vendedor = id_vendedor;
	}

	@Override
	public String toString() {
		return "ProductoVendedorLlave [id_producto=" + id_producto + ", id_vendedor=" + id_vendedor + ", fecha_venta=" + "]";
	}
	public ProductoVendedorId(Integer id_producto, Integer id_vendedor) {
		super();
		this.id_producto = id_producto;
		this.id_vendedor = id_vendedor;

	}
	public ProductoVendedorId() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(id_producto, id_vendedor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoVendedorId other = (ProductoVendedorId) obj;
		return Objects.equals(id_producto, other.id_producto) && Objects.equals(id_vendedor, other.id_vendedor);
	}
	
	
	
}
