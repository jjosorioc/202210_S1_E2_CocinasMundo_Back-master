package co.edu.uniandes.dse.cocinasmundo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.cocinasmundo.dto.EstrellaMichelinDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.EstrellaMichelinDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RestauranteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.UbicacionDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.UbicacionDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.CiudadUbicacionService;
import co.edu.uniandes.dse.cocinasmundo.services.EstrellaMichelinRestauranteService;


@RestController
@RequestMapping("/ciudades")
public class CiudadUbicacionController {
	@Autowired
	private CiudadUbicacionService ciudadUbicacionService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/{ciudadID}/ubicaciones/{ubicacionID}")
	@ResponseStatus(code = HttpStatus.OK)
	public UbicacionDTO addUbicacion (@PathVariable("ciudadID") Long ciudadID,@PathVariable("ubicacionID") Long ubicacionID)
			throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ciudadUbicacionService.addUbicacion(ubicacionID, ciudadID);
		return modelMapper.map(ubicacionEntity, UbicacionDTO.class);
	}
	
	@GetMapping(value = "/{ciudadID}/ubicaciones")
	@ResponseStatus(code = HttpStatus.OK)
	public UbicacionDetailDTO getUbicacion(@PathVariable("ciudadeID") Long ciudadeID) throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ciudadUbicacionService.getUbicacion(ciudadeID);
		return modelMapper.map(ubicacionEntity, UbicacionDetailDTO.class);
	}
	
	@PutMapping(value = "/{ciudadID}/ubicaciones/{ubicacionID}")
	@ResponseStatus(code = HttpStatus.OK)
	public UbicacionDetailDTO replaceUbicacion(@PathVariable("ciudadID") Long ciudadID, @PathVariable("ubicacionID") Long ubicacionID)
			throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = ciudadUbicacionService.replaceUbicacion(ciudadID, ubicacionID);
		return modelMapper.map(ubicacionEntity, UbicacionDetailDTO.class);
	}
	
	@DeleteMapping(value = "/{ciudadID}/ubicaciones")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeUbicacion(@PathVariable("ciudadID") Long ciudadID) throws EntityNotFoundException {
		ciudadUbicacionService.removeUbicacion(ciudadID);
	}
}
