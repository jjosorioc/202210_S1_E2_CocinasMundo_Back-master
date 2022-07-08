package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RegionRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Pais y Region.
 *
 * @author Tomas Angel
 */

@Slf4j
@Service
public class RegionPaisService {
	
	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private RegionRepository regionRepository;
	
	@Transactional
	public PaisEntity addPais(Long regionId, Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un pais a la region con id = {0}", regionId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);

		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no fue encontrada.");

		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("El pais no fue encontrado");

		paisEntity.get().getRegiones().add(regionEntity.get());
		log.info("Termina proceso de asociarle un pais a la region con id = {0}", regionId);
		return paisEntity.get();
	}
	
	@Transactional
	public List<PaisEntity> getPaises(Long regionId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los paises de la region con id = {0}", regionId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no fue encontrada.");

		List<PaisEntity> paises = paisRepository.findAll();
		List<PaisEntity> paisList = new ArrayList<>();

		for (PaisEntity b : paises) {
			if (b.getRegiones().contains(regionEntity.get())) {
				paisList.add(b);
			}
		}
		log.info("Termina proceso de consultar todos los paises de la region con id = {0}", regionId);
		return paisList;
	}
	
	@Transactional
	public PaisEntity getPais(Long regionId, Long paisId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el pais con id = {0} de la region con id = " + regionId, paisId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);

		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la region.");

		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais");

		log.info("Termina proceso de consultar el pais con id = {0} de la region con id = " + regionId, paisId);
		if (paisEntity.get().getRegiones().contains(regionEntity.get()))
			return paisEntity.get();

		throw new IllegalOperationException("El pais no esta asociado a la region");
	}
	
	@Transactional
	public List<PaisEntity> addPaises(Long regionId, List<PaisEntity> paises) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los paises asociados a la region con id = {0}", regionId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La region no se encontro");

		for (PaisEntity pais : paises) {
			Optional<PaisEntity> paisEntity = paisRepository.findById(pais.getId());
			if (paisEntity.isEmpty())
				throw new EntityNotFoundException("El libro no se encontro");

			if (!paisEntity.get().getRegiones().contains(regionEntity.get()))
				paisEntity.get().getRegiones().add(regionEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los paises asociados a la region con id = {0}", regionId);
		return paises;
	}
	
	@Transactional
	public void removePais(Long regionId, Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un pais de la region con id = {0}", regionId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la region");

		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el pais.");

		paisEntity.get().getRegiones().remove(regionEntity.get());
		log.info("Finaliza proceso de borrar un pais de la region con id = {0}", regionId);
	}
}
