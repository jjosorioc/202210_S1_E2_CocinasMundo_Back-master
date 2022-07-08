package co.edu.uniandes.dse.cocinasmundo.entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Getter
@Setter
public class RestauranteEntity extends BaseEntity{

	
	/**
	 * Atributo que representa el nombre del restaurante
	 */
	private String nombreRestaurante;
	
	/**
	 * Atributo que representa la direccion del restaurante
	 */
	private String direccionRestaurante;
	
	private String imagen;
	
	
	
	/**
	 * Atributo que representa la relacion con la entidad Ciudad
	 */
	@PodamExclude
	@ManyToOne
	private CiudadEntity ciudad;
	
	/**
	 * Atributo que representa las o la cultura culinaria presente en el restaurante
	 */
	@PodamExclude
	@ManyToMany
	private List<CulturaCulinariaEntity> culturasCulinarias = new ArrayList<CulturaCulinariaEntity>();
	
	/**
	 * Atributo que representa los platos que ofrece el restaurante
	 */
	@PodamExclude
	@ManyToMany
	private List<PlatoEntity> platos = new ArrayList<PlatoEntity>();
	
	/**
	 * Atributo que representa la estrella michellin asignada al restaurante
	 */
	@PodamExclude
	@OneToOne
	private EstrellaMichelinEntity estrellaMichellin;
	
}
