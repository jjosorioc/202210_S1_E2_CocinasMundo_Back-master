package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CiudadRestauranteService {
	
	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	/*
	 * Métodos
	 */

	@Transactional
	public RestauranteEntity addRestaurante(Long ciudadId, Long restauranteId) throws EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);

		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		restauranteEntity.get().setCiudad(ciudadEntity.get());

		return restauranteEntity.get();
	}

	
	@Transactional
	public List<RestauranteEntity> getRestaurantes(Long ciudadId) throws EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró la ciudad");
		}

		return ciudadEntity.get().getRestaurantes();
	}

	
	@Transactional
	public RestauranteEntity getRestaurante(Long restauranteId, Long ciudadId)
			throws IllegalOperationException, EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		Optional<RestauranteEntity> restauranteEntity =restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		if (ciudadEntity.get().getRestaurantes().contains(restauranteEntity.get())) {
			return restauranteEntity.get();
		}

		throw new IllegalOperationException("Este restaurante no está asociado con la ciudad");
	}

	@Transactional
	public List<RestauranteEntity> replaceRestaurantes(Long ciudadId, List<RestauranteEntity> restaurantes) throws EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		for (RestauranteEntity r : restaurantes) {
			Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(r.getId());
			if (restauranteEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró el restaurante");

			restauranteEntity.get().setCiudad(ciudadEntity.get());
		}

		return restaurantes;
	}

}

