/**
 * 
 */
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
public class PlatoEntity extends BaseEntity {

	/**
	 * Nombre del plato.
	 */
	private String nombrePlato;

	/**
	 * Descripción del plato.
	 */
	private String descripcionPlato;

	/**
	 * Precio del plato.
	 */
	private double precio;

	/**
	 * Lista de recetas que contienen este plato.
	 */
	@PodamExclude
	@OneToMany(mappedBy = "plato", fetch = FetchType.LAZY)
	List<RecetaEntity> recetas = new ArrayList<RecetaEntity>();

	/**
	 * Lista de los restaurantes que venden el plato.
	 */
	@PodamExclude
	@ManyToMany(mappedBy = "platos", fetch = FetchType.LAZY)
	List<RestauranteEntity> restaurantes = new ArrayList<RestauranteEntity>();

	/**
	 * Cultura culinaria a la que pertenece el plato
	 */
	@PodamExclude
	@ManyToOne()
	private CulturaCulinariaEntity culturaCulinaria;

}
