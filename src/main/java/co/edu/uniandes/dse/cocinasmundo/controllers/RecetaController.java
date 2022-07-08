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

import co.edu.uniandes.dse.cocinasmundo.dto.RecetaDTO;
import co.edu.uniandes.dse.cocinasmundo.dto.RecetaDetailDTO;
import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RecetaService;

@RestController
@RequestMapping("/recetas")
/**
 * Clase que implementa los objetos Receta
 * @author alejandro tovar
 *
 */
public class RecetaController {

	@Autowired
	private RecetaService recetaService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Crea una receta a partir de un JSON y el método POST
	 * @param recetaDTO
	 * @return El DTO del objeto creado
	 * @throws EntityNotFoundException
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RecetaDTO create(@RequestBody RecetaDTO recetaDTO) {
		
		RecetaEntity recetaEntity = recetaService.createReceta(modelMapper.map(recetaDTO, RecetaEntity.class));
		return modelMapper.map(recetaEntity,RecetaDTO.class);
	}
	
	/**
	 * Recupera todos los objetos tipo Receta en  la base de  datos
	 * @return La lista con todos los objetos
	 */
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<RecetaDetailDTO> findAll() {
		List<RecetaEntity> recetas = recetaService.getRecetas();
		return modelMapper.map(recetas, new TypeToken<List<RecetaDetailDTO>>(){}.getType());
	}
	
	/**
	 * Recupera una receta por su id
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RecetaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        RecetaEntity recetaEntity = recetaService.getRecetaId(id);
        return modelMapper.map(recetaEntity, RecetaDetailDTO.class);
    }
	
	/**
	 * Actualiza la Receta con el id especificado
	 * @param id
	 * @param recetaDTO en formato JSON
	 * @return
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public RecetaDTO update(@PathVariable("id") Long id, @RequestBody RecetaDTO recetaDTO) throws EntityNotFoundException, IllegalOperationException {
        RecetaEntity recetaEntity = recetaService.updateReceta(id, modelMapper.map(recetaDTO, RecetaEntity.class));
        return modelMapper.map(recetaEntity, RecetaDTO.class);
    }
	
	/**
	 * Elimina la receta con el id provisto
	 * @param id
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
            recetaService.deleteReceta(id);
    }
}