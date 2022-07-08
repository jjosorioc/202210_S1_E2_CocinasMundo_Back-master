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
import co.edu.uniandes.dse.cocinasmundo.services.CulturaCulinariaService;

@RestController
@RequestMapping("/culturasculinarias")
public class CulturaCulinariaController {

	@Autowired
	private CulturaCulinariaService culturaCulinariaService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	 public CulturaCulinariaDTO create(@RequestBody CulturaCulinariaDTO culturaCulinariaDTO) throws IllegalOperationException, EntityNotFoundException {
	        CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaService.createCulturaCulinaria(modelMapper.map(culturaCulinariaDTO, CulturaCulinariaEntity.class));
	        return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDTO.class);
	     }
	
	/**
	 * Busca y devuelve todas las culturas culinarias que existen en la aplicación.
	 * 
	 * @return JSONArray {@link CulturaCulinariaDetailDTO} - Las culturas culinarias encontradas en la
	 *         aplicación. Si no hay ninguna retorna una lista vacía.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> findAll() {
		List<CulturaCulinariaEntity> culturasCulinarias = culturaCulinariaService.getCulturasCulinarias();
		return modelMapper.map(culturasCulinarias, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());
	}
	
	/**
	 * Busca la cultura culinaria con el id asociado recibido en la URL y lo devuelve.
	 * 
	 * @param id Identificador de la cultura culinaria que se está buscando. Este debe ser una
	 *           cadena de dígitos.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - La cultura culinaria buscada.
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaService.getCulturaCulinariaId(id);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}

	/**
	 * Actualiza la cultura culinaria con el id recibido en la URL con la información que se
	 * recibe en el cuerpo de la petición.
	 * 
	 * @param id      Identificador de la cultura culinaria que se desea actualizar. Este debe ser
	 *                una cadena de dígitos.
	 * @param cultura culinariaDTO
	 * @return cultura culinaria {@link CulturaCiudadDTO} La cultura culinaria que se desea guardar.
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDTO update(@PathVariable("id") Long id, @RequestBody CulturaCulinariaDTO culturaCulinariaDTO) throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaService.updateCulturaCulinaria(id, modelMapper.map(culturaCulinariaDTO, CulturaCulinariaEntity.class));
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDTO.class);
	}

	/**
	 * Borra la cultura culinaria con el id asociado recibido en la URL.
	 * 
	 * @param id Identificador de la cultura culinaria que se desea borrar. Este debe ser una
	 *           cadena dígitos.
	 * @throws EntityNotFoundException
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		culturaCulinariaService.deleteCulturaCulinaria(id);
	}
}
