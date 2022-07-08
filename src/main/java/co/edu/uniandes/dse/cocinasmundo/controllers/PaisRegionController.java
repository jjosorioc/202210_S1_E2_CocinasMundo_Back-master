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

import co.edu.uniandes.dse.cocinasmundo.dto.RegionDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RegionDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.PaisRegionService;

/**
 * Clase que implementa el recurso "paises/{id}/regiones".
 *
 * @author Tomas Angel
 */
@RestController
@RequestMapping("/paises")

public class PaisRegionController {
	
	@Autowired
	private PaisRegionService paisRegionService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/{paisId}/regiones/{regionId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RegionDetailDTO addRegion(@PathVariable("regionId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException {
		RegionEntity regionEntity = paisRegionService.addRegion(paisId, regionId);
		return modelMapper.map(regionEntity, RegionDetailDTO.class);
	}
	
	@GetMapping(value = "/{paisId}/regiones/{regionId}")
	@ResponseStatus(code = HttpStatus.OK)
	public RegionDetailDTO getRegion(@PathVariable("regionId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException, IllegalOperationException {
		RegionEntity regionEntity = paisRegionService.getRegion(paisId, regionId);
		return modelMapper.map(regionEntity, RegionDetailDTO.class);
	}
	
	@PutMapping(value = "/{paisId}/regiones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RegionDetailDTO> addRegiones(@PathVariable("paisId") Long paisId, @RequestBody List<RegionDTO> regiones)
			throws EntityNotFoundException {
		List<RegionEntity> entities = modelMapper.map(regiones, new TypeToken<List<RegionEntity>>() {
		}.getType());
		List<RegionEntity> regionesList = paisRegionService.replaceRegiones(paisId, entities);
		return modelMapper.map(regionesList, new TypeToken<List<RegionDetailDTO>>() {
		}.getType());
	}
	
	@GetMapping(value = "/{paisId}/regiones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<RegionDetailDTO> getRegiones(@PathVariable("paisId") Long paisId) throws EntityNotFoundException {
		List<RegionEntity> regionEntity = paisRegionService.getRegiones(paisId);
		return modelMapper.map(regionEntity, new TypeToken<List<RegionDetailDTO>>() {
		}.getType());
	}
	
	@DeleteMapping(value = "/{paisId}/regiones/{regionId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeRegion(@PathVariable("regionId") Long regionId, @PathVariable("paisId") Long paisId)
			throws EntityNotFoundException {
		paisRegionService.removeRegion(paisId, regionId);
	}

}
