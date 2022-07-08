package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredienteDTO {
	
	private Long id;
	
	private String nombre;
	
	private String categoria;
	
	private String historia;
	
	private String marcasRepresentativas;	
}
