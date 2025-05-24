package carlos.mejia.proyectohoteles.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleMantenimientoId implements Serializable {
	
	private Integer idempleado;
	private Integer idmantenimiento;
	
	
	
	
	public DetalleMantenimientoId() {
		super();
	}
	public DetalleMantenimientoId(Integer idempleado, Integer idmantenimiento) {
		super();
		this.idempleado = idempleado;
		this.idmantenimiento = idmantenimiento;
	}
	@Override
	public String toString() {
		return "DetalleMantenimientoId [idempleado=" + idempleado + ", idmantenimiento=" + idmantenimiento + "]";
	}
	public Integer getIdempleado() {
		return idempleado;
	}
	public void setIdempleado(Integer idempleado) {
		this.idempleado = idempleado;
	}
	public Integer getIdmantenimiento() {
		return idmantenimiento;
	}
	public void setIdmantenimiento(Integer idmantenimiento) {
		this.idmantenimiento = idmantenimiento;
	}
	@Override
	public int hashCode() {
		return Objects.hash(idempleado, idmantenimiento);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleMantenimientoId other = (DetalleMantenimientoId) obj;
		return Objects.equals(idempleado, other.idempleado) && Objects.equals(idmantenimiento, other.idmantenimiento);
	}
	
	
	
	
	
	

}
