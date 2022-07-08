package co.edu.uniandes.dse.cocinasmundo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PaisDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PaisDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.CulturaCulinariaPaisService;

@RestController
@RequestMapping("/culturasculinarias")
public class CulturaCulinariaPaisController {

	@Autowired
	private CulturaCulinariaPaisService culturaCulinariaPaisService;

	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Remplaza la instancia de Pais asociada a una instancia de CulturaCulinaria
	 *
	 * @param culturaCulinariaId  Identificador dela cultura culinaria que se esta actualizando.
	 * @param paisId Identificador de la pais que se esta remplazando.
	 * @return JSON {@link PaisDetailDTO} - La pais actualizada
	 */
	@PutMapping(value = "/{culturaCulinariaId}/pais")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO replacePais(@PathVariable("culturaCulinariaId") Long culturaCulinariaId, @RequestBody PaisDTO paisDTO)
			throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaPaisService.replacePais(culturaCulinariaId, paisDTO.getId());
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}
}