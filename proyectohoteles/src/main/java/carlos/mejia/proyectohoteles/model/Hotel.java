package carlos.mejia.proyectohoteles.model;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="hotel")
public class Hotel {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	@Column(name="email")
	private String email;
	@Column(name="direccion")
	private String direccion;
	@Column(name="fotoHotel")
	private String fotoHotel;
	
	//PrecioPromedio
	@Column(name="precioPromedio")
	private double precioPromedio;
	
	@Column(name="estado")
	private int estado;	
	
	//Sus uno a muchos, vaya sus listados
	//Con telefonos
    @OneToMany(mappedBy = "idHotelTelefono", fetch = FetchType.EAGER)
    private List<Telefono> telefonos;
    //Con Empleados
    @OneToMany(mappedBy = "idHotelEmpleado", fetch = FetchType.EAGER)
    private List<Empleado> empleados;
    //Con Habitaciones
    @OneToMany(mappedBy = "idHotelHabitacion", fetch = FetchType.EAGER)
    private List<Habitacion> habitaciones;

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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFotoHotel() {
		return fotoHotel;
	}

	public void setFotoHotel(String fotoHotel) {
		this.fotoHotel = fotoHotel;
	}

	public List<Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", nombre=" + nombre + ", email=" + email + ", direccion=" + direccion
				+ ", fotoHotel=" + fotoHotel + ", precioPromedio=" + precioPromedio + ", estado=" + estado
				+ ", telefonos=" + telefonos + ", empleados=" + empleados + ", habitaciones=" + habitaciones + "]";
	}

	public Hotel(Integer id, String nombre, String email, String direccion, String fotoHotel, List<Telefono> telefonos,
			List<Empleado> empleados, List<Habitacion> habitaciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.direccion = direccion;
		this.fotoHotel = fotoHotel;
		this.telefonos = telefonos;
		this.empleados = empleados;
		this.habitaciones = habitaciones;
	}

	public Hotel() {
		super();
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Hotel(Integer id, String nombre, String email, String direccion, String fotoHotel, int estado,
			List<Telefono> telefonos, List<Empleado> empleados, List<Habitacion> habitaciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.direccion = direccion;
		this.fotoHotel = fotoHotel;
		this.estado = estado;
		this.telefonos = telefonos;
		this.empleados = empleados;
		this.habitaciones = habitaciones;
	}

	public double getPrecioPromedio() {
		return precioPromedio;
	}

	public void setPrecioPromedio(double precioPromedio) {
		this.precioPromedio = precioPromedio;
	}

	public Hotel(Integer id, String nombre, String email, String direccion, String fotoHotel, double precioPromedio,
			int estado, List<Telefono> telefonos, List<Empleado> empleados, List<Habitacion> habitaciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.direccion = direccion;
		this.fotoHotel = fotoHotel;
		this.precioPromedio = precioPromedio;
		this.estado = estado;
		this.telefonos = telefonos;
		this.empleados = empleados;
		this.habitaciones = habitaciones;
	}    
    
	
    
	
	
	
	
	
	


	
}
