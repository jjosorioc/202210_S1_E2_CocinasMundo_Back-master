package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CulturaCulinariaDetailDTO extends CulturaCulinariaDTO {
	/*
	 * Relaciones con cardinalidad many.
	 */
	private List<RestauranteDTO> restaurantes = new ArrayList<>();
	private List<PlatoDTO> recetasMasRepresentativas = new ArrayList<>();
	
}

