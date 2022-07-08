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

import co.edu.uniandes.dse.cocinasmundo.dto.IngredienteDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.IngredienteDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.IngredienteService;

@RestController
@RequestMapping("/ingredientes")
/**
 * Clase que implementa los objetos Ingrediente
 * @author alejandro tovar
 *
 */
public class IngredienteController {


	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
     * Obtiene un ingrediente utilizando su id.
     *
     * @param recetaId = es  el id del ingrediente buscado.
     * @return Ingrediente solicitado si existe.
     * @throws EntityNotFoundException si no la encuentra
     */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public IngredienteDTO create(@RequestBody IngredienteDTO ingredienteDTO) throws EntityNotFoundException {

		IngredienteEntity ingredienteEntity = ingredienteService.createIngrediente(modelMapper.map(ingredienteDTO, IngredienteEntity.class));
		return modelMapper.map(ingredienteEntity,IngredienteDTO.class);
	}
	
	/**
	 * Recupera todos los objetos tipo Ingrediente en  la base de  datos
	 * @return La lista con todos los objetos
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<IngredienteDetailDTO> findAll() {
		List<IngredienteEntity> ingredientes = ingredienteService.getIngredientes();
		return modelMapper.map(ingredientes, new TypeToken<List<IngredienteDetailDTO>>(){}.getType());
	}
	
	/**
	 * Recupera un ingrediente por su id
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public IngredienteDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        IngredienteEntity ingredienteEntity = ingredienteService.getIngredienteId(id);
        return modelMapper.map(ingredienteEntity, IngredienteDetailDTO.class);
    }
	
	/**
	 * Actualiza el Ingrediente con el id especificado
	 * @param id
	 * @param ingredienteDTO en formato JSON
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public IngredienteDTO update(@PathVariable("id") Long id, @RequestBody IngredienteDTO ingredienteDTO) throws EntityNotFoundException, IllegalOperationException {
        IngredienteEntity ingredienteEntity = ingredienteService.updateIngrediente(id, modelMapper.map(ingredienteDTO, IngredienteEntity.class));
        return modelMapper.map(ingredienteEntity, IngredienteDTO.class);
    }
	
	/**
	 * Elimina el ingrediente con el id provisto
	 * @param id
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            ingredienteService.deleteIngrediente(id);
    }
}