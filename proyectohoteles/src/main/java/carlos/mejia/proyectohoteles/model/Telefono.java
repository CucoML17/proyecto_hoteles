package carlos.mejia.proyectohoteles.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="telefono")
public class Telefono {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="telefono")
    private String telefono;
	
	//Su relación con hotel
	@ManyToOne
	@JoinColumn(name="idHotelTelefono")
	@JsonIgnore
    private Hotel idHotelTelefono;

	@Override
	public String toString() {
		return "Telefono [id=" + id + ", telefono=" + telefono + ", idHotel=" + idHotelTelefono + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public Hotel getIdHotelTelefono() {
		return idHotelTelefono;
	}

	public void setIdHotelTelefono(Hotel idHotelTelefono) {
		this.idHotelTelefono = idHotelTelefono;
	}

	public Telefono(Integer id, String telefono, Hotel idHotel) {
		super();
		this.id = id;
		this.telefono = telefono;
		this.idHotelTelefono = idHotel;
	}

	public Telefono() {
		super();
	}	
	
	
	
	

}
