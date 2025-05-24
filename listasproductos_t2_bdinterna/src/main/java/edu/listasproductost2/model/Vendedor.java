package edu.listasproductost2.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="vendedor")
public class Vendedor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//Colocamos sus atributos:
	@Column(name="nombre_completo")
    private String nombre_completo;
	@Column(name="telefono")
    private String telefono;
	@Column(name="direccion")
    private String direccion;
    
    
    //El muchos a muchos
    @OneToMany(mappedBy = "vendedor")
    Set<ProductoVendedor> vendedor;    
    
    //Relación para Clientes:
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.EAGER)
    private List<Cliente> clientes;
    
    
    
    
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public Set<ProductoVendedor> getVendedor() {
		return vendedor;
	}
	public void setVendedor(Set<ProductoVendedor> vendedor) {
		this.vendedor = vendedor;
	}
	@Override
	public String toString() {
		return "Vendedor [id=" + id + ", nombre_completo=" + nombre_completo + ", telefono=" + telefono + ", direccion="
				+ direccion + ", vendedor=" + vendedor + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre_completo() {
		return nombre_completo;
	}
	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Vendedor(Integer id, String nombre_completo, String telefono, String direccion) {
		super();
		this.id = id;
		this.nombre_completo = nombre_completo;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	public Vendedor() {
		super();
	}
    
    
	
}
