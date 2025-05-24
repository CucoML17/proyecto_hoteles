package edu.listasproductost2.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleVentaId implements Serializable{
	private Integer idventa;
	private Integer idproducto;
	@Override
	public String toString() {
		return "DetalleVentaId [idventa=" + idventa + ", idproducto=" + idproducto + "]";
	}
	public Integer getIdventa() {
		return idventa;
	}
	public void setIdventa(Integer idventa) {
		this.idventa = idventa;
	}
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public DetalleVentaId(Integer idventa, Integer idproducto) {
		super();
		this.idventa = idventa;
		this.idproducto = idproducto;
	}
	public DetalleVentaId() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(idproducto, idventa);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleVentaId other = (DetalleVentaId) obj;
		return Objects.equals(idproducto, other.idproducto) && Objects.equals(idventa, other.idventa);
	}

	

	
	
	

}
