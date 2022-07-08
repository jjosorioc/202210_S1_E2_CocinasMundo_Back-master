package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
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

/**
 * Servicio que modela la relación entre Región y CulturaCulinaria
 *
 * @author Alejandro Tovar
 */
@Slf4j
@Service
public class PaisCulturaCulinariaService {

	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	@Autowired
	private PaisRepository paisRepository;
	
	/**
	 * Agregar una culturaCulinaria a la pais
	 *
	 * @param culturaCulinariaId      El id libro a guardar
	 * @param paisId El id de la pais en la cual se va a guardar el libro.
	 * @return El libro creado.
	 * @throws EntityNotFoundException 
	 * @throws IllegalOperationException 
	 */
	
	@Transactional
	public CulturaCulinariaEntity addCulturaCulinaria(Long culturaCulinariaId, Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle una cultura culinaria al país con id = "+ paisId);
		
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if(culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No existe la cultura culinaria que se busca");
		
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if(paisEntity.isEmpty())
			throw new EntityNotFoundException("No existe el país especificado");
		paisEntity.get().getCulturasCulinarias().add(culturaCulinariaEntity.get());
		culturaCulinariaEntity.get().setPais(paisEntity.get());
		log.info("Termina proceso de agregarle una cultura culinaria al país con id =  "+ paisId);
		return culturaCulinariaEntity.get();
	}

	/**
	 * Retorna todos los culturaCulinarias asociados a una pais
	 *
	 * @param paisId El ID de la pais buscada
	 * @return La lista de libros de la pais
	 * @throws EntityNotFoundException si la pais no existe
	 */
	@Transactional
	public List<CulturaCulinariaEntity> getCulturasCulinarias(Long paisId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar las culturas culinarias asociadas al pais con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if(paisEntity.isEmpty())
			throw new EntityNotFoundException("No existe el país especificado");
		
		return paisEntity.get().getCulturasCulinarias();
	}

	/**
	 * Retorna un culturaCulinaria asociado a una pais
	 *
	 * @param paisId El id de la pais a buscar.
	 * @param culturaCulinariaId      El id del libro a buscar
	 * @return El libro encontrado dentro de la pais.
	 * @throws EntityNotFoundException Si el libro no se encuentra en la pais
	 * @throws IllegalOperationException Si el libro no está asociado a la pais
	 */
	@Transactional
	public CulturaCulinariaEntity getCulturaCulinaria(Long paisId, Long culturaCulinariaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar la cultura culinaria con id = {0} del país con id = " + paisId, culturaCulinariaId);
		
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if(paisEntity.isEmpty())
			throw new EntityNotFoundException("No existe el país especificado");
		
		Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
		if(culturaCulinariaEntity.isEmpty())
			throw new EntityNotFoundException("No existe la cultura culinaria que se busca");
						
		if(paisEntity.get().getCulturasCulinarias().contains(culturaCulinariaEntity.get()))
			return culturaCulinariaEntity.get();
		
		throw new EntityNotFoundException("El pais no está asociado con esa cultura culinaria");
	}

	/**
	 * Remplazar culturaCulinarias de una pais
	 *
	 * @param culturaCulinarias        Lista de libros que serán los de la pais.
	 * @param paisId El id de la pais que se quiere actualizar.
	 * @return La lista de libros actualizada.
	 * @throws EntityNotFoundException Si la pais o un libro de la lista no se encuentran
	 */
	@Transactional
	public List<CulturaCulinariaEntity> replaceCulturasCulinarias(Long paisId, List<CulturaCulinariaEntity> culturaCulinarias) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar la pais con id = {0}", paisId);
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisId);
		if(paisEntity.isEmpty())
			throw new EntityNotFoundException("No existe el país especificado");
		
		for(CulturaCulinariaEntity culturaCulinaria : culturaCulinarias) {
			Optional<CulturaCulinariaEntity> b = culturaCulinariaRepository.findById(culturaCulinaria.getId());
			if(b.isEmpty())
				throw new EntityNotFoundException("No existe la cultura culinaria");
			if(b.get().getPais()!=null)
				b.get().getPais().getCulturasCulinarias().remove(b.get());
			b.get().setPais(paisEntity.get());
			paisEntity.get().getCulturasCulinarias().add(b.get());
		}		
		return paisEntity.get().getCulturasCulinarias();
	}
}