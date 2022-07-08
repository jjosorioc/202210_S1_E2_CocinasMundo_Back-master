package co.edu.uniandes.dse.cocinasmundo.entities;

import java.sql.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class EstrellaMichelinEntity extends BaseEntity {
	
	/**
	 * Fecha en la que fue entregada la estrella
	 */
	private Integer fechaEntregada;
	
	/**
	 * Descripcion de la estrella
	 */
	private String descripcion;
	
	/**
	 * Tipo de estrella que fue entregada
	 */
	private String tipoEstrella;
	
}
