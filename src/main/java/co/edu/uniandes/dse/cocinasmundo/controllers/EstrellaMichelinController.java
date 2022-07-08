package co.edu.uniandes.dse.cocinasmundo.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.cocinasmundo.dto.EstrellaMichelinDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.EstrellaMichelinService;
import co.edu.uniandes.dse.cocinasmundo.dto.EstrellaMichelinDetailDTO;

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

@RestController
@RequestMapping("/estrellamichelin")
public class EstrellaMichelinController {
	@Autowired
	private EstrellaMichelinService estrellaMichelinService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Metodos
	 */
	
	/**
	 * Crea una nueva estrella michelin con la informacion que se recibe en el cuerpo de la 
	 * peticion y se regresa un objeto identico con un id auto-generado por la base
	 * de datos.
	 * 
	 **/
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public EstrellaMichelinDTO create(@RequestBody EstrellaMichelinDTO estrellaMichelinDTO) throws IllegalOperationException, EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinService.createEstrellaMichelin(modelMapper.map(estrellaMichelinDTO, EstrellaMichelinEntity.class));
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDTO.class);
	}
	
	/**
	 * Busca y devuelve todas las estrellas michelin que existen en la aplicacion.
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<EstrellaMichelinDetailDTO> findAll() {
		List<EstrellaMichelinEntity> estrellasMichelin = estrellaMichelinService.getEstrellaMichelin();
		return modelMapper.map(estrellasMichelin,  new TypeToken<List<EstrellaMichelinDetailDTO>>() {
			
		}.getType());
	}
	
	/**
	 * Busca la estrella michelin con el id asociado recibido en la URL y lo devuelve.
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public EstrellaMichelinDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinService.getEstrellaMichelinId(id);
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDetailDTO.class);
	}
	
	/**
	 * Actualiza la ubicacion con el id recibido en la URL con la informacion que se
	 * recibe en el cuerpo de la peticion.
	 * 
	 * 
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public EstrellaMichelinDTO update(@PathVariable("id") Long id, @RequestBody EstrellaMichelinDTO estrellaMichelinDTO) throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinService.updateEstrellaMichelin(id, modelMapper.map(estrellaMichelinDTO, EstrellaMichelinEntity.class));
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDTO.class);
	}
	
	/**
	 * Borra la estrella michelin con el id asociado recibido en el URL.
	 * 
	 * @param id Identificador de la ubicacion que se desea borrar. Este debe ser una
	 * 		  cadena digitos.
	 * @throws EntityNotFoundException
	 */
	 @DeleteMapping(value = "/{id}")
	 @ResponseStatus(code = HttpStatus.NO_CONTENT)
	 public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
		 estrellaMichelinService.deleteEstrellaMichelin(id);
	 }
	 
}
