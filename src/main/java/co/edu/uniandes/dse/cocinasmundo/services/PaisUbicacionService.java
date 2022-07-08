package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.UbicacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaisUbicacionService {
	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private PaisRepository paisRepository;
	
	@Transactional
	public UbicacionEntity addUbicacion(Long ubicacionId, Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociar la ubicacion con id = {0} a la ubicacion con id = " + paisId, ubicacionId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ubicacion");

		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais");

		paisEntity.get().setUbicacion(ubicacionEntity.get());
		log.info("Termina proceso de asociar la ubicacion con id = {0} al pais con id = {1}", ubicacionId, paisId);
		return ubicacionEntity.get();
	}
	
	@Transactional
	public UbicacionEntity getUbicacion(Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la ubicacion del pais  con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais.");

		if (paisEntity.get().getUbicacion() == null)
			throw new EntityNotFoundException("La ubicacion no fue encontrada.");

		log.info("Termina proceso de consultar la ubicacion del pais con id = {0}", paisId);
		return paisEntity.get().getUbicacion();
	}
	
	@Transactional
	public UbicacionEntity replaceUbicacion(Long paisId, Long ubicacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la ubicacion del pais con id = {0}", paisId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ubicacion");

		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais.");

		paisEntity.get().setUbicacion(ubicacionEntity.get());
		log.info("Termina proceso de asociar la ubicacion con id = {0} al pais con id = " + ubicacionId, paisId);
		return ubicacionEntity.get();
	}
	
	@Transactional
	public void removeUbicacion(Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar la ubicacion del pais con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais");

		if (paisEntity.get().getUbicacion() == null) {
			throw new EntityNotFoundException("El pais no tiene ubicacion");
		}
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(paisEntity.get().getUbicacion().getId());

		ubicacionEntity.ifPresent(ubicacion -> {
			paisEntity.get().setUbicacion(null);
		});

		log.info("Termina proceso de borrar la ubicacion del pais con id = " + paisId);
	}
}
