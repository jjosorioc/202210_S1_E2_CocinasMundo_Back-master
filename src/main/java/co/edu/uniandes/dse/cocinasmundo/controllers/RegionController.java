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

import co.edu.uniandes.dse.cocinasmundo.dto.RegionDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RegionDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RegionService;
import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;

@RestController
@RequestMapping("/regiones")
public class RegionController {

	@Autowired
	private RegionService regionService;

	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Métodos.
	 */

	/**
	 * Crea una nueva región con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param regionDTO {@link RegionDTO} - La región que se desea guardar.
	 * @return JSON {@link RegiónDTO} - La región guardada con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RegionDTO create(@RequestBody RegionDTO regionDTO) throws IllegalOperationException, EntityNotFoundException {
	        RegionEntity regionEntity = regionService.createRegion(modelMapper.map(regionDTO, RegionEntity.class));
	        return modelMapper.map(regionEntity, RegionDTO.class);
	     }
	
	/**
	 * Busca y devuelve todas las regiones que existen en la aplicación.
	 * 
	 * @return JSONArray {@link RegionDetailDTO} - Las regiones encontradas en la
	 *         aplicación. Si no hay ninguna retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<RegionDetailDTO> findAll() {
		List<RegionEntity> regiones = regionService.getRegiones();
		return modelMapper.map(regiones, new TypeToken<List<RegionDetailDTO>>() {

		}.getType());
	}
	
	/**
	 * Busca la región con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador de la región que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link RegionDetailDTO} - La región buscada.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public RegionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		RegionEntity regionEntity = regionService.getRegionId(id);
		return modelMapper.map(regionEntity, RegionDetailDTO.class);
	}

	/**
	 * Actualiza la región con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador de la región que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param regionDTO
	 * @return region {@link RegionDTO} La región que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public RegionDTO update(@PathVariable("id") Long id, @RequestBody RegionDTO regionDTO) throws EntityNotFoundException {
		RegionEntity regionEntity = regionService.updateRegion(id, modelMapper.map(regionDTO, RegionEntity.class));
		return modelMapper.map(regionEntity, RegionDTO.class);
	}

	/**
	 * Borra la región con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador de la región que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		regionService.deleteRegion(id);
	}
}

