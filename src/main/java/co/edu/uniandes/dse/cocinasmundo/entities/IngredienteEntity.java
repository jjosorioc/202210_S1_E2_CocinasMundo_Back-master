package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad que modela el ingrediente
 *
 * @author Alejandro Tovar
 */

@Entity
@Getter
@Setter
public class IngredienteEntity extends BaseEntity {
	/**
	 * Atributo que representa el nombre del ingrediente
	 */
	private String nombre;
	
	/**
	 * Atributo que representa la categoria a la que pertenece el ingrediente
	 */
	private String categoria;
	
	/**
	 * Atributo que representa la descripción del ingrediente
	 */
	private String descripcion;
	
	/**
	 * Atributo que representa la historia del ingrediente
	 */
	private String historia;
	
	/**
	 * Atributo que representa las marcas representativas del ingrediente
	 */
	private String marcasRepresentativas;
	
	/**
	 * Atributo que representa la relación con la entidad Receta
	 */
	@PodamExclude
	@ManyToMany
	private List<RecetaEntity> recetas = new ArrayList<RecetaEntity>();
}