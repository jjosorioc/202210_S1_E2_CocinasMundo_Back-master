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

import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PlatoDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.PlatoCulturaCulinariaService;

@RestController
@RequestMapping("/platos")
public class PlatoCulturaCulinariaController {
	@Autowired
	private PlatoCulturaCulinariaService platoCulturaCulinariaService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 
	 * @param platoId
	 * @param culturaCulinariaDTO
	 * @return
	 * @throws EntityNotFoundException
	 */
	@PutMapping(value = "/{platoID}/culturaculinaria")
	@ResponseStatus(code = HttpStatus.OK)
	public PlatoDetailDTO replaceEditorial(@PathVariable("platoID") Long platoId,
			@RequestBody CulturaCulinariaDTO culturaCulinariaDTO) throws EntityNotFoundException {
		PlatoEntity platoEntity = platoCulturaCulinariaService.replaceCulturaCulinaria(platoId,
				culturaCulinariaDTO.getId());
		return modelMapper.map(platoEntity, PlatoDetailDTO.class);
	}
}
