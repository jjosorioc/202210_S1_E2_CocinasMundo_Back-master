package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaDTO {
	
	private Long id;

	private String nombre;
	
	private String descripcion;
	
	private String preparacion;
	
	private String imagenes;
	
	private PlatoDTO plato;
}