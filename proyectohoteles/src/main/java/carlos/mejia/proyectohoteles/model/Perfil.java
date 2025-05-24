package carlos.mejia.proyectohoteles.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="perfil")
public class Perfil {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="perfil")
	private String perfil;

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", perfil=" + perfil + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Perfil(Integer id, String perfil) {
		super();
		this.id = id;
		this.perfil = perfil;
	}

	public Perfil() {
		super();
	}
	
	
}
