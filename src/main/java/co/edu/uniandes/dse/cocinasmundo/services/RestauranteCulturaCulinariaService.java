package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.ArrayList;
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
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteCulturaCulinariaService {
	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	/**
	 * Asocia una cultura culinaria existente a un Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param culturaCulinariaID       Identificador de la instancia de cultura culinaria
	 * @return Instancia de CulturaCulinariaEntity que fue asociada a Restaurante
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public CulturaCulinariaEntity addCulturaCulinaria(Long restauranteID, Long culturaCulinariaID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		culturaCulinariaEntity.get().getRestaurantes().add(restauranteEntity.get());

		return culturaCulinariaEntity.get();
	}
	
	/**
	 * Obtiene una colección de instancias de CulturaCulinariaEntity asociadas a una instancia
	 * de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @return Colección de instancias de CulturaCulinariaEntity asociadas a la instancia de
	 *         Restaurante.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<CulturaCulinariaEntity> getCulturasCulinarias(Long restauranteID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		List<CulturaCulinariaEntity> culturas = culturaCulinariaRepository.findAll();
		List<CulturaCulinariaEntity> culturasCulinariasList = new ArrayList<>();

		for (CulturaCulinariaEntity c : culturas) {
			if (c.getRestaurantes().contains(restauranteEntity.get())) {
				culturasCulinariasList.add(c);
			}
		}
		return culturasCulinariasList;
	}
	
	/**
	 * Obtiene una instancia de CulturaCulinariaEntity asociada a una instancia de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param culturaCulinariaID      Identificador de la instancia de culturas culinarias
	 * @return La entidad de cultura culinaria del Restaurante
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public CulturaCulinariaEntity getCulturaCulinaria(Long restauranteID, Long culturaCulinariaID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		if (culturaCulinariaEntity.get().getRestaurantes().contains(restauranteEntity.get()))
			return culturaCulinariaEntity.get();

		throw new IllegalOperationException("La Cultura Culinaria no está asociada al Restaurante");
	}
	
	/**
	 * Reemplaza las instancias de Cultura Culinaria asociadas a una instancia de Restaurante
	 * 
	 * @param restauranteID Identificador de la instancia del Restaurante
	 * @param culturasCulinarias        Coleccion de instancias de CulturaCulinariaEntity a asociar a la
	 *                      instancia de Restaurante
	 * @return Nueva coleccion de CulturaCulinariaEntity asociada a la instancia de
	 *         Restaurante.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<CulturaCulinariaEntity> replaceCulturasCulinarias(Long restauranteID, List<CulturaCulinariaEntity> culturas)
			throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		for (CulturaCulinariaEntity cultura : culturas) {
			Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(cultura.getId());
			if (culturaCulinariaEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró la cultura culinaria");

			if (!restauranteEntity.get().getCulturasCulinarias().contains(culturaCulinariaEntity.get())) {
				restauranteEntity.get().getCulturasCulinarias().add(culturaCulinariaEntity.get());
			}
		}
		return culturas;
	}
	
	/**
	 * Desasocia una cultura culinaria existente de un Restaurante existente
	 * 
	 * @param restauranteID Identificador de la instancia de Restaurante
	 * @param culturaCulinariaID       Identificador de la instancia de la cultura culinaria
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removeCulturaCulinaria(Long restauranteID, Long culturaCulinariaID) throws EntityNotFoundException {
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteID);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaID);
		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		culturaCulinariaEntity.get().getRestaurantes().remove(restauranteEntity.get());

	}
}
