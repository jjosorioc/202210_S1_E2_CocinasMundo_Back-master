package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Getter
@Setter

public class CiudadEntity extends BaseEntity {
	
	
	private String nombre;
	
	@PodamExclude
	@OneToOne()
	@NotFound(action=NotFoundAction.IGNORE)
	private UbicacionEntity ubicacion;
	
	@PodamExclude
	@ManyToOne()
	@NotFound(action=NotFoundAction.IGNORE)
	private PaisEntity pais;
	
	@PodamExclude
	@OneToMany(mappedBy = "ciudad", fetch = FetchType.LAZY)
	private List<RestauranteEntity> restaurantes = new ArrayList<>();

}
