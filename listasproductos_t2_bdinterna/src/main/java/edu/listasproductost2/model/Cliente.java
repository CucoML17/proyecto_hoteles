package edu.listasproductost2.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//Otros atributos de cliente
	@Column(name="nombre_completo")
	private String nombreCompleto;
	@Column(name="direccion")
	private String direccion;
	@Column(name="telefono")
	private String telefono;
	
	@ManyToOne
    @JoinColumn(name="id_vendedor")    
    private Vendedor vendedor;

    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Venta> ventas;    
    
    
    
	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombreCompleto=" + nombreCompleto + ", direccion=" + direccion + ", telefono="
				+ telefono + ", vendedor=" + vendedor + ", ventas=" + ventas + "]";
	}

	public Cliente() {
		super();
	}
	
	
    
    

}
