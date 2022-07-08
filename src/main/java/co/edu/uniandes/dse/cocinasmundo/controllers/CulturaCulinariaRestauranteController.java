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

import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.CulturaCulinariaRestauranteService;

@RestController
@RequestMapping("/culturasculinarias")
public class CulturaCulinariaRestauranteController {
	@Autowired
	private CulturaCulinariaRestauranteService culturaCulinariaRestauranteService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{culturaCulinariaID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO addRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("culturaCulinariaID") Long culturaCulinariaID) throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = culturaCulinariaRestauranteService.addRestaurante(culturaCulinariaID, restauranteId);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

	@GetMapping(value = "/{culturaCulinariaID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO getRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("culturaCulinariaID") Long culturaCulinariaID) throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = culturaCulinariaRestauranteService.getRestaurante(culturaCulinariaID, restauranteId);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

	@PutMapping(value = "/{culturaCulinariaID}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> addRestaurantes(@PathVariable("culturaCulinariaID") Long culturaCulinariaID,
			@RequestBody List<RestauranteDTO> restaurantes) throws EntityNotFoundException {
		List<RestauranteEntity> entities = modelMapper.map(restaurantes, new TypeToken<List<RestauranteEntity>>() {
		}.getType());
		List<RestauranteEntity> restaurantesList = culturaCulinariaRestauranteService.replaceRestaurantes(culturaCulinariaID, entities);
		return modelMapper.map(restaurantesList, new TypeToken<List<RestauranteDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{culturaCulinariaID}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> getRestaurantes(@PathVariable("culturaCulinariaID") Long culturaCulinariaID)
			throws EntityNotFoundException {
		List<RestauranteEntity> restauranteEntity = culturaCulinariaRestauranteService.getRestaurantes(culturaCulinariaID);
		return modelMapper.map(restauranteEntity, new TypeToken<List<RestauranteDetailDTO>>() {
		}.getType());
	}

	@DeleteMapping(value = "/{culturaCulinariaID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("culturaCulinariaID") Long culturaCulinariaID) throws EntityNotFoundException {
		culturaCulinariaRestauranteService.removeRestaurante(culturaCulinariaID, restauranteId);
	}
}
