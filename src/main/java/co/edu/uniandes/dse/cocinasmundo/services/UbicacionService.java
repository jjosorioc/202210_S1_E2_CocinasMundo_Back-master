package co.edu.uniandes.dse.cocinasmundo.services;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.UbicacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionService {
	@Autowired
	private UbicacionRepository ubicacionRepository;
	
	/**
     * Obtiene la lista de los registros de Ubicacion.
     *
     * @return Colección de objetos de UbicacioneEntity.
     */
    @Transactional
    public List<UbicacionEntity> getUbicaciones() {
    	log.info("Inicia el proceso de obtener todos las ubicaciones");	
    	return ubicacionRepository.findAll();
    }
    
    /**
     * Obtiene una Ubicacion utilizando su id.
     *
     * @param ubicacionId = es  el id de la ubicacion que se busca
     * @return Ubicacion solicitado de existir
     * @throws EntityNotFoundException si no encuentra dicha ubicacion
     */
    @Transactional
    public UbicacionEntity getUbicacionId(Long ubicacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la ubicacion con id = {0}", ubicacionId);
		Optional<UbicacionEntity> ubicacion = ubicacionRepository.findById(ubicacionId);
		if (ubicacion.isEmpty()) {
			throw new EntityNotFoundException("La ubicacion que ha buscado no existe");
		}
		log.info("Termina proceso de consultar la ubicacion con id = {0}", ubicacionId);
        return ubicacion.get();
    }
    
    /**
	 * Se encarga de crear una Ubicacion en la base de datos.
	 *
	 * @param ubicacion Objeto de Ubicacion
	 * @return Objeto de UbicacionEntity con los datos nuevos y su ID.
	 */
    @Transactional
    public UbicacionEntity createUbicacion(UbicacionEntity ubicacion) {
    	log.info("Inicia proceso de creación de la Ubicacion");
        return ubicacionRepository.save(ubicacion);
    }
    
    /**
	 * Actualiza la información de una instancia de Ubicacion.
	 *
	 * @param ubicacionId     Identificador de la instancia a actualizar
	 * @param ubicacionEntity Instancia de UbicacionEntity con los nuevos datos.
	 * @return Instancia de UbicacionEntity con los datos actualizados.
	 */
	@Transactional
	public UbicacionEntity updateUbicacion(Long ubicacionId, UbicacionEntity ubicacion) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la ubicacion con id = {0}", ubicacionId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty()) {
			throw new EntityNotFoundException("La ubicacion a actualizar no existe");
		}
		log.info("Termina proceso de actualizar la ubicacion con id = {0}", ubicacionId);
		ubicacion.setId(ubicacionId);
		return ubicacionRepository.save(ubicacion);
	}
	
	/**
	 * Elimina una instancia de Ubicacion de la base de datos.
	 *
	 * @param ubicacionId Identificador de la instancia a eliminar.
	 * @throws EntityNotFoundException si no existe la Ubicacion.
	 */
	@Transactional
	public void deleteUbicacion(Long ubicacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar la ubicacion con id = {0}", ubicacionId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty()) {
			throw new EntityNotFoundException("La ubicacion a eliminar no existe");
		}
		ubicacionRepository.deleteById(ubicacionId);
		log.info("Termina proceso de borrar la ubicacion con id = {0}", ubicacionId);
	}
}
