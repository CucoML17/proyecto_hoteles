package carlos.mejia.proyectohoteles.model;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reserva")
public class Reserva {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name="codigoReserva")
    private String codigoReserva;
	
	@Column(name="fechainicio")
    private Date fechainicio;	
	
	@Column(name="fechafin")
    private Date fechafin;
	
	@Column(name="estado")
    private String estado;		
	
	@Column(name="detalles")
    private String detalles;		
	
	@Column(name="precioTotal")
    private double precioTotal;	
	
	@Column(name="numeroOcupantes")
    private int numeroOcupantes;	
	
	
	//Sus relaciones
	
	//Cliente
	@ManyToOne
	@JoinColumn(name="idReservaCliente")
    private Cliente idReservaCliente;	
	
	//Empleado
	@ManyToOne
	@JoinColumn(name="idReservaEmpleado")
    private Empleado idReservaEmpleado;
	
	
	//Habitación
	@ManyToOne
	@JoinColumn(name="idReservaHabitacion")
    private Habitacion idReservaHabitacion;


	@Override
	public String toString() {
		return "Reserva [id=" + id + ", codigoReserva=" + codigoReserva + ", fechainicio=" + fechainicio + ", fechafin="
				+ fechafin + ", estado=" + estado + ", detalles=" + detalles + ", precioTotal=" + precioTotal
				+ ", numeroOcupantes=" + numeroOcupantes + ", idReservaCliente=" + idReservaCliente
				+ ", idReservaEmpleado=" + idReservaEmpleado + ", idReservaHabitacion=" + idReservaHabitacion + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodigoReserva() {
		return codigoReserva;
	}


	public void setCodigoReserva(String codigoReserva) {
		this.codigoReserva = codigoReserva;
	}


	public Date getFechainicio() {
		return fechainicio;
	}


	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}


	public Date getFechafin() {
		return fechafin;
	}


	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getDetalles() {
		return detalles;
	}


	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}


	public double getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}


	public int getNumeroOcupantes() {
		return numeroOcupantes;
	}


	public void setNumeroOcupantes(int numeroOcupantes) {
		this.numeroOcupantes = numeroOcupantes;
	}


	public Cliente getIdReservaCliente() {
		return idReservaCliente;
	}


	public void setIdReservaCliente(Cliente idReservaCliente) {
		this.idReservaCliente = idReservaCliente;
	}


	public Empleado getIdReservaEmpleado() {
		return idReservaEmpleado;
	}


	public void setIdReservaEmpleado(Empleado idReservaEmpleado) {
		this.idReservaEmpleado = idReservaEmpleado;
	}


	public Habitacion getIdReservaHabitacion() {
		return idReservaHabitacion;
	}


	public void setIdReservaHabitacion(Habitacion idReservaHabitacion) {
		this.idReservaHabitacion = idReservaHabitacion;
	}


	public Reserva(Integer id, String codigoReserva, Date fechainicio, Date fechafin, String estado, String detalles,
			double precioTotal, int numeroOcupantes, Cliente idReservaCliente, Empleado idReservaEmpleado,
			Habitacion idReservaHabitacion) {
		super();
		this.id = id;
		this.codigoReserva = codigoReserva;
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.estado = estado;
		this.detalles = detalles;
		this.precioTotal = precioTotal;
		this.numeroOcupantes = numeroOcupantes;
		this.idReservaCliente = idReservaCliente;
		this.idReservaEmpleado = idReservaEmpleado;
		this.idReservaHabitacion = idReservaHabitacion;
	}


	public Reserva() {
		super();
	}	
	
	
	
	
	
	
	
}
