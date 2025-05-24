package edu.listasproductost2.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="producto")
public class Producto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//Categoria cateDefecto = new Categoria(0, "Categoría por defecto", "Esta categoría es para aquellos productos sin categoría", "sinfoto.png", 0);
	
	@Column(name="nombre")
    private String nombre;
	@Column(name="fecha_publicacion")
    private Date fechaPublicacion;
	@Column(name="descripcion")
    private String descripcion;
	@Column(name="precio")
    private double precio;
    
	@Column(name="destacado")
    private Integer destacado;
    
	@Column(name="imagen")
    private String imagen;
	@Column(name="status")
    private String status;
	@Column(name="detalles")
    private String detalles;
    
    //Faltantes
    //@Transient
    
	@ManyToOne
	@JoinColumn(name="categoria")
    private Categoria categoria;



    //El muchos a uno
    @OneToMany(mappedBy = "producto")
    Set<ProductoVendedor> producto;     
    
    
    //Muchos a muchos DetalleVenta
    @OneToMany(mappedBy = "productoDetalle")
    Set<DetalleVenta> productoDetalle;    


	public Set<ProductoVendedor> getProducto() {
		return producto;
	}


	public void setProducto(Set<ProductoVendedor> producto) {
		this.producto = producto;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDetalles() {
		return detalles;
	}


	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}


	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", fechaPublicacion=" + fechaPublicacion + ", descripcion="
				+ descripcion + ", precio=" + precio + ", destacado=" + destacado + ", imagen=" + imagen + ", status="
				+ status + ", detalles=" + detalles + ", categoria=" + categoria + ", producto=" + producto + "]";
	}

	
	public Producto() {
		super();
	}


	public Producto(Integer id, String nombre, Date fechaPublicacion, String descripcion, double precio,
			 Integer destacado, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaPublicacion = fechaPublicacion;
		this.descripcion = descripcion;
		this.precio = precio;
		
		this.destacado = destacado;
		this.imagen = imagen;
	}

	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}



	public Integer getDestacado() {
		return destacado;
	}

	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
    
    
	
}
