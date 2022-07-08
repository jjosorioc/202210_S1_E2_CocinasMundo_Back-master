package co.edu.uniandes.dse.cocinasmundo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	private Long id;
	private String nombreRestaurante;
	private String direccionRestaurante;
	private EstrellaMichelinDTO estrellaMichellin;
	private CiudadDTO ciudad;
	private String imagen;
}
