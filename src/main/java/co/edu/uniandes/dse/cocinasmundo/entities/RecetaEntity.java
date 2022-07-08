package co.edu.uniandes.dse.cocinasmundo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
public class RecetaEntity extends BaseEntity {
	/**
	 * Atributo que representa el nombre de la receta
	 */
	private String nombre;
	
	/**
	 * Atributo que representa la descripción de la receta
	 */
	private String descripcion;
	
	/**
	 * Atributo que representa la preparación de la receta
	 */
	private String preparacion;	
	
	/**
	 * Atributo que representa las ubicaciones de las imágenes de las recetas
	 */
	private String imagenes;
	
	/**
	 * Atributo que representa la relación con la entidad Ingrediente
	 */
	@PodamExclude
	@ManyToMany(mappedBy="recetas", fetch=FetchType.LAZY)
	private List<IngredienteEntity> ingredientes = new ArrayList<IngredienteEntity>();
	
	/**
	 * Atributo que representa la relación con la entidad Plato
	 */
	@PodamExclude
	@ManyToOne
	private PlatoEntity plato;
}