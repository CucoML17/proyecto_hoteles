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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="venta")
public class Venta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//Otros atributos
	@Column(name="fecha")
	private Date fecha;
	@Column(name="folio")
	private String folio;
	@Column(name="total")
	private double total;
	
	
	//Relación con Clientes:
    @ManyToOne
    @JoinColumn(name="idcliente")    
    private Cliente cliente;	
	
    //Muchos a muchos DetalleVenta
    @OneToMany(mappedBy = "venta")
    Set<DetalleVenta> venta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<DetalleVenta> getVenta() {
		return venta;
	}

	public void setVenta(Set<DetalleVenta> venta) {
		this.venta = venta;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", fecha=" + fecha + ", folio=" + folio + ", total=" + total + ", cliente=" + cliente
				+ "]";
	}

	public Venta(Integer id, Date fecha, String folio, double total, Cliente cliente) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.folio = folio;
		this.total = total;
		this.cliente = cliente;

	}

	public Venta(Integer id, Date fecha, String folio, double total, Cliente cliente, Set<DetalleVenta> venta) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.folio = folio;
		this.total = total;
		this.cliente = cliente;
		this.venta = venta;
	}

	public Venta() {
		super();
	}         
	
	
	
    
    
}
