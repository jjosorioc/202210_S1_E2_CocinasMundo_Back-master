package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.IngredienteRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa los servicios de la lógica de la entidad IngredienteEntity. 
 *
 * @author Alejandro Tovar
 */
@Slf4j
@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	/**
     * Obtiene la lista de los registros de Ingrediente.
     *
     * @return Colección de objetos de IngredienteEntity.
     */
    @Transactional
    public List<IngredienteEntity> getIngredientes() {
    	log.info("Inicia el proceso de obtener todos los ingredientes");	
    	return ingredienteRepository.findAll();
    }
    
    /**
     * Obtiene un ingrediente utilizando su id.
     *
     * @param ingredienteId = es  el id del ingrediente buscado
     * @return Ingrediente solicitado si existe.
     * @throws EntityNotFoundException si no lo encuentra
     */
    @Transactional
    public IngredienteEntity getIngredienteId(Long ingredienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar el ingrediente con id = {0}", ingredienteId);
		Optional<IngredienteEntity> ingrediente = ingredienteRepository.findById(ingredienteId);
		if (ingrediente.isEmpty()) {
			throw new EntityNotFoundException("El ingrediente buscado no existe");
		}
		log.info("Termina proceso de consultar el ingrediente con id = {0}", ingredienteId);
        return ingrediente.get();
    }
    
    /**
	 * Se encarga de crear un Ingrediente en la base de datos.
	 *
	 * @param ingrediente Objeto de Ingrediente
	 * @return Objeto de IngredienteEntity con los datos nuevos y su ID.
	 */
    @Transactional
    public IngredienteEntity createIngrediente(IngredienteEntity ingrediente) {
    	log.info("Inicia proceso de creación del ingrediente");
        return ingredienteRepository.save(ingrediente);
    }
    
    /**
	 * Actualiza la información de una instancia de Ingrediente.
	 *
	 * @param ingredienteId     Identificador de la instancia a actualizar
	 * @param ingredienteEntity Instancia de IngredienteEntity con los nuevos datos.
	 * @return Instancia de IngredienteEntity con los datos actualizados.
	 */
	@Transactional
	public IngredienteEntity updateIngrediente(Long ingredienteId, IngredienteEntity ingrediente) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el autor con id = {0}", ingredienteId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty()) {
			throw new EntityNotFoundException("El ingrediente a actualizar no existe");
		}
		log.info("Termina proceso de actualizar el ingrediente con id = {0}", ingredienteId);
		ingrediente.setId(ingredienteId);
		return ingredienteRepository.save(ingrediente);
	}
	
	/**
	 * Elimina una instancia de Ingrediente de la base de datos.
	 *
	 * @param ingredienteId Identificador de la instancia a eliminar.
	 * @throws EntityNotFoundException si no existe el Ingrediente.
	 */
	@Transactional
	public void deleteIngrediente(Long ingredienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar el ingrediente con id = {0}", ingredienteId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty()) {
			throw new EntityNotFoundException("El ingrediente a eliminar no existe");
		}
		ingredienteRepository.deleteById(ingredienteId);
		log.info("Termina proceso de borrar el ingrediente con id = {0}", ingredienteId);
	}
}
