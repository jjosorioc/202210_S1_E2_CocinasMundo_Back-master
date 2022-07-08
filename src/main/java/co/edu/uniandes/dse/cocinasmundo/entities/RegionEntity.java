package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Getter
@Setter

public class RegionEntity extends BaseEntity {
	
	private String nombre;
	
	@PodamExclude
	@OneToMany(mappedBy = "region", fetch=FetchType.LAZY)
	private List<CulturaCulinariaEntity> culturasCulinarias = new ArrayList<>();
	
	@PodamExclude
	@ManyToMany(mappedBy = "regiones", fetch=FetchType.LAZY)
	private List<PaisEntity> paises = new ArrayList<>();

}
