package carlos.mejia.proyectohoteles.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="empleado")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name="nombre")
    private String nombre;
	
	@Column(name="apellido")
    private String apellido;
	
	@Column(name="fotoEmpleado")
    private String fotoEmpleado;		
	
	@Column(name="telefono")
    private String telefono;	
	
	@Column(name="rfc")
    private String rfc;	
	
	@Column(name="correo")
    private String correo;		
	
	@Column(name="estado")
    private int estado;	
	
	
	//Su relación con hotel
	@ManyToOne
	@JoinColumn(name="idHotelEmpleado")
    private Hotel idHotelEmpleado;	
	
	//Su lista de reservas atendidas
	//Su lista de reservas
    @OneToMany(mappedBy = "idReservaEmpleado", fetch = FetchType.EAGER)
    private List<Reserva> reservasAtendidas;	
	
    
	//MUCHOS A MUCHOS (primera forma)
	//El many to many
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="empleadorol", 
			joinColumns = @JoinColumn(name="idEmpleado"),
			inverseJoinColumns = @JoinColumn(name="idRol"))
	
	private List<Rol> roles = new LinkedList<>();
	
	public void agregarRolEmpleado(Rol tmpRol) {
		if (roles==null) {
			//Sino encuentra perfiles, entonces crea la liste
			roles = new LinkedList<Rol>();
			
		}
		//Si encuentra, quiere decir que ya tiene perfil, solo agrega más
		roles.add(tmpRol);
		
	}	    
	
	//Para la relación MUCHOS A MUCHOS
    @OneToMany(mappedBy = "empleadoDetalleMantenimiento")
    Set<DetalleMantenimiento> empleadoDetalleMantenimiento;

    
	//Su relación con usuario
	@ManyToOne
	@JoinColumn(name="idEmpleadoUser")
    private Usuario idEmpleadoUser;
	
	
	


	public Usuario getIdEmpleadoUser() {
		return idEmpleadoUser;
	}

	public void setIdEmpleadoUser(Usuario idEmpleadoUser) {
		this.idEmpleadoUser = idEmpleadoUser;
	}

	public Empleado(Integer id, String nombre, String apellido, String fotoEmpleado, String telefono, String rfc,
			String correo, int estado, Hotel idHotelEmpleado, List<Reserva> reservasAtendidas, List<Rol> roles,
			Set<DetalleMantenimiento> empleadoDetalleMantenimiento, Usuario idEmpleadoUser) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fotoEmpleado = fotoEmpleado;
		this.telefono = telefono;
		this.rfc = rfc;
		this.correo = correo;
		this.estado = estado;
		this.idHotelEmpleado = idHotelEmpleado;
		this.reservasAtendidas = reservasAtendidas;
		this.roles = roles;
		this.empleadoDetalleMantenimiento = empleadoDetalleMantenimiento;
		this.idEmpleadoUser = idEmpleadoUser;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fotoEmpleado=" + fotoEmpleado
				+ ", telefono=" + telefono + ", rfc=" + rfc + ", correo=" + correo + ", estado=" + estado
				+ ", idHotelEmpleado=" + idHotelEmpleado + ", reservasAtendidas=" + reservasAtendidas + ", roles="
				+ roles + ", empleadoDetalleMantenimiento=" + empleadoDetalleMantenimiento + "]";
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFotoEmpleado() {
		return fotoEmpleado;
	}

	public void setFotoEmpleado(String fotoEmpleado) {
		this.fotoEmpleado = fotoEmpleado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Hotel getIdHotelEmpleado() {
		return idHotelEmpleado;
	}

	public void setIdHotelEmpleado(Hotel idHotelEmpleado) {
		this.idHotelEmpleado = idHotelEmpleado;
	}

	public List<Reserva> getReservasAtendidas() {
		return reservasAtendidas;
	}

	public void setReservasAtendidas(List<Reserva> reservasAtendidas) {
		this.reservasAtendidas = reservasAtendidas;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Set<DetalleMantenimiento> getEmpleadoDetalleMantenimiento() {
		return empleadoDetalleMantenimiento;
	}

	public void setEmpleadoDetalleMantenimiento(Set<DetalleMantenimiento> empleadoDetalleMantenimiento) {
		this.empleadoDetalleMantenimiento = empleadoDetalleMantenimiento;
	}

	public Empleado(Integer id, String nombre, String apellido, String fotoEmpleado, String telefono, String rfc,
			String correo, int estado, Hotel idHotelEmpleado, List<Reserva> reservasAtendidas, List<Rol> roles,
			Set<DetalleMantenimiento> empleadoDetalleMantenimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fotoEmpleado = fotoEmpleado;
		this.telefono = telefono;
		this.rfc = rfc;
		this.correo = correo;
		this.estado = estado;
		this.idHotelEmpleado = idHotelEmpleado;
		this.reservasAtendidas = reservasAtendidas;
		this.roles = roles;
		this.empleadoDetalleMantenimiento = empleadoDetalleMantenimiento;
	}

	public Empleado() {
		super();
		this.roles = new LinkedList<>();
	} 	
	
    

}
