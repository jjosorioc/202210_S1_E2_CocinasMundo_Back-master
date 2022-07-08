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
import co.edu.uniandes.dse.cocinasmundo.services.PlatoRestauranteService;

@RestController
@RequestMapping("/platos")
public class PlatoRestauranteController {

	@Autowired
	private PlatoRestauranteService platoRestauranteService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{platoID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO addRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("platoID") Long platoId) throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = platoRestauranteService.addRestaurante(platoId, restauranteId);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

	@GetMapping(value = "/{platoID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO getRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("platoID") Long platoId) throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = platoRestauranteService.getRestaurante(platoId, restauranteId);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

	@PutMapping(value = "/{platoID}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> addRestaurantes(@PathVariable("platoID") Long platoId,
			@RequestBody List<RestauranteDTO> restaurantes) throws EntityNotFoundException {
		List<RestauranteEntity> entities = modelMapper.map(restaurantes, new TypeToken<List<RestauranteEntity>>() {
		}.getType());
		List<RestauranteEntity> restaurantesList = platoRestauranteService.replaceRestaurantes(platoId, entities);
		return modelMapper.map(restaurantesList, new TypeToken<List<RestauranteDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{platoID}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> getRestaurantes(@PathVariable("platoID") Long platoId)
			throws EntityNotFoundException {
		List<RestauranteEntity> restauranteEntity = platoRestauranteService.getRestaurantes(platoId);
		return modelMapper.map(restauranteEntity, new TypeToken<List<RestauranteDetailDTO>>() {
		}.getType());
	}

	@DeleteMapping(value = "/{platoID}/restaurantes/{restauranteID}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeRestaurante(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("platoID") Long platoId) throws EntityNotFoundException {
		platoRestauranteService.removeRestaurante(platoId, restauranteId);
	}
}
