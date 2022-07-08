package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PlatoRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CulturaCulinariaRestauranteService {
	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	/**
	 * Asocia un Restaurante existente a una cultura culinaria
	 * 
	 * @param culturaCulinariaID       Identificador de la instancia de cultura culinaria
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @return Instancia de RestauranteEntity que fue asociada a cultura culinaria
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public RestauranteEntity addRestaurante(Long culturaCulinariaID, Long restauranteID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		culturaCulinariaEntity.get().getRestaurantes().add(restauranteEntity.get());
		return restauranteEntity.get();
	}
	
	/**
	 * Obtiene una colección de instancias de RestauranteEntity asociadas a una
	 * instancia de cultura culinaria
	 * 
	 * @param culturaCulinariaID Identificador de la instancia de cultura culinaria
	 * @return Colección de instancas de RestauranteEntity asociadas a la instancias
	 *         de cultura culinaria
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<RestauranteEntity> getRestaurantes(Long culturaCulinariaID) throws EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		return culturaCulinariaEntity.get().getRestaurantes();
	}
	
	/**
	 * Obtiene una instancia de RestauranteEntity asociada a una instancia de cultura culinaria
	 * 
	 * @param culturaCulinariaID       Identificador de la instancia de cc
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @return La entidad del Restaurante asociada a la cc
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public RestauranteEntity getRestaurante(Long culturaCulinariaID, Long restauranteID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		if (culturaCulinariaEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
			return restauranteEntity.get();
		}

		throw new IllegalOperationException("El restaurante no está asociado con la cultura culinaria");
	}
	
	/**
	 * Reemplazar las instancias de Restaurante asociadas a una instancia de cc
	 * 
	 * @param culturaCulinariaID Identificador de la instancia de cc
	 * @param list    Colección de instancias de RestauranteEntity a asociar a la
	 *                instancia de cc
	 * @return Nueva colección de RestauranteEntity asociada a la instancia de
	 *         cc.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<RestauranteEntity> replaceRestaurantes(Long culturaCulinariaID, List<RestauranteEntity> list)
			throws EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);
		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		for (RestauranteEntity restaurante : list) {
			Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restaurante.getId());
			if (restauranteEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró el restaurante");

			if (!culturaCulinariaEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
				culturaCulinariaEntity.get().getRestaurantes().add(restauranteEntity.get());
			}
		}
		return getRestaurantes(culturaCulinariaID);
	}
	
	/**
	 * Desasocia un Restaurante existente de una cc existente
	 * 
	 * @param culturaCulinariaID       Identificador de la instancia de una cc
	 * @param restauranteID Identificador de la instancia Restaurante
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removeRestaurante(Long culturaCulinariaID, Long restauranteID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		culturaCulinariaEntity.get().getRestaurantes().remove(restauranteEntity.get());
	}
}
