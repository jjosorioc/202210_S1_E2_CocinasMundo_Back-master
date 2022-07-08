package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.RecetaRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa los servicios de la lógica de la entidad RecetaEntity. 
 *
 * @author Alejandro Tovar
 */
@Slf4j
@Service
public class RecetaService {
	@Autowired
	private RecetaRepository recetaRepository;
	
	/**
     * Obtiene la lista de los registros de Receta.
     *
     * @return Colección de objetos de RecetaEntity.
     */
    @Transactional
    public List<RecetaEntity> getRecetas() {
        return recetaRepository.findAll();
    }
    
    /**
     * Obtiene una receta utilizando su id.
     *
     * @param recetaId = es  el id de la receta buscada.
     * @return Receta solicitado si existe.
     * @throws EntityNotFoundException si no la encuentra
     */
    @Transactional
    public RecetaEntity getRecetaId(Long recetaId) throws EntityNotFoundException {
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty()) {
			throw new EntityNotFoundException("La receta buscada no existe");
		}
        return recetaEntity.get();
    }
    
    /**
	 * Se encarga de crear una Receta en la base de datos.
	 *
	 * @param receta Objeto de Receta.
	 * @return Objeto de RecetaEntity con los datos nuevos y su ID.
	 */
    @Transactional
    public RecetaEntity createReceta(RecetaEntity receta) {
    	log.info("Inicia proceso de creación de la receta");
        return recetaRepository.save(receta);
    }
    
    /**
	 * Actualiza la información de una instancia de Receta.
	 *
	 * @param recetaId     Identificador de la instancia a actualizar
	 * @param recetaEntity Instancia de RecetaEntity con los nuevos datos.
	 * @return Instancia de RecetaEntity con los datos actualizados.
	 */
	@Transactional
	public RecetaEntity updateReceta(Long recetaId, RecetaEntity receta) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty()) {
			throw new EntityNotFoundException("La receta a actualizar no existe");
		}
		log.info("Termina proceso de actualizar la receta con id = {0}", recetaId);
		receta.setId(recetaId);
		return recetaRepository.save(receta);
	}
	
	/**
	 * Elimina una instancia de Receta de la base de datos.
	 *
	 * @param recetaId Identificador de la instancia a eliminar.
	 * @throws EntityNotFoundException si no existe la receta.
	 */
	@Transactional
	public void deleteReceta(Long recetaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty()) {
			throw new EntityNotFoundException("El ingrediente a eliminar no existe");
		}
		recetaRepository.deleteById(recetaId);
		log.info("Termina proceso de borrar la receta con id = {0}", recetaId);
	}
}
