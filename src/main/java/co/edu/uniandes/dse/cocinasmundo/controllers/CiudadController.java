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

import co.edu.uniandes.dse.cocinasmundo.dto.CiudadDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.CiudadDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.CiudadService;
import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

	@Autowired
	private CiudadService ciudadService;

	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Métodos.
	 */

	/**
	 * Crea una nueva ciudad con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param ciudadDTO {@link CiudadDTO} - La ciudad que se desea guardar.
	 * @return JSON {@link CiudadDTO} - La ciudad guardada con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	 public CiudadDTO create(@RequestBody CiudadDTO ciudadDTO) throws IllegalOperationException, EntityNotFoundException {
	        CiudadEntity ciudadEntity = ciudadService.createCiudad(modelMapper.map(ciudadDTO, CiudadEntity.class));
	        return modelMapper.map(ciudadEntity, CiudadDTO.class);
	     }
	
	/**
	 * Busca y devuelve todas las ciudades que existen en la aplicación.
	 * 
	 * @return JSONArray {@link CiudadDetailDTO} - Las ciudades encontradas en la
	 *         aplicación. Si no hay ninguna retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<CiudadDetailDTO> findAll() {
		List<CiudadEntity> ciudades = ciudadService.getCiudades();
		return modelMapper.map(ciudades, new TypeToken<List<CiudadDetailDTO>>() {

		}.getType());
	}
	
	/**
	 * Busca la ciudad con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador de la ciudad que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link CiudadDetailDTO} - La ciudad buscada.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CiudadDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		CiudadEntity ciudadEntity = ciudadService.getCiudadId(id);
		return modelMapper.map(ciudadEntity, CiudadDetailDTO.class);
	}

	/**
	 * Actualiza la ciudad con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador de la ciudad que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param ciudadDTO
	 * @return ciudad {@link CiudadDTO} La ciudad que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CiudadDTO update(@PathVariable("id") Long id, @RequestBody CiudadDTO ciudadDTO) throws EntityNotFoundException {
		CiudadEntity ciudadEntity = ciudadService.updateCiudad(id, modelMapper.map(ciudadDTO, CiudadEntity.class));
		return modelMapper.map(ciudadEntity, CiudadDTO.class);
	}

	/**
	 * Borra la ciudad con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador de la ciudad que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		ciudadService.deleteCiudad(id);
	}
}

