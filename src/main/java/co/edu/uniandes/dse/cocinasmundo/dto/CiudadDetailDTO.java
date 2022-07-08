package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CiudadDetailDTO extends CiudadDTO {
	/*
	 * Relaciones con cardinalidad many.
	 */
	private List<RestauranteDTO> restaurantes = new ArrayList<>();
	
}