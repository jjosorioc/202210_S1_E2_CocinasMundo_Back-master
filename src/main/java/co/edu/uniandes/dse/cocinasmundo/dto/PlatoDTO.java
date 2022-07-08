package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoDTO {
	private Long id;
	private String nombrePlato;
	private String descripcionPlato;
	private Double precioDouble;
	private CulturaCulinariaDTO culturaCulinaria;
}
