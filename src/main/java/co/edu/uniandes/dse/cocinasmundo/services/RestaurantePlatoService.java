package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.ArrayList;
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
public class RestaurantePlatoService {

	@Autowired
	private PlatoRepository platoRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	/**
	 * Asocia un Plato existente a un Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param platoID       Identificador de la instancia de Plato
	 * @return Instancia de PlatoEntity que fue asociada a Restaurante
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public PlatoEntity addPlato(Long restauranteID, Long platoID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		if (restauranteEntity.get().getPlatos().contains(platoEntity.get())) {
			throw new IllegalOperationException("El restaurante ya tiene ese producto.");
		}

		platoEntity.get().getRestaurantes().add(restauranteEntity.get());
		restauranteEntity.get().getPlatos().add(platoEntity.get());
		return platoEntity.get();
	}

	/**
	 * Obtiene una colección de instancias de PlatoEntity asociadas a una instancia
	 * de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @return COlección de instancias de PlatoEntity asociadas a la instancia de
	 *         Restaurante.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<PlatoEntity> getPlatos(Long restauranteID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		List<PlatoEntity> platos = platoRepository.findAll();
		List<PlatoEntity> platoList = new ArrayList<>();

		for (PlatoEntity p : platos) {
			if (p.getRestaurantes().contains(restauranteEntity.get())
					|| restauranteEntity.get().getPlatos().contains(p)) {
				platoList.add(p);
			}
		}
		return platoList;
	}

	/**
	 * Obtiene una instancia de PlatoEntity asociada a una instancia de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param platoID       Identificador de la instancia de Plato
	 * @return La entidad de Plato del Restaurante
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public PlatoEntity getPlato(Long restauranteID, Long platoID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

//		if (platoEntity.get().getRestaurantes().contains(restauranteEntity.get()))
//			return platoEntity.get();
		if (restauranteEntity.get().getPlatos().contains(platoEntity.get())) {
			return platoEntity.get();
		}
		throw new IllegalOperationException("El Plato no está asociado al Restaurante");
	}

	/**
	 * Reemplaza las instancias de Plato asociadas a una instancia de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia del Restaurante
	 * @param platos        Colecci[on de instancias de PlatoEntity a asociar a la
	 *                      instancia de Restaurante
	 * @return Nueva colecci[on de PlatoEntity asociada a la instancia de
	 *         Restaurante.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<PlatoEntity> replacePlatos(Long restauranteID, List<PlatoEntity> platos)
			throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		for (PlatoEntity plato : platos) {
			Optional<PlatoEntity> platoEntity = platoRepository.findById(plato.getId());
			if (platoEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró el plato");
//			if (!platoEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
//				platoEntity.get().getRestaurantes().add(restauranteEntity.get());
//			}
			if (!restauranteEntity.get().getPlatos().contains(platoEntity.get())) {
				restauranteEntity.get().getPlatos().add(platoEntity.get());
			}
		}
		return platos;
	}

	/**
	 * Desasocia un Plato existente de un Restaurante existente
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param platoID       Identificador de la instancia de Plato
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removePlato(Long restauranteID, Long platoID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		platoEntity.get().getRestaurantes().remove(restauranteEntity.get());
		restauranteEntity.get().getPlatos().remove(platoEntity.get());

	}
}
