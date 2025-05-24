package carlos.mejia.proyectohoteles.model;


import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "detallemantenimiento")
public class DetalleMantenimiento {
    @EmbeddedId
    private DetalleMantenimientoId id;
    
    @ManyToOne
    @MapsId("idempleado")
    @JoinColumn(name = "idempleado")
    private Empleado empleadoDetalleMantenimiento;//Tiene que ser la entidad/modelo, ya no el atributo, por eso
    //se le coloca el MapsId Para saber a qué atributo se refiere

    @ManyToOne
    @MapsId("idmantenimiento")
    @JoinColumn(name = "idmantenimiento")
    private Mantenimiento mantenimientoDetalleMantenimiento;//Tiene que ser la entidad/modelo, ya no el atributo, por eso
    //se le coloca el MapsId Para saber a qué atributo se refiere

    
    
	@Override
	public String toString() {
		return "DetalleMantenimiento [id=" + id + ", empleadoDetalleMantenimiento=" + empleadoDetalleMantenimiento
				+ ", mantenimientoDetalleMantenimiento=" + mantenimientoDetalleMantenimiento + "]";
	}

	public DetalleMantenimientoId getId() {
		return id;
	}

	public void setId(DetalleMantenimientoId id) {
		this.id = id;
	}

	public Empleado getEmpleadoDetalleMantenimiento() {
		return empleadoDetalleMantenimiento;
	}

	public void setEmpleadoDetalleMantenimiento(Empleado empleadoDetalleMantenimiento) {
		this.empleadoDetalleMantenimiento = empleadoDetalleMantenimiento;
	}

	public Mantenimiento getMantenimientoDetalleMantenimiento() {
		return mantenimientoDetalleMantenimiento;
	}

	public void setMantenimientoDetalleMantenimiento(Mantenimiento mantenimientoDetalleMantenimiento) {
		this.mantenimientoDetalleMantenimiento = mantenimientoDetalleMantenimiento;
	}

	public DetalleMantenimiento(DetalleMantenimientoId id, Empleado empleadoDetalleMantenimiento,
			Mantenimiento mantenimientoDetalleMantenimiento) {
		super();
		this.id = id;
		this.empleadoDetalleMantenimiento = empleadoDetalleMantenimiento;
		this.mantenimientoDetalleMantenimiento = mantenimientoDetalleMantenimiento;
	}

	public DetalleMantenimiento() {
		super();
	}

    //Ahora sí, podemos colocar los demás atributos, porque esta sí es la relación en la BD.
    
	
	
    

}
