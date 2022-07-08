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

import co.edu.uniandes.dse.cocinasmundo.dto.CiudadDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PaisDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.CiudadPaisService;

@RestController
@RequestMapping("/ciudades")
public class CiudadPaisController {

	@Autowired
	private CiudadPaisService ciudadPaisService;

	@Autowired
	private ModelMapper modelMapper;


	@PutMapping(value = "/{ciudadId}/pais")
	@ResponseStatus(code = HttpStatus.OK)
	public CiudadDetailDTO replacePais(@PathVariable("ciudadId") Long ciudadId, @RequestBody PaisDTO paisDTO)
			throws EntityNotFoundException {
		CiudadEntity ciudadEntity = ciudadPaisService.replacePais(ciudadId, paisDTO.getId());
		return modelMapper.map(ciudadEntity, CiudadDetailDTO.class);
	}

}

