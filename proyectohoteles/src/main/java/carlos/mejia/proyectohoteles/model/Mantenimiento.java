package carlos.mejia.proyectohoteles.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="mantenimiento")
public class Mantenimiento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name="tipo")
    private String tipo;
	
    @Column(name="fecha")
    private Date fecha;
    @Column(name="detalles")
    private String detalles;
    
    @Column(name="estado")
    private String estado;	
	
	//Para la relación MUCHOS A MUCHOS  con DetalleMantenimiento
    @OneToMany(mappedBy = "mantenimientoDetalleMantenimiento")
    Set<DetalleMantenimiento> mantenimientoDetalleMantenimiento;
    
    //Para su relación con habitacion
	@ManyToOne
	@JoinColumn(name="idMantenimientoHabitacion")
    private Habitacion idMantenimientoHabitacion;	

    public List<Empleado> getEmpleadosAsignados() {
        List<Empleado> empleados = new ArrayList<>();
        if (mantenimientoDetalleMantenimiento != null) {
            for (DetalleMantenimiento detalle : mantenimientoDetalleMantenimiento) {
                empleados.add(detalle.getEmpleadoDetalleMantenimiento());
            }
        }
        return empleados;
    }
	

	public Set<DetalleMantenimiento> getMantenimientoDetalleMantenimiento() {
		return mantenimientoDetalleMantenimiento;
	}

	public void setMantenimientoDetalleMantenimiento(Set<DetalleMantenimiento> mantenimientoDetalleMantenimiento) {
		this.mantenimientoDetalleMantenimiento = mantenimientoDetalleMantenimiento;
	}

	public Habitacion getIdMantenimientoHabitacion() {
		return idMantenimientoHabitacion;
	}

	public void setIdMantenimientoHabitacion(Habitacion idMantenimientoHabitacion) {
		this.idMantenimientoHabitacion = idMantenimientoHabitacion;
	}

	public Mantenimiento(Integer id, String tipo, Date fecha, String detalles, String estado,
			Set<DetalleMantenimiento> mantenimientoDetalleMantenimiento, Habitacion idMantenimientoHabitacion) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.fecha = fecha;
		this.detalles = detalles;
		this.estado = estado;
		this.mantenimientoDetalleMantenimiento = mantenimientoDetalleMantenimiento;
		this.idMantenimientoHabitacion = idMantenimientoHabitacion;
	}

	@Override
	public String toString() {
		return "Mantenimiento [id=" + id + ", tipo=" + tipo + ", fecha=" + fecha + ", detalles=" + detalles
				+ ", estado=" + estado + ", mantenimientoDetalleMantenimiento=" + mantenimientoDetalleMantenimiento
				+ ", idMantenimientoHabitacion=" + idMantenimientoHabitacion + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Mantenimiento() {
		super();
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


    
    
	

}
