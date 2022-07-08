package co.edu.uniandes.dse.cocinasmundo.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.PaisCiudadService;



@RestController
@RequestMapping("/paises")
public class PaisCiudadController {

	@Autowired
	private PaisCiudadService paisCiudadService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/{paisId}/ciudades/{ciudadId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CiudadDTO addCiudad(@PathVariable("paisId") Long paisId, @PathVariable("ciudadId") Long ciudadId)
			throws EntityNotFoundException {
		CiudadEntity ciudadEntity = paisCiudadService.addCiudad(ciudadId, paisId);
		return modelMapper.map(ciudadEntity, CiudadDTO.class);
	}

	@GetMapping(value = "/{paisId}/ciudades")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CiudadDetailDTO> getCiudades(@PathVariable("paisId") Long paisId) throws EntityNotFoundException {
		List<CiudadEntity> ciudadList = paisCiudadService.getCiudades(paisId);
		return modelMapper.map(ciudadList, new TypeToken<List<CiudadDetailDTO>>() {
		}.getType());
	}

	@GetMapping(value = "/{paisId}/ciudades/{ciudadId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CiudadDetailDTO getCiudad(@PathVariable("paisId") Long paisId, @PathVariable("ciudadId") Long ciudadId)
			throws EntityNotFoundException, IllegalOperationException {
		CiudadEntity ciudadEntity = paisCiudadService.getCiudad(paisId, ciudadId);
		return modelMapper.map(ciudadEntity, CiudadDetailDTO.class);
	}

	@PutMapping(value = "/{paisId}/ciudades")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CiudadDetailDTO> replaceCiudades(@PathVariable("paisId") Long paisId,
			@RequestBody List<CiudadDetailDTO> ciudades) throws EntityNotFoundException {
		List<CiudadEntity> ciudadList = modelMapper.map(ciudades, new TypeToken<List<CiudadEntity>>() {
		}.getType());
		List<CiudadEntity> result = paisCiudadService.replaceCiudades(paisId, ciudadList);
		return modelMapper.map(result, new TypeToken<List<CiudadDetailDTO>>() {
		}.getType());
	}
}
