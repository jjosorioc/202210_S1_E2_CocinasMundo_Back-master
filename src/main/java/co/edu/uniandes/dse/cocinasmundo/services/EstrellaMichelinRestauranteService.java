package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.EstrellaMichelinRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad estrella michelin y estrellaMichelin
 *
 * @author Tomas Angel
 */
@Slf4j
@Service
public class EstrellaMichelinRestauranteService {
	
	@Autowired
	private EstrellaMichelinRepository estrellaMichelinRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Transactional
	public EstrellaMichelinEntity addEstrellaMichelin(Long estrellaMichelinId, Long restauranteId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociar el estrellaMichelin con id = {0} a la estrella michelin con id = " + restauranteId, estrellaMichelinId);
		Optional<EstrellaMichelinEntity> estrellaMichelinEntity = estrellaMichelinRepository.findById(estrellaMichelinId);
		if (estrellaMichelinEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el estrellaMichelin");

		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la estrella michelin");

		restauranteEntity.get().setEstrellaMichellin(estrellaMichelinEntity.get());
		log.info("Termina proceso de asociar el autor con id = {0} al premio con id = {1}", estrellaMichelinId, restauranteId);
		return estrellaMichelinEntity.get();
	}
	
	@Transactional
	public EstrellaMichelinEntity getEstrellaMichelin(Long restauranteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar el estrellaMichelin de la estrella michelin con id = {0}", restauranteId);
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la estrella michelin.");

		if (restauranteEntity.get().getEstrellaMichellin() == null)
			throw new EntityNotFoundException("El estrellaMichelin no fue encontrado.");

		log.info("Termina proceso de consultar el estrellaMichelin de la estrella michelin con id = {0}", restauranteId);
		return restauranteEntity.get().getEstrellaMichellin();
	}
	
	@Transactional
	public EstrellaMichelinEntity replaceEstrellaMichelin(Long restauranteId, Long estrellaMichelinId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el estrellaMichelin de la estrella michelin con id = {0}", restauranteId);
		Optional<EstrellaMichelinEntity> estrellaMichelinEntity = estrellaMichelinRepository.findById(estrellaMichelinId);
		if (estrellaMichelinEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el estrellaMichelin");

		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el restuarante.");

		restauranteEntity.get().setEstrellaMichellin(estrellaMichelinEntity.get());
		log.info("Termina proceso de asociar el autor con id = {0} al premio con id = " + restauranteId, estrellaMichelinId);
		return estrellaMichelinEntity.get();
	}
	
	@Transactional
	public void removeEstrellaMichelin(Long restauranteId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar el estrellaMichelin de la estrella michelin con id = {0}", restauranteId);
		Optional<RestauranteEntity> restauranteEntity = restauranteRepository.findById(restauranteId);
		if (restauranteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la estrella michelin");

		if (restauranteEntity.get().getEstrellaMichellin() == null) {
			throw new EntityNotFoundException("La estrella michelin no tiene estrellaMichelin");
		}
		Optional<EstrellaMichelinEntity> estrellaMichelinEntity = estrellaMichelinRepository.findById(restauranteEntity.get().getEstrellaMichellin().getId());

		estrellaMichelinEntity.ifPresent(estrellaMichelin -> {
			restauranteEntity.get().setEstrellaMichellin(null);
		});

		log.info("Termina proceso de borrar el estrellaMichelin de la estrella michelin con id = " + restauranteId);
	}
	
}

