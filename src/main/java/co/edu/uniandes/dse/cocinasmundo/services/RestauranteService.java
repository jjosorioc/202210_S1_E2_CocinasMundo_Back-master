package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	/**
     * Obtiene la lista de los registros de Restaurante.
     *
     * @return Colección de objetos de RestauranteEntity.
     */
    @Transactional
    public List<RestauranteEntity> getRestaurantes() {
    	log.info("Inicia el proceso de obtener todos los restaurantes");	
    	return restauranteRepository.findAll();
    }
    
    /**
     * Obtiene un restaurante utilizando su id.
     *
     * @param restauranteId = es  el id del restaurante que se busca
     * @return Restaurante solicitado de existir
     * @throws EntityNotFoundException si no encuentra dicho restaurante
     */
    @Transactional
    public RestauranteEntity getRestauranteId(Long restauranteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar el restaurante con id = {0}", restauranteId);
		Optional<RestauranteEntity> restaurante = restauranteRepository.findById(restauranteId);
		if (restaurante.isEmpty()) {
			throw new EntityNotFoundException("El restaurante que ha buscado no existe");
		}
		log.info("Termina proceso de consultar el restaurante con id = {0}", restauranteId);
        return restaurante.get();
    }
    
    /**
	 * Se encarga de crear un Restaurante en la base de datos.
	 *
	 * @param restaurante Objeto de Restaurante
	 * @return Objeto de RestauranteEntity con los datos nuevos y su ID.
	 */
    @Transactional
    public RestauranteEntity createRestaurante(RestauranteEntity restaurante) {
    	log.info("Inicia proceso de creación del restaurante");
        return restauranteRepository.save(restaurante);
    }
    
    /**
	 * Actualiza la información de una instancia de Restaurante.
	 *
	 * @param restauranteId     Identificador de la instancia a actualizar
	 * @param restauranteEntity Instancia de RestauranteEntity con los nuevos datos.
	 * @return Instancia de RestaurantreEntity con los datos actualizados.
	 */
	@Transactional
	public RestauranteEntity updateRestaurante(Long restauranteId, RestauranteEntity restaurante) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el restaurante con id = {0}", restauranteId);
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty()) {
			throw new EntityNotFoundException("El restaurante a actualizar no existe");
		}
		log.info("Termina proceso de actualizar el restaurante con id = {0}", restauranteId);
		restaurante.setId(restauranteId);
		return restauranteRepository.save(restaurante);
	}
	
	/**
	 * Elimina una instancia de Restaurante de la base de datos.
	 *
	 * @param restauranteId Identificador de la instancia a eliminar.
	 * @throws EntityNotFoundException si no existe el Restaurante.
	 */
	@Transactional
	public void deleteRestaurante(Long restauranteId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar el restaurante con id = {0}", restauranteId);
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty()) {
			throw new EntityNotFoundException("El restaurante a eliminar no existe");
		}
		restauranteRepository.deleteById(restauranteId);
		log.info("Termina proceso de borrar el restaurante con id = {0}", restauranteId);
	}
}
