package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
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
public class PaisEntity extends BaseEntity {

	/**
	 * Nombre del país.
	 */
	private String nombre;

	/**
	 * Ubicación geográfica del país.
	 */
	@PodamExclude
	@OneToOne() // Se omite el mappedBy
	@NotFound(action = NotFoundAction.IGNORE)
	private UbicacionEntity ubicacion;

	/**
	 * Ciudades dentro del país.
	 */
	@PodamExclude
	@OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
	List<CiudadEntity> ciudades = new ArrayList<CiudadEntity>();

	/**
	 * Regiones dentro del país.
	 */
	@PodamExclude
	@ManyToMany(fetch = FetchType.LAZY)
	List<RegionEntity> regiones = new ArrayList<RegionEntity>();

	/**
	 * Culturas culinarias asociadas al país.
	 */
	@PodamExclude
	@OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
	List<CulturaCulinariaEntity> culturasCulinarias = new ArrayList<CulturaCulinariaEntity>();
}
