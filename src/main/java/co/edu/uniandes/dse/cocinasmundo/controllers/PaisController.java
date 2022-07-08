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

import co.edu.uniandes.dse.cocinasmundo.dto.PaisDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PaisDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.PaisService;

@RestController
@RequestMapping("/paises")
public class PaisController {

	@Autowired
	private PaisService paisService;

	@Autowired
	private ModelMapper modelMapper;

	/*
	 * Métodos.
	 */

	/**
	 * Crea un nuevo pais con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param paisDTO {@link PaisDTO} - El pais que se desea guardar.
	 * @return JSON {@link PaisDTO} - El pais guardado con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PaisDTO create(@RequestBody PaisDTO paisDTO) throws IllegalOperationException {
		PaisEntity paisEntity = paisService.createPais(modelMapper.map(paisDTO, PaisEntity.class));
		return modelMapper.map(paisEntity, PaisDTO.class);
	}

	/**
	 * Busca y devuelve todos los Paises que existen en la aplicación.
	 * 
	 * @return JSONArray {@link PaisDetailDTO} - Los paises encontrados en la
	 *         aplicación. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<PaisDetailDTO> findall() {
		List<PaisEntity> paises = paisService.getPaises();
		return modelMapper.map(paises, new TypeToken<List<PaisDetailDTO>>() {

		}.getType());
	}

	/**
	 * Busca el Pais con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador del Pais que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link PaisDetailDTO} - El pais buscado.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaisDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		PaisEntity paisEntity = paisService.getPaisByID(id);
		return modelMapper.map(paisEntity, PaisDetailDTO.class);
	}

	/**
	 * Actualiza el Pais con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador del Pais que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param paisDTO
	 * @return pais {@link PaisDTO} El pais que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaisDTO update(@PathVariable("id") Long id, @RequestBody PaisDTO paisDTO) throws EntityNotFoundException {
		PaisEntity paisEntity = paisService.updatePais(id, modelMapper.map(paisDTO, PaisEntity.class));
		return modelMapper.map(paisEntity, PaisDTO.class);
	}

	/**
	 * Borra el Pais con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador del Pais que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		paisService.deletePais(id);
	}
}
