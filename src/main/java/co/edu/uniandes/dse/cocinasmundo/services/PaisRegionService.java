package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.RegionRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaisRegionService {
	
	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private RegionRepository regionRepository;
	
	@Transactional
	public RegionEntity addRegion(Long paisId, Long regionId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una region a un pais con id = {0}", paisId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no se encontro");

		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("El pais no se encontro");

		paisEntity.get().getRegiones().add(regionEntity.get());
		log.info("Termina proceso de asociarle una region a un pais con id = {0}", paisId);
		return regionEntity.get();
	}
	
	@Transactional
	public List<RegionEntity> getRegiones(Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las regiones con un pais con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais");
		log.info("Finaliza proceso de consultar todas las regiones de un pais con id = {0}", paisId);
		return paisEntity.get().getRegiones();
	}
	
	@Transactional
	public RegionEntity getRegion(Long paisId, Long regionId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la region del pais con id = {0}", paisId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);

		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no se encontro");

		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("El pais no se encontro");
		log.info("Termina proceso de consultar la region del pais con id = {0}", paisId);
		if (paisEntity.get().getRegiones().contains(regionEntity.get()))
			return regionEntity.get();

		throw new IllegalOperationException("The author is not associated to the book");
	}
	
	@Transactional
	public List<RegionEntity> replaceRegiones(Long paisId, List<RegionEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las regiones del pais con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("El pais no se encontro");

		for (RegionEntity region : list) {
			Optional<RegionEntity> regionEntity = regionRepository.findById(region.getId());
			if (regionEntity.isEmpty())
				throw new EntityNotFoundException("La region no se encontro");

			if (!paisEntity.get().getRegiones().contains(regionEntity.get()))
				paisEntity.get().getRegiones().add(regionEntity.get());
		}
		log.info("Termina proceso de reemplazar las regiones del pais con id = {0}", paisId);
		return getRegiones(paisId);
	}
	
	@Transactional
	public void removeRegion(Long paisId, Long regionId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una region del pais con id = {0}", paisId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);

		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no se encontro");

		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("El pais no se encontro");

		paisEntity.get().getRegiones().remove(regionEntity.get());

		log.info("Termina proceso de borrar una region del pais con id = {0}", paisId);
	}
	
}
