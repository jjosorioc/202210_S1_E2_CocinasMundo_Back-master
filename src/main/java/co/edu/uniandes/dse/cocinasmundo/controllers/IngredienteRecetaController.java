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

import co.edu.uniandes.dse.cocinasmundo.dto.RecetaDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RecetaDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.IngredienteRecetaService;

/**
 * Clase que implementa el recurso "ingredientees/{id}/recetas".
 *
 * @author Tomas Angel
 */
@RestController
@RequestMapping("/ingredientes")

public class IngredienteRecetaController {
	
	@Autowired
	private IngredienteRecetaService ingredienteRecetaService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/{ingredienteId}/recetas/{recetaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RecetaDetailDTO addReceta(@PathVariable("recetaId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException {
		RecetaEntity recetaEntity = ingredienteRecetaService.addReceta(ingredienteId, recetaId);
		return modelMapper.map(recetaEntity, RecetaDetailDTO.class);
	}
	
	@GetMapping(value = "/{ingredienteId}/recetas/{recetaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RecetaDetailDTO getReceta(@PathVariable("recetaId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException, IllegalOperationException {
		RecetaEntity recetaEntity = ingredienteRecetaService.getReceta(ingredienteId, recetaId);
		return modelMapper.map(recetaEntity, RecetaDetailDTO.class);
	}
	
	@PutMapping(value = "/{ingredienteId}/recetas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RecetaDetailDTO> addRecetas(@PathVariable("ingredienteId") Long ingredienteId, @RequestBody List<RecetaDTO> recetas)
			throws EntityNotFoundException {
		List<RecetaEntity> entities = modelMapper.map(recetas, new TypeToken<List<RecetaEntity>>() {
		}.getType());
		List<RecetaEntity> recetasList = ingredienteRecetaService.replaceRecetas(ingredienteId, entities);
		return modelMapper.map(recetasList, new TypeToken<List<RecetaDetailDTO>>() {
		}.getType());
	}
	
	@GetMapping(value = "/{ingredienteId}/recetas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RecetaDetailDTO> getRecetas(@PathVariable("ingredienteId") Long ingredienteId) throws EntityNotFoundException {
		List<RecetaEntity> recetaEntity = ingredienteRecetaService.getRecetas(ingredienteId);
		return modelMapper.map(recetaEntity, new TypeToken<List<RecetaDetailDTO>>() {
		}.getType());
	}
	
	@DeleteMapping(value = "/{ingredienteId}/recetas/{recetaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeReceta(@PathVariable("recetaId") Long recetaId, @PathVariable("ingredienteId") Long ingredienteId)
			throws EntityNotFoundException {
		ingredienteRecetaService.removeReceta(ingredienteId, recetaId);
	}

}
