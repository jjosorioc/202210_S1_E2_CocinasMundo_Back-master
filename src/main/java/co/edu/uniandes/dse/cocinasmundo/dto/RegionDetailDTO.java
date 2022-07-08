package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDetailDTO extends RegionDTO {
	/*
	 * Relaciones con cardinalidad many.
	 */
	private List<PaisDTO> paises = new ArrayList<>();
	private List<CulturaCulinariaDTO> culturasCulinarias = new ArrayList<>();
}
