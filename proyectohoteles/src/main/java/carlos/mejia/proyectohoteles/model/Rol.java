package carlos.mejia.proyectohoteles.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="rol")
public class Rol {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name="tipo")
    private String tipo;

	@Override
	public String toString() {
		return "Rol [id=" + id + ", tipo=" + tipo + "]";
	}

	public Rol(Integer id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public Rol() {
		super();
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
	
	
	
	

	
}
