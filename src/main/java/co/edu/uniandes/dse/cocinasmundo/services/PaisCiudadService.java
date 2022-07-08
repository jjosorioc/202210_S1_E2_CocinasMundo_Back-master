package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaisCiudadService {
	
	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private PaisRepository paisRepository;

	/*
	 * Métodos
	 */

	@Transactional
	public CiudadEntity addCiudad(Long paisId, Long ciudadId) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);

		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el país");

		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		ciudadEntity.get().setPais(paisEntity.get());

		return ciudadEntity.get();
	}

	
	@Transactional
	public List<CiudadEntity> getCiudades(Long paisId) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró el país");
		}

		return paisEntity.get().getCiudades();
	}

	
	@Transactional
	public CiudadEntity getCiudad(Long paisId, Long ciudadId)
			throws IllegalOperationException, EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el país");

		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		if (paisEntity.get().getCiudades().contains(ciudadEntity.get())) {
			return ciudadEntity.get();
		}

		throw new IllegalOperationException("Esta ciudad no está asociada con el País");
	}

	@Transactional
	public List<CiudadEntity> replaceCiudades(Long paisId, List<CiudadEntity> ciudades) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el país");

		for (CiudadEntity c : ciudades) {
			Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(c.getId());
			if (ciudadEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró la ciudad");

			ciudadEntity.get().setPais(paisEntity.get());
		}

		return ciudades;
	}

}
