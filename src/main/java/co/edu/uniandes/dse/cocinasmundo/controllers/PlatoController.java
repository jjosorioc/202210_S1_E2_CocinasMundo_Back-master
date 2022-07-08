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
import co.edu.uniandes.dse.cocinasmundo.services.PlatoService;

@RestController
@RequestMapping("/platos")
public class PlatoController {

	@Autowired
	private PlatoService platoService;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * Métodos.
	 */

	/**
	 * Crea un nuevo plato con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param platoDTO {@link PlatoDTO} - El plato que se desea guardar.
	 * @return JSON {@link PlatoDTO} - El plato guardado con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlatoDTO create(@RequestBody PlatoDTO platoDTO) throws IllegalOperationException {
		PlatoEntity platoEntity = platoService.createPlato(modelMapper.map(platoDTO, PlatoEntity.class));
		return modelMapper.map(platoEntity, PlatoDTO.class);
	}

	/**
	 * Busca y devuelve todos los Platos que existen en la aplicación.
	 * 
	 * @return JSONArray {@link PlatoDetailDTO} - Los platos encontrados en la
	 *         aplicación. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<PlatoDetailDTO> findall() {
		List<PlatoEntity> platos = platoService.getPlatos();
		return modelMapper.map(platos, new TypeToken<List<PlatoDetailDTO>>() {

		}.getType());
	}

	/**
	 * Busca el Plato con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador del Plato que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link PlatoDetailDTO} - El plato buscado
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		PlatoEntity platoEntity = platoService.getPlatoByID(id);
		return modelMapper.map(platoEntity, PlatoDetailDTO.class);
	}

	/**
	 * Actualiza el plato con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id       Identificador del plato que se desea actualizar. Este debe
	 *                 ser una cadena de dígitos.
	 * @param platoDTO
	 * @return plato {@link PlatoDTO} El plato que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDTO update(@PathVariable("id") Long id, @RequestBody PlatoDTO platoDTO) throws EntityNotFoundException {
		PlatoEntity platoEntity = platoService.updatePlato(id, modelMapper.map(platoDTO, PlatoEntity.class));
		return modelMapper.map(platoEntity, PlatoDTO.class);
	}

	/**
	 * Borra el plato con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador del plato que se desea borrar. Este debe ser una
	 *           cadena de dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		platoService.deletePlato(id);
	}
}
