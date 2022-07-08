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
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RestauranteService;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Métodos.
	 */

	/**
	 * Crea un nuevo restaurante con la información que se recibe en el cuerpo de la
	 * petición y se regresa un objeto idéntico con un id auto-generado por la base
	 * de datos.
	 * 
	 * @param restauranteDTO {@link CiudadDTO} - El restaurante que se desea guardar.
	 * @return JSON {@link CiudadDTO} - El restaurante guardado con el atributo id
	 *         autogenerado.
	 * @throws IllegalOperationException
	 */
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	 public RestauranteDTO create(@RequestBody RestauranteDTO restauranteDTO) throws IllegalOperationException, EntityNotFoundException {
	        RestauranteEntity restauranteEntity = restauranteService.createRestaurante(modelMapper.map(restauranteDTO, RestauranteEntity.class));
	        return modelMapper.map(restauranteEntity, RestauranteDTO.class);
	     }
	
	/**
	 * Busca y devuelve todos los restaurantes que existen en la aplicación.
	 * 
	 * @return JSONArray {@link CiudadDetailDTO} - Los restaurantes encontrados en la
	 *         aplicación. Si no hay ninguna retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<RestauranteDetailDTO> findAll() {
		List<RestauranteEntity> restaurantes = restauranteService.getRestaurantes();
		return modelMapper.map(restaurantes, new TypeToken<List<RestauranteDetailDTO>>() {

		}.getType());
	}
	
	/**
	 * Busca el restaurante con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador del restaurante que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link CiudadDetailDTO} - El restaurante buscado.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = restauranteService.getRestauranteId(id);
		return modelMapper.map(restauranteEntity, RestauranteDetailDTO.class);
	}
	
	/**
	 * Actualiza el restaurante con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador del restaurante que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param restauranteDTO
	 * @return restaurante {@link CiudadDTO} El restaurante que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteDTO update(@PathVariable("id") Long id, @RequestBody RestauranteDTO restauranteDTO) throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = restauranteService.updateRestaurante(id, modelMapper.map(restauranteDTO, RestauranteEntity.class));
		return modelMapper.map(restauranteEntity, RestauranteDTO.class);
	}
	
	/**
	 * Borra el restaurante con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador del restaurante que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		restauranteService.deleteRestaurante(id);
	}
}
