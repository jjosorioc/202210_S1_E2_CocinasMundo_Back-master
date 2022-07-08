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

import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.CulturaCulinariaDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.PaisCulturaCulinariaService;

@RestController
@RequestMapping("/paises")
public class PaisCulturaCulinariaController {

	@Autowired
	private PaisCulturaCulinariaService paisCulturaCulinariaService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Guarda una cultura culinaria dentro de una pais con la informacion que recibe el la
	 * URL. Se devuelve la cultura culinaria que se guarda en la pais.
	 *
	 * @param paisId Identificador de la pais que se esta actualizando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param culturaCulinariaId      Identificador de la cultura culinaria que se desea guardar. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link CulturaCulinariaDTO} - La cultura culinaria guardada en la pais.
	 */
	@PostMapping(value = "/{paisId}/culturasculinarias/{culturaCulinariaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDTO addCulturaCulinaria(@PathVariable("paisId") Long paisId, @PathVariable("culturaCulinariaId") Long culturaCulinarialId)
			throws EntityNotFoundException, IllegalOperationException {
		CulturaCulinariaEntity culturaCulinariaEntity = paisCulturaCulinariaService.addCulturaCulinaria(culturaCulinarialId, paisId);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDTO.class);
	}

	/**
	 * Busca y devuelve todos las culturas culinarias que existen en la pais.
	 *
	 * @param paisId Identificador de la pais que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @return JSONArray {@link CulturaCulinariaDetailDTO} - Las culturas culinarias encontradas en la
	 *         pais. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{paisId}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> getCulturasCulinarias(@PathVariable("paisId") Long paisId) throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturaCulinariaList = paisCulturaCulinariaService.getCulturasCulinarias(paisId);
		return modelMapper.map(culturaCulinariaList, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());
	}

	/**
	 * Busca la cultura culinaria con el id asociado dentro de la pais con id asociado.
	 *
	 * @param paisId Identificador de la pais que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @param culturaCulinariaId      Identificador dla cultura culinaria que se esta buscando. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - El libro buscado
	 */
	@GetMapping(value = "/{paisId}/culturasculinarias/{culturaCulinariaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO getCulturaCulinaria(@PathVariable("paisId") Long paisId, @PathVariable("culturaCulinariaId") Long culturaCulinariaId) throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = paisCulturaCulinariaService.getCulturaCulinaria(paisId, culturaCulinariaId);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}

	/**
	 * Remplaza las instancias de CulturaCulinaria asociadas a una instancia de Pais
	 *
	 * @param paisId Identificador de la pais que se esta remplazando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param culturaCulinarias       JSONArray {@link CulturaCulinariaDTO} El arreglo de libros nuevo para
	 *                    la pais.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - El arreglo de libros guardado en la
	 *         pais.
	 */
	@PutMapping(value = "/{paisId}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> replaceCulturasCulinarias(@PathVariable("paisId") Long paissId,
			@RequestBody List<CulturaCulinariaDetailDTO> culturaCulinarias) throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturaCulinariasList = modelMapper.map(culturaCulinarias, new TypeToken<List<CulturaCulinariaEntity>>() {
		}.getType());
		List<CulturaCulinariaEntity> result = paisCulturaCulinariaService.replaceCulturasCulinarias(paissId, culturaCulinariasList);
		return modelMapper.map(result, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());
	}
}