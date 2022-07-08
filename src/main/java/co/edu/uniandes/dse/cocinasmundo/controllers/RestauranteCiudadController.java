package co.edu.uniandes.dse.cocinasmundo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.CiudadDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.RestauranteCiudadService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteCiudadController {

	@Autowired
	private RestauranteCiudadService restauranteCiudadService;

	@Autowired
	private ModelMapper modelMapper;


	@PutMapping(value = "/{restauranteId}/ciudad")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO replaceCiudad (@PathVariable("restauranteId") Long restauranteId, @RequestBody CiudadDTO ciudadDTO)
			throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = restauranteCiudadService.replaceCiudad(restauranteId, ciudadDTO.getId());
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}

}