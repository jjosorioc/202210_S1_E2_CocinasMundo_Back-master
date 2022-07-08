package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.PlatoRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlatoRestauranteService {

	@Autowired
	private PlatoRepository platoRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	/**
	 * Asocia un Restaurante existente a un Plato
	 * 
	 * @param platoID       Identificador de la instancia de Plato
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @return Instancia de RestauranteEntity que fue asociada a Plato
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException 
	 */
	@Transactional
	public RestauranteEntity addRestaurante(Long platoID, Long restauranteID) throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");
		
		if (platoEntity.get().getRestaurantes().contains(restauranteEntity.get()))
		{
			throw new IllegalOperationException("El restaurante ya contiene este plato!");
		}

		platoEntity.get().getRestaurantes().add(restauranteEntity.get());
		restauranteEntity.get().getPlatos().add(platoEntity.get());
		return restauranteEntity.get();
	}

	/**
	 * Obtiene una colección de instancias de RestauranteEntity asociadas a una
	 * instancia de Plato
	 * 
	 * @param platoID Identificador de la instancia de Plato
	 * @return Colección de instancas de RestauranteEntity asociadas a la instancias
	 *         de Plato
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<RestauranteEntity> getRestaurantes(Long platoID) throws EntityNotFoundException {
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		return platoEntity.get().getRestaurantes();
	}

	/**
	 * Obtiene una instancia de RestauranteEntity asociada a una instancia de Plato
	 * 
	 * @param platoID       Identificador de la instancia de Plato
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @return La entidad del Restaurante asociada al plato
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public RestauranteEntity getRestaurante(Long platoID, Long restauranteID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		if (platoEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
			return restauranteEntity.get();
		}

		throw new IllegalOperationException("El restaurante no está asociado con el plato");
	}

	/**
	 * Reemplazar las instancias de Restaurante asociadas a una instancia de Plato
	 * 
	 * @param platoID Identificador de la instancia de Plato
	 * @param list    Colección de instancias de RestauranteEntity a asociar a la
	 *                instancia de Plato
	 * @return Nueva colección de RestauranteEntity asociada a la instancia de
	 *         Plato.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<RestauranteEntity> replaceRestaurantes(Long platoID, List<RestauranteEntity> list)
			throws EntityNotFoundException {
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		for (RestauranteEntity restaurante : list) {
			Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restaurante.getId());
			if (restauranteEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró el restaurante");

			if (!platoEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
				platoEntity.get().getRestaurantes().add(restauranteEntity.get());
				restauranteEntity.get().getPlatos().add(platoEntity.get());
			}
		}
		return getRestaurantes(platoID);
	}

	/**
	 * Desasocia un Restaurante existente de un Plato existente
	 * 
	 * @param platoID       Identificador de la instancia de Plato
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removeRestaurante(Long platoID, Long restauranteID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		platoEntity.get().getRestaurantes().remove(restauranteEntity.get());
		restauranteEntity.get().getPlatos().remove(platoEntity.get());
	}
}
