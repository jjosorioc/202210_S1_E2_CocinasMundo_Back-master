package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisDetailDTO extends PaisDTO {
	/*
	 * Relaciones con cardinalidad many.
	 */
	private List<CiudadDTO> ciudades = new ArrayList<>();
	private List<RegionDTO> regiones = new ArrayList<>();
	private List<CulturaCulinariaDTO> culturasCulinarias = new ArrayList<>();
}
