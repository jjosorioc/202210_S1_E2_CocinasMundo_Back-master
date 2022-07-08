package co.edu.uniandes.dse.cocinasmundo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CiudadDTO {
	private Long id;
	private String nombre;
	private PaisDTO pais;
	private UbicacionDTO ubicacion;
}
