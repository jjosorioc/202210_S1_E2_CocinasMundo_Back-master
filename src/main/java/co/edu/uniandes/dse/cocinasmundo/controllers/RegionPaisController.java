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

import co.edu.uniandes.dse.cocinasmundo.dto.PaisDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.PaisDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RegionPaisService;

/**
 * Clase que implementa el recurso "region/{id}/paises".
 *
 * @author Tomas Angel
 */
@RestController
@RequestMapping("/regiones")

public class RegionPaisController {
	
	@Autowired
	private RegionPaisService regionPaisService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/{regionId}/paises/{paisId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaisDetailDTO getPais(@PathVariable("regionId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException, IllegalOperationException {
		PaisEntity paisEntity = regionPaisService.getPais(regionId, paisId);
		return modelMapper.map(paisEntity, PaisDetailDTO.class);
	}
	
	@GetMapping(value = "/{regionId}/paises")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PaisDetailDTO> getPaises(@PathVariable("regionId") Long regionId) throws EntityNotFoundException {
		List<PaisEntity> paisEntity = regionPaisService.getPaises(regionId);
		return modelMapper.map(paisEntity, new TypeToken<List<PaisDetailDTO>>() {
		}.getType());
	}
	
	@PostMapping(value = "/{regionId}/paises/{paisId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaisDetailDTO addPais(@PathVariable("regionId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException {
		PaisEntity paisEntity = regionPaisService.addPais(regionId, paisId);
		return modelMapper.map(paisEntity, PaisDetailDTO.class);
	}
	
	@PutMapping(value = "/{regionId}/paises")
	@ResponseStatus(code = HttpStatus.OK)
	public List<PaisDetailDTO> replacePaises(@PathVariable("regionId") Long regionId, @RequestBody List<PaisDTO> paises)
			throws EntityNotFoundException {
		List<PaisEntity> entities = modelMapper.map(paises, new TypeToken<List<PaisEntity>>() {
		}.getType());
		List<PaisEntity> paisesList = regionPaisService.addPaises(regionId, entities);
		return modelMapper.map(paisesList, new TypeToken<List<PaisDetailDTO>>() {
		}.getType());
	}
	
	@DeleteMapping(value = "/{regoinId}/paises/{paisId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removePais(@PathVariable("regoinId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException {
		regionPaisService.removePais(regionId, paisId);
	}
	
}



