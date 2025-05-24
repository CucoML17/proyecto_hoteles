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
@Table(name="habitacion")
public class Habitacion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name="numeroHabitacion")
    private int numeroHabitacion;
	
	@Column(name="tipoHabitacion")
    private String tipoHabitacion;
	
	@Column(name="estado")
    private int estado;		
	
	@Column(name="precioNoche")
    private double precioNoche;	
	
	@Column(name="capacidad")
    private int capacidad;	
	
	@Column(name="fotoHabitacion")
    private String fotoHabitacion;		

	
	
	//Su relación con hotel
	@ManyToOne
	@JoinColumn(name="idHotelHabitacion")
    private Hotel idHotelHabitacion;	
	
	//Su lista de reservas
    @OneToMany(mappedBy = "idReservaHabitacion", fetch = FetchType.EAGER)
    private List<Reserva> reservasHabitacion;	
    
    //Su lista de mantenimientos
    @OneToMany(mappedBy = "idMantenimientoHabitacion", fetch = FetchType.EAGER)
    private List<Mantenimiento> mantenimientosHabitacion;	



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(int numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public double getPrecioNoche() {
		return precioNoche;
	}

	public void setPrecioNoche(double precioNoche) {
		this.precioNoche = precioNoche;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getFotoHabitacion() {
		return fotoHabitacion;
	}

	public void setFotoHabitacion(String fotoHabitacion) {
		this.fotoHabitacion = fotoHabitacion;
	}

	public Hotel getIdHotelHabitacion() {
		return idHotelHabitacion;
	}

	public void setIdHotelHabitacion(Hotel idHotelHabitacion) {
		this.idHotelHabitacion = idHotelHabitacion;
	}

	public List<Reserva> getReservasHabitacion() {
		return reservasHabitacion;
	}

	public void setReservasHabitacion(List<Reserva> reservasHabitacion) {
		this.reservasHabitacion = reservasHabitacion;
	}

	
	
	

	public List<Mantenimiento> getMantenimientosHabitacion() {
		return mantenimientosHabitacion;
	}

	public void setMantenimientosHabitacion(List<Mantenimiento> mantenimientosHabitacion) {
		this.mantenimientosHabitacion = mantenimientosHabitacion;
	}

	public Habitacion(Integer id, int numeroHabitacion, String tipoHabitacion, int estado, double precioNoche,
			int capacidad, String fotoHabitacion, Hotel idHotelHabitacion, List<Reserva> reservasHabitacion,
			List<Mantenimiento> mantenimientosHabitacion) {
		super();
		this.id = id;
		this.numeroHabitacion = numeroHabitacion;
		this.tipoHabitacion = tipoHabitacion;
		this.estado = estado;
		this.precioNoche = precioNoche;
		this.capacidad = capacidad;
		this.fotoHabitacion = fotoHabitacion;
		this.idHotelHabitacion = idHotelHabitacion;
		this.reservasHabitacion = reservasHabitacion;
		this.mantenimientosHabitacion = mantenimientosHabitacion;
	}

	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", numeroHabitacion=" + numeroHabitacion + ", tipoHabitacion=" + tipoHabitacion
				+ ", estado=" + estado + ", precioNoche=" + precioNoche + ", capacidad=" + capacidad
				+ ", fotoHabitacion=" + fotoHabitacion + ", idHotelHabitacion=" + idHotelHabitacion
				+ ", reservasHabitacion=" + reservasHabitacion + ", mantenimientosHabitacion="
				+ mantenimientosHabitacion + "]";
	}

	public Habitacion() {
		super();
	}      
	
    
	

}
