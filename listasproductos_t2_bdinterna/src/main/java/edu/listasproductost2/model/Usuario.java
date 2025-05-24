package edu.listasproductost2.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="username")
	private String username;
	@Column(name="estatus")
	private Integer estatus;
	@Column(name="fecha_registro")
	private Date fechaRegistro ;
	
	
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", password=" + password
				+ ", username=" + username + ", status=" + estatus + ", fechaRegistro=" + fechaRegistro + ", perfiles="
				+ perfiles + "]";
	}
	
	//El many to many
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuarioperfil", 
			joinColumns = @JoinColumn(name="idUsuario"),
			inverseJoinColumns = @JoinColumn(name="idPerfil"))
	
	private List<Perfil> perfiles;
	
	public void agregarPerfilUsuario(Perfil tmpPerfil) {
		if (perfiles==null) {
			//Sino encuentra perfiles, entonces crea la liste
			perfiles = new LinkedList<Perfil>();
			
		}
		//Si encuentra, quiere decir que ya tiene perfil, solo agrega más
		perfiles.add(tmpPerfil);
		
	}
	
	
	
	public List<Perfil> getPerfiles() {
		return perfiles;
	}



	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getStatus() {
		return estatus;
	}
	public void setStatus(Integer status) {
		this.estatus = status;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Usuario(Integer id, String nombre, String email, String password, String username, Integer status,
			Date fechaRegistro) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.username = username;
		this.estatus = status;
		this.fechaRegistro = fechaRegistro;
	}
	public Usuario() {
		super();
	}
	
	
	
	
	
}
