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
import co.edu.uniandes.dse.cocinasmundo.services.RegionCulturaCulinariaService;


@RestController
@RequestMapping("/regiones")
public class RegionCulturaCulinariaController {

	@Autowired
	private RegionCulturaCulinariaService regionCulturaCulinariaService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Guarda una cultura culinaria dentro de una region con la informacion que recibe el la
	 * URL. Se devuelve la cultura culinaria que se guarda en la region.
	 *
	 * @param regionId Identificador de la region que se esta actualizando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param culturaCulinariaId      Identificador de la cultura culinaria que se desea guardar. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link CulturaCulinariaDTO} - La cultura culinaria guardada en la region.
	 */
	@PostMapping(value = "/{regionId}/culturasculinarias/{culturaCulinariaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDTO addCulturaCulinaria(@PathVariable("regionId") Long regionId, @PathVariable("culturaCulinariaId") Long culturaCulinarialId)
			throws EntityNotFoundException, IllegalOperationException {
		CulturaCulinariaEntity culturaCulinariaEntity = regionCulturaCulinariaService.addCulturaCulinaria(culturaCulinarialId, regionId);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDTO.class);
	}

	/**
	 * Busca y devuelve todos las culturas culinarias que existen en la region.
	 *
	 * @param regionId Identificador de la region que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @return JSONArray {@link CulturaCulinariaDetailDTO} - Las culturas culinarias encontradas en la
	 *         region. Si no hay ninguno retorna una lista vacía.
	 */
	@GetMapping(value = "/{regionId}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> getCulturasCulinarias(@PathVariable("regionId") Long regionId) throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturaCulinariaList = regionCulturaCulinariaService.getCulturasCulinarias(regionId);
		return modelMapper.map(culturaCulinariaList, new TypeToken<List<CulturaCulinariaDetailDTO>>() {
		}.getType());
	}

	/**
	 * Busca la cultura culinaria con el id asociado dentro de la region con id asociado.
	 *
	 * @param regionId Identificador de la region que se esta buscando. Este
	 *                    debe ser una cadena de dígitos.
	 * @param culturaCulinariaId      Identificador dla cultura culinaria que se esta buscando. Este debe
	 *                    ser una cadena de dígitos.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - El libro buscado
	 */
	@GetMapping(value = "/{regionId}/culturasculinarias/{culturaCulinariaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public CulturaCulinariaDetailDTO getCulturaCulinaria(@PathVariable("regionId") Long regionId, @PathVariable("culturaCulinariaId") Long culturaCulinariaId) throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = regionCulturaCulinariaService.getCulturaCulinaria(regionId, culturaCulinariaId);
		return modelMapper.map(culturaCulinariaEntity, CulturaCulinariaDetailDTO.class);
	}

	/**
	 * Remplaza las instancias de CulturaCulinaria asociadas a una instancia de Region
	 *
	 * @param regionId Identificador de la region que se esta remplazando.
	 *                    Este debe ser una cadena de dígitos.
	 * @param culturaCulinarias       JSONArray {@link CulturaCulinariaDTO} El arreglo de libros nuevo para
	 *                    la region.
	 * @return JSON {@link CulturaCulinariaDetailDTO} - El arreglo de libros guardado en la
	 *         region.
	 */
	@PutMapping(value = "/{regionId}/culturasculinarias")
	@ResponseStatus(code = HttpStatus.OK)
	public List<CulturaCulinariaDetailDTO> replaceCulturasCulinarias(@PathVariable("regionId") Long regionsId, @RequestBody List<CulturaCulinariaDetailDTO> culturasCulinarias) throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturasCulinariasList = modelMapper.map(culturasCulinarias, new TypeToken<List<CulturaCulinariaEntity>>() {}.getType());
		List<CulturaCulinariaEntity> result = regionCulturaCulinariaService.replaceCulturasCulinarias(regionsId, culturasCulinariasList);
		return modelMapper.map(result, new TypeToken<List<CulturaCulinariaDetailDTO>>() {}.getType());
	}
}