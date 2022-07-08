package co.edu.uniandes.dse.cocinasmundo.services;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.EstrellaMichelinRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstrellaMichelinService {
	@Autowired
	private EstrellaMichelinRepository estrellaMichelinRepository;
	
	/**
	 * Se encarga de crear una estrella michelin en la base de datos.
	 * 
	 * @param estrellaMichelin Objeto de EstrellaMichelin
	 * @return Objeto de EstrellaMichelinEntity con los datos nuevos y su id.
	 */
	@Transactional
	public EstrellaMichelinEntity createEstrellaMichelin(EstrellaMichelinEntity estrellaMichelin) {
		log.info("Inicia el proceso de creacion de una estrella michelin");
		return estrellaMichelinRepository.save(estrellaMichelin);
	}
	
	/**
	 * Obtiene la lista de los registros de las Estrellas Michelin.
	 * 
	 * @return Coleccion de objetos de EstrellaMichelinEntity.
	 */
	@Transactional
	public List<EstrellaMichelinEntity> getEstrellaMichelin() {
		log.info("Inicia el proceso de obtener todas las estrellas michelin.");
		return estrellaMichelinRepository.findAll();
	}
	
	/**
	 * Obtiene una estrella michelin utilizando su id.
	 * 
	 * @param estrellaMichelinId = es la id de la estrella michelin que se esta buscando
	 * @return Estrella michelin solicitado
	 * @throws EntityNotFoundException si no se encuentra la estrella michelin que se estaba buscando
	 */
	@Transactional
	public EstrellaMichelinEntity getEstrellaMichelinId (Long estrellaMichelinId) throws EntityNotFoundException {
		log.info("Inicia el proceso de consultar la estrella michelin con id = {0}", estrellaMichelinId);
		Optional<EstrellaMichelinEntity> estrellaMichelin = estrellaMichelinRepository.findById(estrellaMichelinId);
		if (estrellaMichelin.isEmpty()) {
			throw new EntityNotFoundException("La estrella michelin que esta buscando no existe.");
		}
		log.info("Termian el proceso de consulta para una estrella michelin con un id = {0}", estrellaMichelinId);
		return estrellaMichelin.get();
	}
		
	/**
	 * Actualiza la informacion de una instancia de estrella michelin
	 * 
	 * @param estrellaMichelinId	  Identificador de la instancia a actualizar
	 * @param estrellaMichelinEntity  Instancia de EstrellaMichelinEntity con los nuevos datos
	 * @return Instancia de EstrellaMichelinEntity con los datos actualizados
	 */
	@Transactional
	public EstrellaMichelinEntity updateEstrellaMichelin(Long estrellaMichelinId, EstrellaMichelinEntity estrellaMichelin) throws EntityNotFoundException {
		log.info("Inicia el proceso de actualizar la estrella michelin con id = {0}", estrellaMichelinId);
		Optional<EstrellaMichelinEntity> estrellaMichelinEntity = estrellaMichelinRepository.findById(estrellaMichelinId);
		if (estrellaMichelinEntity.isEmpty()) {
			throw new EntityNotFoundException("La estrella michelin a actualizar no existe");
		}
		log.info("Termina proceso de actualizar la estrella michelin con id = {0}", estrellaMichelinId);
		estrellaMichelin.setId(estrellaMichelinId);
		return estrellaMichelinRepository.save(estrellaMichelin);
	}
	
	/**
	 * Elimina una instancia de estrella michelin de la base de datos.
	 * 
	 * @param estrellaMichelinId	Identificador de la instancia a eliminar.
	 * @throws EntityNotFoundException si no existe la estrella michelin.
	 */
	@Transactional
	public void deleteEstrellaMichelin(Long estrellaMichelinId) throws EntityNotFoundException {
		log.info("Inicia el proceso de eliminacion de la estrella michelin con id = {0}", estrellaMichelinId);
		Optional<EstrellaMichelinEntity> estrellaMichelinEntity = estrellaMichelinRepository.findById(estrellaMichelinId);
		if (estrellaMichelinEntity.isEmpty()) {
			throw new EntityNotFoundException("La estrella michelin a eliminar no exite");
		}
		estrellaMichelinRepository.deleteById(estrellaMichelinId);
		log.info("Termina el proceso de eliminar la estrella michelin con id = {0}", estrellaMichelinId);
	}

}



