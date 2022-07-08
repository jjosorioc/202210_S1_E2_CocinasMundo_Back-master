package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RegionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/**
 * Servicio que modela la relación entre CulturaCulinaria y Región
 *
 * @author Alejandro Tovar
 */
public class CulturaCulinariaRegionService {
	
	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private RegionRepository regionRepository;
	
	/**
	 * Remplazar region de un premio
	 *
	 * @param culturaCulinariaId  el id del premio que se quiere actualizar.
	 * @param regionId El id del nuebo region asociado al premio.
	 * @return el nuevo region asociado.
	 * @throws EntityNotFoundException
	 */

	@Transactional
	public CulturaCulinariaEntity replaceRegion(Long culturaCulinariaId, Long regionId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la región de la cultura culinaria con id = {0}", culturaCulinariaId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty())
			throw new EntityNotFoundException("La región nueva no existe");

		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("La cultura culinaria especificada no existe");

		if(culturaCulinariaEntity.get().getRegion()!=null)
			culturaCulinariaEntity.get().getRegion().getCulturasCulinarias().remove(culturaCulinariaEntity.get());
		culturaCulinariaEntity.get().setRegion(regionEntity.get());
		regionEntity.get().getCulturasCulinarias().add(culturaCulinariaEntity.get());
		log.info("Termina proceso de asociar el region con id = {0} a la cultura culinaria con id = " + culturaCulinariaId, regionId);
		return culturaCulinariaEntity.get();
	}
	
	/**
	 * Desasocia la Region de una CulturaCulinaria existente
	 *
	 * @param culturaCulinariaId   Identificador de la instancia de CulturaCulinaria
	 * @param
	 * @throws IllegalOperationException 
	 */
	public void removeRegion(Long culturaCulinariaId) throws EntityNotFoundException {
		log.info("Inicia proceso de eliminar la region de la cultura culinaria con id = {0}", culturaCulinariaId);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("La cultura culinaria especificada no existe");
		
		Optional<RegionEntity> regionEntity = regionRepository
				.findById(culturaCulinariaEntity.get().getRegion().getId());
		regionEntity.ifPresent(region -> region.getCulturasCulinarias().remove(culturaCulinariaEntity.get()));
		culturaCulinariaEntity.get().setRegion(null);

		log.info("Termina proceso de borrar una region de la cultura culinaria con id = {0}", culturaCulinariaId);
	}
}