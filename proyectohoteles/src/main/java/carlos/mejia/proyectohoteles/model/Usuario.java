package carlos.mejia.proyectohoteles.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="password")
	private String password;
	@Column(name="username")
	private String username;
	@Column(name="estatus")
	private Integer estatus;
	@Column(name="fecha_registro")
	private Date fechaRegistro ;
	
	
	//Para su relación muchos a muchos con Perfil
	//El many to many
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="usuarioperfil", 
			joinColumns = @JoinColumn(name="idUsuario"),
			inverseJoinColumns = @JoinColumn(name="idPerfil"))
	private List<Perfil> perfiles = new LinkedList<>();
	
	public void agregarPerfilUsuario(Perfil tmpPerfil) {
		if (perfiles==null) {
			//Sino encuentra perfiles, entonces crea la liste
			perfiles = new LinkedList<Perfil>();
			
		}
		//Si encuentra, quiere decir que ya tiene perfil, solo agrega más
		perfiles.add(tmpPerfil);
		
	}
	
	
	
	//Por si está ligado a un Cliente
    @OneToMany(mappedBy = "idClienteUser", fetch = FetchType.EAGER)
    private List<Cliente> clienteligado;
	
	//Por si está ligado a un Empleado
    @OneToMany(mappedBy = "idEmpleadoUser", fetch = FetchType.EAGER)
    private List<Empleado> empleadoligado;

    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Cliente> getClienteligado() {
		return clienteligado;
	}

	public void setClienteligado(List<Cliente> clienteligado) {
		this.clienteligado = clienteligado;
	}

	public List<Empleado> getEmpleadoligado() {
		return empleadoligado;
	}

	public void setEmpleadoligado(List<Empleado> empleadoligado) {
		this.empleadoligado = empleadoligado;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", password=" + password + ", username=" + username + ", estatus=" + estatus
				+ ", fechaRegistro=" + fechaRegistro + ", clienteligado=" + clienteligado + ", empleadoligado="
				+ empleadoligado + "]";
	}

	public Usuario(Integer id, String password, String username, Integer estatus, Date fechaRegistro,
			List<Cliente> clienteligado, List<Empleado> empleadoligado) {
		super();
		this.id = id;
		this.password = password;
		this.username = username;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
		this.clienteligado = clienteligado;
		this.empleadoligado = empleadoligado;
	}

	public Usuario() {
		super();
	}

	public Usuario(Integer id, String password, String username, Integer estatus, Date fechaRegistro,
			List<Perfil> perfiles, List<Cliente> clienteligado, List<Empleado> empleadoligado) {
		super();
		this.id = id;
		this.password = password;
		this.username = username;
		this.estatus = estatus;
		this.fechaRegistro = fechaRegistro;
		this.perfiles = perfiles;
		this.clienteligado = clienteligado;
		this.empleadoligado = empleadoligado;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}	
	
	
    
    
		
}
