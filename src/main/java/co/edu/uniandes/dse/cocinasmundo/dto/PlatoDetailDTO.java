package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoDetailDTO extends PlatoDTO {
	/*
	 * Relaciones con cardinalidad many.
	 */
	private List<RecetaDTO> recetas = new ArrayList<>();
	private List<RestauranteDTO> restaurantes = new ArrayList<>();

}
