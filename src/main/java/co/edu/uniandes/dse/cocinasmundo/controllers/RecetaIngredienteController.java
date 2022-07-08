package co.edu.uniandes.dse.cocinasmundo.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.cocinasmundo.dto.IngredienteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.IngredienteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RecetaIngredienteService;

/**
 * Clase que implementa el recurso "receta/{id}/ingredientes".
 *
 * @author Tomas Angel
 */
@RestController
@RequestMapping("/recetas")

public class RecetaIngredienteController {
	
	@Autowired
	private RecetaIngredienteService recetaIngredienteService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/{recetaId}/ingredientes/{ingredienteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public IngredienteDetailDTO getIngrediente(@PathVariable("recetaId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException, IllegalOperationException {
		IngredienteEntity ingredienteEntity = recetaIngredienteService.getIngrediente(recetaId, ingredienteId);
		return modelMapper.map(ingredienteEntity, IngredienteDetailDTO.class);
	}
	
	@GetMapping(value = "/{recetaId}/ingredientes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<IngredienteDetailDTO> getIngredientes(@PathVariable("recetaId") Long recetaId) throws EntityNotFoundException {
		List<IngredienteEntity> ingredienteEntity = recetaIngredienteService.getIngredientes(recetaId);
		return modelMapper.map(ingredienteEntity, new TypeToken<List<IngredienteDetailDTO>>() {
		}.getType());
	}
	
	@PostMapping(value = "/{recetaId}/ingredientes/{ingredienteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public IngredienteDetailDTO addIngrediente(@PathVariable("recetaId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException {
		IngredienteEntity ingredienteEntity = recetaIngredienteService.addIngrediente(recetaId, ingredienteId);
		return modelMapper.map(ingredienteEntity, IngredienteDetailDTO.class);
	}
	
	@PutMapping(value = "/{recetaId}/ingredientes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<IngredienteDetailDTO> replaceIngredientes(@PathVariable("recetaId") Long recetaId, @RequestBody List<IngredienteDTO> ingredientes)
			throws EntityNotFoundException {
		List<IngredienteEntity> entities = modelMapper.map(ingredientes, new TypeToken<List<IngredienteEntity>>() {
		}.getType());
		List<IngredienteEntity> ingredientesList = recetaIngredienteService.addIngredientes(recetaId, entities);
		return modelMapper.map(ingredientesList, new TypeToken<List<IngredienteDetailDTO>>() {
		}.getType());
	}
	
	@DeleteMapping(value = "/{regoinId}/ingredientes/{ingredienteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeIngrediente(@PathVariable("regoinId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException {
		recetaIngredienteService.removeIngrediente(recetaId, ingredienteId);
	}
	
}



