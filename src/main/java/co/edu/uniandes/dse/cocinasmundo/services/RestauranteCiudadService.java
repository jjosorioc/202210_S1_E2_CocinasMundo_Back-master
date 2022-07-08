package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteCiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;


	@Transactional
	public RestauranteEntity replaceCiudad(Long restauranteId, Long ciudadId) throws EntityNotFoundException {

		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		restauranteEntity.get().setCiudad(ciudadEntity.get());

		return restauranteEntity.get();
	}

	 
	@Transactional
	public void removeCiudad(Long restauranteId) throws EntityNotFoundException {

		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el restaurante");

		Optional<CiudadEntity> ciudadEntity = ciudadRepository
				.findById(restauranteEntity.get().getCiudad().getId());
		ciudadEntity.ifPresent(ciudad -> ciudad.getRestaurantes().remove(restauranteEntity.get()));

		restauranteEntity.get().setCiudad(null); // NULL
	}
}
