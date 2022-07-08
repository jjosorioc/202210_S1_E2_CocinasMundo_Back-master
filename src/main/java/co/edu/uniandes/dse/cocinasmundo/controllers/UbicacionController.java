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
import co.edu.uniandes.dse.cocinasmundo.dto.UbicacionDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.UbicacionDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.UbicacionService;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {
	@Autowired
	private UbicacionService ubicacionService;

	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Métodos.
	 */

	/**
	 * Crea una nueva ubicacion con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param ubicacionDTO {@link CiudadDTO} - La ubicacion que se desea guardar.
	 * @return JSON {@link CiudadDTO} - La ubicacion guardada con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	 public UbicacionDTO create(@RequestBody UbicacionDTO ubicacionDTO) throws IllegalOperationException, EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ubicacionService.createUbicacion(modelMapper.map(ubicacionDTO, UbicacionEntity.class));
	        return modelMapper.map(ubicacionEntity, UbicacionDTO.class);
	     }
	
	/**
	 * Busca y devuelve todas las ubicaciones que existen en la aplicación.
	 * 
	 * @return JSONArray {@link CiudadDetailDTO} - Las ubicaciones encontradas en la
	 *         aplicación. Si no hay ninguna retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<UbicacionDetailDTO> findAll() {
		List<UbicacionEntity> ubicaciones = ubicacionService.getUbicaciones();
		return modelMapper.map(ubicaciones, new TypeToken<List<UbicacionDetailDTO>>() {

		}.getType());
	}
	
	/**
	 * Busca la ubicacion con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador de la ubicacion que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link UbicacionDetailDTO} - La ubicacion buscada.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UbicacionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ubicacionService.getUbicacionId(id);
		return modelMapper.map(ubicacionEntity, UbicacionDetailDTO.class);
	}

	/**
	 * Actualiza la ubicacion con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador de la ubicacion que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param ubicacionDTO
	 * @return ubicacion {@link CiudadDTO} La ubicacion que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public UbicacionDTO update(@PathVariable("id") Long id, @RequestBody UbicacionDTO ubicacionDTO) throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ubicacionService.updateUbicacion(id, modelMapper.map(ubicacionDTO, UbicacionEntity.class));
		return modelMapper.map(ubicacionEntity, UbicacionDTO.class);
	}

	/**
	 * Borra la ubicacion con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador de la ubicacion que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		ubicacionService.deleteUbicacion(id);
	}
}
