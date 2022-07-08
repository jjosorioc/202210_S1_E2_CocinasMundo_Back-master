package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CiudadPaisService {

	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private PaisRepository paisRepository;


	@Transactional
	public CiudadEntity replacePais(Long ciudadId, Long paisId) throws EntityNotFoundException {

		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el país");

		ciudadEntity.get().setPais(paisEntity.get());

		return ciudadEntity.get();
	}

	 
	@Transactional
	public void removePais(Long ciudadId) throws EntityNotFoundException {

		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la ciudad");

		Optional<PaisEntity> paisEntity = paisRepository
				.findById(ciudadEntity.get().getPais().getId());
		paisEntity.ifPresent(pais -> pais.getCiudades().remove(ciudadEntity.get()));

		ciudadEntity.get().setPais(null); // NULL
	}
}
