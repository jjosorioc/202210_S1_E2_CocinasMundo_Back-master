package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
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


/**
 * Servicio que modela la relación entre Región y CulturaCulinaria
 *
 * @author Alejandro Tovar
 */
@Slf4j
@Service
public class RegionCulturaCulinariaService {

	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private RegionRepository regionRepository;
	
	/**
	 * Agregar una culturaCulinaria a la region
	 *
	 * @param culturaCulinariaId      El id libro a guardar
	 * @param regionId El id de la region en la cual se va a guardar el libro.
	 * @return El libro creado.
	 * @throws EntityNotFoundException 
	 * @throws IllegalOperationException 
	 */
	
	@Transactional
	public CulturaCulinariaEntity addCulturaCulinaria(Long culturaCulinariaId, Long regionId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de agregarle una cultura culinaria a la region con id = {0}", regionId);
		
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if(culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No existe la cultura culinaria que se busca");
		
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if(regionEntity.isEmpty())
			throw new EntityNotFoundException("No existe la región especificada");
		if(culturaCulinariaEntity.get().getRegion()!=null)
			throw new IllegalOperationException("La cultura culinaria ya está asociada a una región");
		regionEntity.get().getCulturasCulinarias().add(culturaCulinariaEntity.get());
		culturaCulinariaEntity.get().setRegion(regionEntity.get());
		log.info("Termina proceso de agregarle una cultura culinaria a la region con id = "+ regionId);
		return culturaCulinariaEntity.get();
	}

	/**
	 * Retorna todos los culturaCulinarias asociados a una region
	 *
	 * @param regionId El ID de la region buscada
	 * @return La lista de libros de la region
	 * @throws EntityNotFoundException si la region no existe
	 */
	@Transactional
	public List<CulturaCulinariaEntity> getCulturasCulinarias(Long regionId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar las culturas culinarias asociadas a la region con id = {0}", regionId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if(regionEntity.isEmpty())
			throw new EntityNotFoundException("No existe la región especificada");
		
		return regionEntity.get().getCulturasCulinarias();
	}

	/**
	 * Retorna un culturaCulinaria asociado a una region
	 *
	 * @param regionId El id de la region a buscar.
	 * @param culturaCulinariaId      El id del libro a buscar
	 * @return El libro encontrado dentro de la region.
	 * @throws EntityNotFoundException Si el libro no se encuentra en la region
	 * @throws IllegalOperationException Si el libro no está asociado a la region
	 */
	@Transactional
	public CulturaCulinariaEntity getCulturaCulinaria(Long regionId, Long culturaCulinariaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la cultura culinaria con id = {0} de la region con id = " + regionId, culturaCulinariaId);
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if(regionEntity.isEmpty())
			throw new EntityNotFoundException("No existe la región especificada");
		
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if(culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No existe la cultura culinaria que se busca");
						
		if(regionEntity.get().getCulturasCulinarias().contains(culturaCulinariaEntity.get()))
			return culturaCulinariaEntity.get();
		
		throw new EntityNotFoundException("La region no está asociada con esa cultura culinaria");
	}

	/**
	 * Remplazar culturaCulinarias de una region
	 *
	 * @param culturaCulinarias        Lista de libros que serán los de la region.
	 * @param regionId El id de la region que se quiere actualizar.
	 * @return La lista de libros actualizada.
	 * @throws EntityNotFoundException Si la region o un libro de la lista no se encuentran
	 */
	@Transactional
	public List<CulturaCulinariaEntity> replaceCulturasCulinarias(Long regionId, List<CulturaCulinariaEntity> culturasCulinarias) throws EntityNotFoundException {
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if(regionEntity.isEmpty())
			throw new EntityNotFoundException("No existe la región especificada");
		for(CulturaCulinariaEntity culturaCulinaria : culturasCulinarias) {
			Optional<CulturaCulinariaEntity> b = culturaCulinariaRepository.findById(culturaCulinaria.getId());
			if(b.isEmpty())
				throw new EntityNotFoundException("No existe la cultura culinaria");
			if(b.get().getRegion()!=null)
				b.get().getRegion().getCulturasCulinarias().remove(b.get());
			b.get().setRegion(regionEntity.get());
			regionEntity.get().getCulturasCulinarias().add(b.get());
		}		
		return regionEntity.get().getCulturasCulinarias();
	}
}
