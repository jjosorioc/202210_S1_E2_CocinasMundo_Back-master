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
import co.edu.uniandes.dse.cocinasmundo.dto.RegionDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.CulturaCulinariaRegionService;

@RestController
@RequestMapping("/culturasculinarias")
public class CulturaCulinariaRegionController {

	@Autowired
	private CulturaCulinariaRegionService culturaCulinariaRegionService;
	
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Remplaza la instancia de Region asociada a un CulturaCulinaria.
	 *
	 * @param culturaCulinariaId    Identificador del libro que se esta actualizando. Este debe
	 *                  ser una cadena de dígitos.
	 * @param region La region que se será del libro.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - El arreglo de libros guardado en la
	 *         region.
	 */
	@PutMapping(value = "/{culturaCulinariaId}/region")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO replaceRegion(@PathVariable("culturaCulinariaId") Long culturaCulinariaId, @RequestBody RegionDTO regionDTO)
			throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaRegionService.replaceRegion(culturaCulinariaId, regionDTO.getId());
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}
}
