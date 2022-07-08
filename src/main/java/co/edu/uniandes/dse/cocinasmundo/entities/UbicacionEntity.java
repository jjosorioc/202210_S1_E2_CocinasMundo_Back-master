package co.edu.uniandes.dse.cocinasmundo.entities;


import javax.persistence.Entity;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter 
public class UbicacionEntity extends BaseEntity{
	
	/**
	 * Atributo que representa la latitud de la ubicacion
	 */
	private Float latitudUbicacion;
	
	/**
	 * Atributo que representa la longitud de la ubicacion
	 */
	private Float longitudUbicacion;
	
}