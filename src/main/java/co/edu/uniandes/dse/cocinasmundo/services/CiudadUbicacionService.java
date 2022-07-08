package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.UbicacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CiudadUbicacionService {
	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private CiudadRepository ciudadRepository;
	
	@Transactional
	public UbicacionEntity addUbicacion(Long ubicacionId, Long ciudadId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociar la ciudad con id = {0} a la ubicacion con id = " + ciudadId, ubicacionId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ubicacion");

		Optional<CiudadEntity> ciudadEntity =ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ciudad");

		ciudadEntity.get().setUbicacion(ubicacionEntity.get());
		log.info("Termina proceso de asociar la ubicacion con id = {0} a la ciudad con id = {1}", ubicacionId, ciudadId);
		return ubicacionEntity.get();
	}
	
	@Transactional
	public UbicacionEntity getUbicacion(Long ciudadId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la ubicacion de la ciudad  con id = {0}", ciudadId);
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ciudad.");

		if (ciudadEntity.get().getUbicacion() == null)
			throw new EntityNotFoundException("La ubicacion no fue encontrada.");

		log.info("Termina proceso de consultar la ubicacion de la ciudad con id = {0}", ciudadId);
		return ciudadEntity.get().getUbicacion();
	}
	
	@Transactional
	public UbicacionEntity replaceUbicacion(Long ciudadId, Long ubicacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la ubicacion de la ciudad con id = {0}", ciudadId);
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
		if (ubicacionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ubicacion");

		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ciudad.");

		ciudadEntity.get().setUbicacion(ubicacionEntity.get());
		log.info("Termina el proceso de asociar la ubicacion con id = {0} a la ciudad con id = " + ubicacionId, ciudadId);
		return ubicacionEntity.get();
	}
	
	@Transactional
	public void removeUbicacion(Long ciudadId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar la ubicacion de la ciudad con id = {0}", ciudadId);
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la ciudad");

		if (ciudadEntity.get().getUbicacion() == null) {
			throw new EntityNotFoundException("La ciudad no tiene ubicacion");
		}
		Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ciudadEntity.get().getUbicacion().getId());

		ubicacionEntity.ifPresent(ubicacion -> {
			ciudadEntity.get().setUbicacion(null);
		});

		log.info("Termina proceso de borrar la ubicacion de la ciudad con id = " + ciudadId);
	}
}
