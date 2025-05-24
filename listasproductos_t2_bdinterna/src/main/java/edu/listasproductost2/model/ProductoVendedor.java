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
@Table(name = "productovendedor")
public class ProductoVendedor {
    @EmbeddedId
    private ProductoVendedorId id;

    @ManyToOne
    @MapsId("id_producto")
    @JoinColumn(name = "id_producto")
    private Producto producto;//Tiene que ser la entidad/modelo, ya no el atributo, por eso
    //se le coloca el MapsId Para saber a qué atributo se refiere

    @ManyToOne
    @MapsId("id_vendedor")
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;

    //Ahora sí, podemos colocar los demás atributos, porque esta sí es la relación en la BD.
    @Column(name="fecha_venta")
    private Date fechaVenta;

	public ProductoVendedorId getId() {
		return id;
	}

	public void setId(ProductoVendedorId id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	@Override
	public String toString() {
		return "ProductoVendedor [id=" + id + ", producto=" + producto + ", vendedor=" + vendedor + ", fechaVenta="
				+ fechaVenta + "]";
	}

	public ProductoVendedor(ProductoVendedorId id, Producto producto, Vendedor vendedor, Date fechaVenta) {
		super();
		this.id = id;
		this.producto = producto;
		this.vendedor = vendedor;
		this.fechaVenta = fechaVenta;
	}

	public ProductoVendedor() {
		super();
	}
    
    
    
    
}
