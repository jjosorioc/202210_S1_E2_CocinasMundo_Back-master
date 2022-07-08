package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Getter
@Setter

public class CulturaCulinariaEntity extends BaseEntity{
	
//	Atributos
	
	private String nombreCultura;
	
	private String descripcionCultura ;
	
	private Integer calificacionCultura;
	
	private String imagen;
	
	@PodamExclude
	@OneToMany(mappedBy="culturaCulinaria", fetch=FetchType.LAZY)
	private List<PlatoEntity> recetasMasRepresentativas = new ArrayList<>();
	
	@PodamExclude
	@ManyToMany(mappedBy = "culturasCulinarias", fetch =FetchType.LAZY)
	private List<RestauranteEntity> restaurantes = new ArrayList<>();
	
	@PodamExclude
	@ManyToOne()
	private PaisEntity pais;
	
	@PodamExclude
	@ManyToOne()
	private RegionEntity region;
	

}
