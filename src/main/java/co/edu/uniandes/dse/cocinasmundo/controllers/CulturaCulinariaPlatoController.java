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
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.CulturaCulinariaPlatoService;

@RestController
@RequestMapping("/culturasculinarias")
public class CulturaCulinariaPlatoController {

	@Autowired
	private CulturaCulinariaPlatoService culturaCulinariaPlatoService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 
	 * @param culturaCulinariaID
	 * @param platolID
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@PostMapping(value = "/{culturaCulinariaID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDTO addPlato(@PathVariable("culturaCulinariaID") Long culturaCulinariaID,
			@PathVariable("platoID") Long platolID) throws EntityNotFoundException, IllegalOperationException {
		PlatoEntity platoEntity = culturaCulinariaPlatoService.addPlato(platolID, culturaCulinariaID);
		return modelMapper.map(platoEntity, PlatoDTO.class);
	}

	/**
	 * 
	 * @param culturaCulinariaID
	 * @return
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{culturaCulinariaID}/platos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlatoDTO> getPlatos(@PathVariable("culturaCulinariaID") Long culturaCulinariaID)
			throws EntityNotFoundException {
		List<PlatoEntity> platoList = culturaCulinariaPlatoService.getPlatos(culturaCulinariaID);
		return modelMapper.map(platoList, new TypeToken<List<PlatoDTO>>() {
		}.getType());
	}

	/**
	 * 
	 * @param culturaCulinariaID
	 * @param platoID
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@GetMapping(value = "/{culturaCulinariaID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDTO getPlato(@PathVariable("culturaCulinariaID") Long culturaCulinariaID,
			@PathVariable("platoID") Long platoID) throws EntityNotFoundException, IllegalOperationException {
		PlatoEntity platoEntity = culturaCulinariaPlatoService.getPlato(culturaCulinariaID, platoID);
		return modelMapper.map(platoEntity, PlatoDTO.class);
	}

	/**
	 * 
	 * @param culturaCulinariasID
	 * @param platos
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{culturaCulinariaID}/platos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlatoDTO> replacePlatos(@PathVariable("culturaCulinariaID") Long culturaCulinariasID,
			@RequestBody List<PlatoDTO> platos) throws EntityNotFoundException {
		List<PlatoEntity> platosList = modelMapper.map(platos, new TypeToken<List<PlatoEntity>>() {
		}.getType());
		List<PlatoEntity> result = culturaCulinariaPlatoService.replacePlatos(culturaCulinariasID, platosList);
		return modelMapper.map(result, new TypeToken<List<PlatoDTO>>() {
		}.getType());
	}

	/**
	 * 
	 * @param culturaID
	 * @param platoID
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{culturaCulinariaID}/platos/{platoID}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePlato(@PathVariable("culturaCulinariaID") Long culturaID, @PathVariable("platoID") Long platoID)
			throws EntityNotFoundException {
		culturaCulinariaPlatoService.removePlato(culturaID, platoID);
	}

}
