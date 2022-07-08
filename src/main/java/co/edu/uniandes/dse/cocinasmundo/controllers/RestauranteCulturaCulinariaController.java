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

import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RestauranteCulturaCulinariaService;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteCulturaCulinariaController {
	@Autowired
	private RestauranteCulturaCulinariaService restauranteCulturaCulinariaService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 
	 * @param restauranteID
	 * @param culturaCulinariaID
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@GetMapping(value = "/{restauranteID}/culturasculinarias/{culturaCulinariaID}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO getCulturaCulinaria(@PathVariable("restauranteID") Long restauranteID,
			@PathVariable("culturaCulinariaID") Long culturaCulinariaID) throws EntityNotFoundException, IllegalOperationException {
		CulturaCulinariaEntity culturaCulinariaEntity = restauranteCulturaCulinariaService.getCulturaCulinaria(restauranteID, culturaCulinariaID);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}

	/**
	 * 
	 * @param restauranteId
	 * @return
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{restauranteID}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> getCulturasCulinarias(@PathVariable("restauranteID") Long restauranteId)
			throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturaCulinariaEntity = restauranteCulturaCulinariaService.getCulturasCulinarias(restauranteId);
		return modelMapper.map(culturaCulinariaEntity, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());
	}

	/**
	 * 
	 * @param restauranteId
	 * @param culturaCulinariaID
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PostMapping(value = "/{restauranteID}/culturasculinarias/{culturaCulinariaID}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO addCulturaCulinaria(@PathVariable("restauranteID") Long restauranteId,
			@PathVariable("culturaCulinariaID") Long culturaCulinariaID) throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = restauranteCulturaCulinariaService.addCulturaCulinaria(restauranteId, culturaCulinariaID);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}

	/**
	 * 
	 * @param restauranteId
	 * @param culturaculinaria
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{restauranteID}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> replaceCulturasCulinarias(@PathVariable("restauranteID") Long restauranteId,
			@RequestBody List<CulturaCulinariaDTO> culturasCulinarias) throws EntityNotFoundException {
		List<CulturaCulinariaEntity> entities = modelMapper.map(culturasCulinarias, new TypeToken<List<CulturaCulinariaEntity>>() {
		}.getType());
		List<CulturaCulinariaEntity> culturasList = restauranteCulturaCulinariaService.replaceCulturasCulinarias(restauranteId, entities);
		return modelMapper.map(culturasList, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());

	}

	/**
	 * 
	 * @param restauranteId
	 * @param culturaCulinariaID
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{restauranteID}/culturasculinarias/{culturaCulinariaID}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeCulturaCulinaria(@PathVariable("restauranteID") Long restauranteId, @PathVariable("culturaCulinariaID") Long culturaCulinariaID)
			throws EntityNotFoundException {
		restauranteCulturaCulinariaService.removeCulturaCulinaria(restauranteId, culturaCulinariaID);
	}
}
