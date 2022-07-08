package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredienteDetailDTO extends  IngredienteDTO  {

	private List<RecetaDTO> recetas = new ArrayList<RecetaDTO>();
}
