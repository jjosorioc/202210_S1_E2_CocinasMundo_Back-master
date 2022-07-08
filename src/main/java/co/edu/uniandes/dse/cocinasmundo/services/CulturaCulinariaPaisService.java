package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/**
 * Servicio que modela la relación entre CulturaCulinaria y Región
 *
 * @author Alejandro Tovar
 */
public class CulturaCulinariaPaisService {
	
	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private PaisRepository paisRepository;
	
	/**
	 * Remplazar pais de un premio
	 *
	 * @param culturaCulinariaId  el id del premio que se quiere actualizar.
	 * @param paisId El id del nuebo pais asociado al premio.
	 * @return el nuevo pais asociado.
	 * @throws EntityNotFoundException
	 */

	@Transactional
	public CulturaCulinariaEntity replacePais(Long culturaCulinariaId, Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar el pais del premio premio con id = {0}", culturaCulinariaId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if (paisEntity.isEmpty())
			throw new EntityNotFoundException("La región nueva no existe");

		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("La cultura culinaria especificada no existe");

		if(culturaCulinariaEntity.get().getPais()!=null)
			culturaCulinariaEntity.get().getPais().getCulturasCulinarias().remove(culturaCulinariaEntity.get());
		culturaCulinariaEntity.get().setPais(paisEntity.get());
		paisEntity.get().getCulturasCulinarias().add(culturaCulinariaEntity.get());
		log.info("Termina proceso de asociar el pais con id = {0} a la cultura culinaria con id = " + culturaCulinariaId, paisId);
		return culturaCulinariaEntity.get();
	}
	
	/**
	 * Desasocia la Pais de una CulturaCulinaria existente
	 *
	 * @param culturaCulinariaId   Identificador de la instancia de CulturaCulinaria
	 * @param
	 * @throws IllegalOperationException 
	 */
	public void removePais(Long culturaCulinariaId) throws EntityNotFoundException {
		log.info("Inicia proceso de eliminar la pais de la cultura culinaria con id = {0}", culturaCulinariaId);
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);

		if (culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("La cultura culinaria especificada no existe");
		
		Optional<PaisEntity> paisEntity = paisRepository
				.findById(culturaCulinariaEntity.get().getPais().getId());
		paisEntity.ifPresent(pais -> pais.getCulturasCulinarias().remove(culturaCulinariaEntity.get()));
		culturaCulinariaEntity.get().setPais(null);

		log.info("Termina proceso de borrar un autor del libro con id = {0}", culturaCulinariaId);
	}
}