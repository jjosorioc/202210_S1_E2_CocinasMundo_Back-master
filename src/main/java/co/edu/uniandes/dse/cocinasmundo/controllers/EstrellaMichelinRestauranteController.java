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
import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.services.EstrellaMichelinRestauranteService;

/**
 * Clase que implementa el recurso "estrellasmichelin/{id}/restaurante".
 *
 * @author ISIS2603
 */
@RestController
@RequestMapping("/restaurantes")
public class EstrellaMichelinRestauranteController {
	
	@Autowired
	private EstrellaMichelinRestauranteService estrellaMichelinRestauranteService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/{restauranteId}/estrellaMichelin/{estrellaMichelinId}")
	@ResponseStatus(code = HttpStatus.OK)
	public EstrellaMichelinDTO addEstrellaMichelin (@PathVariable("restauranteId") Long restauranteId,@PathVariable("estrellaMichelinId") Long estrellaMichelinId)
			throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinRestauranteService.addEstrellaMichelin(estrellaMichelinId, restauranteId);
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDTO.class);
	}
	
	@GetMapping(value = "/{restauranteId}/estrellaMichelin")
	@ResponseStatus(code = HttpStatus.OK)
	public EstrellaMichelinDetailDTO getEstrellaMichelin(@PathVariable("restauranteId") Long restauranteId) throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinRestauranteService.getEstrellaMichelin(restauranteId);
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDetailDTO.class);
	}
	
	@PutMapping(value = "/{restauranteId}/estrellaMichelin/{estrellaMichelinId}")
	@ResponseStatus(code = HttpStatus.OK)
	public EstrellaMichelinDetailDTO replaceEstrellaMichelin(@PathVariable("restauranteId") Long restauranteId, @PathVariable("estrellaMichelinId") Long estrellaMichelinId)
			throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinRestauranteService.replaceEstrellaMichelin(restauranteId, estrellaMichelinId);
		return modelMapper.map(estrellaMichelinEntity, EstrellaMichelinDetailDTO.class);
	}
	
	@DeleteMapping(value = "/{restauranteId}/estrellaMichelin")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removeEstrellaMichelin(@PathVariable("restauranteId") Long restauranteId) throws EntityNotFoundException {
		estrellaMichelinRestauranteService.removeEstrellaMichelin(restauranteId);
	}
}
