package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaDetailDTO extends RecetaDTO {
	
	private List<IngredienteDTO> ingredientes = new ArrayList<IngredienteDTO>();

}
