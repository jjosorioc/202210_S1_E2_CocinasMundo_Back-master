package co.edu.uniandes.dse.cocinasmundo.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDetailDTO extends RestauranteDTO{
	
	private List<CulturaCulinariaDTO> culturasCulinarias = new ArrayList<>();
	private List<PlatoDTO> platos = new ArrayList<>();
	
}
