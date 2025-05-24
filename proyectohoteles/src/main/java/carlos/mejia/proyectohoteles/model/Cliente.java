package carlos.mejia.proyectohoteles.model;

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
	
	
	@Column(name="nombre")
    private String nombre;
	
	@Column(name="fotoCliente")
    private String fotoCliente;	
	
	@Column(name="apellido")
    private String apellido;
	
	@Column(name="telefono")
    private String telefono;		
	
	@Column(name="correo")
    private String correo;	
	
	@Column(name="direccion")
    private String direccion;		
	
	@Column(name="estado")
    private int estado;	
	
	

	
	
	//Su lista de reservas
    @OneToMany(mappedBy = "idReservaCliente", fetch = FetchType.EAGER)
    private List<Reserva> reservas;

    
	//Su relación con usuario
	@ManyToOne
	@JoinColumn(name="idClienteUser")
    private Usuario idClienteUser;	
	
	
	



	public Cliente(Integer id, String nombre, String fotoCliente, String apellido, String telefono, String correo,
			String direccion, int estado, List<Reserva> reservas, Usuario idClienteUser) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fotoCliente = fotoCliente;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.estado = estado;
		this.reservas = reservas;
		this.idClienteUser = idClienteUser;
	}





	public Usuario getIdClienteUser() {
		return idClienteUser;
	}





	public void setIdClienteUser(Usuario idClienteUser) {
		this.idClienteUser = idClienteUser;
	}





	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", fotoCliente=" + fotoCliente + ", apellido=" + apellido
				+ ", telefono=" + telefono + ", correo=" + correo + ", direccion=" + direccion + ", estado=" + estado
				+ ", reservas=" + reservas + "]";
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





	public String getFotoCliente() {
		return fotoCliente;
	}





	public void setFotoCliente(String fotoCliente) {
		this.fotoCliente = fotoCliente;
	}





	public String getApellido() {
		return apellido;
	}





	public void setApellido(String apellido) {
		this.apellido = apellido;
	}





	public String getTelefono() {
		return telefono;
	}





	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}





	public String getCorreo() {
		return correo;
	}





	public void setCorreo(String correo) {
		this.correo = correo;
	}





	public String getDireccion() {
		return direccion;
	}





	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}





	public int getEstado() {
		return estado;
	}





	public void setEstado(int estado) {
		this.estado = estado;
	}





	public List<Reserva> getReservas() {
		return reservas;
	}





	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}





	public Cliente(Integer id, String nombre, String fotoCliente, String apellido, String telefono, String correo,
			String direccion, int estado, List<Reserva> reservas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fotoCliente = fotoCliente;
		this.apellido = apellido;
		this.telefono = telefono;
		this.correo = correo;
		this.direccion = direccion;
		this.estado = estado;
		this.reservas = reservas;
	}





	public Cliente() {
		super();
	}	

    
    
    
    
}
