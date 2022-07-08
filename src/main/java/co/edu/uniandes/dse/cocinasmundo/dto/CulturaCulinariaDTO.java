package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CulturaCulinariaDTO {
	private Long id;
	private String nombreCultura;
	private String descripcionCultura;
	private Integer calificacionCultura;
	private String imagen;
	private PaisDTO pais;
	private RegionDTO region;
}