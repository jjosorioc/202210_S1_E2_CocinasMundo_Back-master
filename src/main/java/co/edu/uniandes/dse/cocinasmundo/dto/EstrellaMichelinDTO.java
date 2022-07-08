package co.edu.uniandes.dse.cocinasmundo.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstrellaMichelinDTO {
	private Long id;
	private Date fechaEntrega;
	private String descripcion;
	private String tipoEstrella;
}
