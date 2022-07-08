package co.edu.uniandes.dse.cocinasmundo.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import co.edu.uniandes.dse.cocinasmundo.dto.CiudadDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.CiudadRestauranteService;


@RestController
@RequestMapping("/ciudades")
public class CiudadRestauranteController {

	@Autowired
	private CiudadRestauranteService ciudadRestauranteService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{ciudadId}/restaurantes/{restauranteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDTO addRestaurante(@PathVariable("ciudadId") Long ciudadId, @PathVariable("restauranteId") Long restauranteId)
			throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = ciudadRestauranteService.addRestaurante(restauranteId, ciudadId);
		return modelMapper.map(restauranteEntity, RestauranteDTO.class);
	}

	@GetMapping(value = "/{ciudadId}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> getRestaurantes(@PathVariable("ciudadId") Long ciudadId) throws EntityNotFoundException {
		List<RestauranteEntity> restaurantesList = ciudadRestauranteService.getRestaurantes(ciudadId);
		return modelMapper.map(restaurantesList, new TypeToken<List<RestauranteDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{ciudadId}/restaurantes/{restauranteId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO getRestaurante(@PathVariable("ciudadId") Long ciudadId, @PathVariable("restauranteId") Long restauranteId)
			throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = ciudadRestauranteService.getRestaurante(ciudadId, restauranteId);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

	@PutMapping(value = "/{ciudadId}/restaurantes")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> replaceRestaurantes(@PathVariable("ciudadId") Long ciudadId,
			@RequestBody List<RestauranteDetailDTO> restaurantes) throws EntityNotFoundException {
		List<RestauranteEntity> restaurantesList = modelMapper.map(restaurantes, new TypeToken<List<CiudadEntity>>() {
		}.getType());
		List<RestauranteEntity> result = ciudadRestauranteService.replaceRestaurantes(ciudadId, restaurantesList);
		return modelMapper.map(result, new TypeToken<List<CiudadDetailDTO>>() {
		}.getType());
	}
}