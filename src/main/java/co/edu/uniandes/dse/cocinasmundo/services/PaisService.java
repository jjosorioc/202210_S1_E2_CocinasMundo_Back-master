package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.PaisRepository;

@Service
public class PaisService {
	@Autowired
	private PaisRepository paisRepository;

	/**
	 * Obtiene la lista de los registros de Pais.
	 * 
	 * @return Colección de objetos de PaisEntity.
	 */
	@Transactional
	public List<PaisEntity> getPaises() {
		return paisRepository.findAll();
	}

	/**
	 * Se obtiene un PaisEntity según el ID.
	 * 
	 * @param paisID
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public PaisEntity getPaisByID(Long paisID) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisID);

		if (paisEntity.isEmpty()) {
			throw new EntityNotFoundException("No existe ese PaisEntity");
		}
		return paisEntity.get();
	}

	/**
	 * Se guarda un PaisEntity en la base de datos.
	 * 
	 * @param entity
	 * @return
	 * @throws IllegalOperationException
	 */
	@Transactional
	public PaisEntity createPais(PaisEntity entity) throws IllegalOperationException {

		List<PaisEntity> alreadyExists = paisRepository.findByNombre(entity.getNombre());
		if (!alreadyExists.isEmpty()) {
			throw new IllegalOperationException("¡Ese país ya existe!");
		} else {
			return paisRepository.save(entity);
		}

	}

	/**
	 * Actualiza la información de una instancia de PaisEntity.
	 * 
	 * @param paisID
	 * @param newPais
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public PaisEntity updatePais(Long paisID, PaisEntity newPais) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisID);

		if (paisEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró el PaisEntity");
		}

		newPais.setId(paisID);
		return paisRepository.save(newPais);
	}

	/**
	 * Se borra un PaisEntity de la base de datos según su ID.
	 * 
	 * @param paisID
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void deletePais(Long paisID) throws EntityNotFoundException {
		Optional<PaisEntity> paisEntity = paisRepository.findById(paisID);

		if (paisEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró el PaisEntity");
		}

		paisRepository.deleteById(paisID);
	}
}
