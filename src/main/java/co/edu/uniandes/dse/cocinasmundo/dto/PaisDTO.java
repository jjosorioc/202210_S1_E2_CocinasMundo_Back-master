package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisDTO {
	private Long id;
	private String nombre;
	private UbicacionDTO ubicacion;
}
