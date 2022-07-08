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

import co.edu.uniandes.dse.cocinasmundo.dto.PlatoDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PlatoDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RestaurantePlatoService;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantePlatoController {

	@Autowired
	private RestaurantePlatoService restaurantePlatoService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 
	 * @param restauranteID
	 * @param platoID
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@GetMapping(value = "/{restauranteID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDetailDTO getPlato(@PathVariable("restauranteID") Long restauranteID,
			@PathVariable("platoID") Long platoID) throws EntityNotFoundException, IllegalOperationException {
		PlatoEntity platoEntity = restaurantePlatoService.getPlato(restauranteID, platoID);
		return modelMapper.map(platoEntity, PlatoDetailDTO.class);
	}

	/**
	 * 
	 * @param restauranteId
	 * @return
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{restauranteID}/platos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlatoDetailDTO> getPlatos(@PathVariable("restauranteID") Long restauranteId)
			throws EntityNotFoundException {
		List<PlatoEntity> platoEntity = restaurantePlatoService.getPlatos(restauranteId);
		return modelMapper.map(platoEntity, new TypeToken<List<PlatoDetailDTO>>() {
		}.getType());
	}

	/**
	 * 
	 * @param restauranteId
	 * @param platoId
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@PostMapping(value = "/{restauranteID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDetailDTO addPlato(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("platoID") Long platoId) throws EntityNotFoundException, IllegalOperationException {
		PlatoEntity platoEntity = restaurantePlatoService.addPlato(restauranteId, platoId);
		return modelMapper.map(platoEntity, PlatoDetailDTO.class);
	}

	/**
	 * 
	 * @param restauranteId
	 * @param platos
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{restauranteID}/platos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlatoDetailDTO> replacePlatos(@PathVariable("restauranteID") Long restauranteId,
			@RequestBody List<PlatoDTO> platos) throws EntityNotFoundException {
		List<PlatoEntity> entities = modelMapper.map(platos, new TypeToken<List<PlatoEntity>>() {
		}.getType());
		List<PlatoEntity> platosList = restaurantePlatoService.replacePlatos(restauranteId, entities);
		return modelMapper.map(platosList, new TypeToken<List<PlatoDetailDTO>>() {
		}.getType());

	}

	/**
	 * 
	 * @param restauranteId
	 * @param platoId
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{restauranteID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePlato(@PathVariable("restauranteID") Long restauranteId, @PathVariable("platoID") Long platoId)
			throws EntityNotFoundException {
		restaurantePlatoService.removePlato(restauranteId, platoId);
	}
}
