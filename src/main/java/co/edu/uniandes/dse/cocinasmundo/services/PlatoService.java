package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.PlatoRepository;

@Service
public class PlatoService {

	@Autowired
	private PlatoRepository platoRepository;

	/**
	 * Obtiene la lista de los registros de Plato.
	 * 
	 * @return Colección de objetos de PlatoEntity.
	 */
	@Transactional
	public List<PlatoEntity> getPlatos() {
		return platoRepository.findAll();
	}

	/**
	 * Se obtiene un PlatoEntity según el ID.
	 * 
	 * @param platoID
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public PlatoEntity getPlatoByID(Long platoID) throws EntityNotFoundException {
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (platoEntity.isEmpty()) {
			throw new EntityNotFoundException("No existe ese PlatoEntity");
		}

		return platoEntity.get();
	}

	/**
	 * Se guarda un PlatoEntity en la base de datos.
	 * 
	 * @param entity
	 * @return
	 * @throws IllegalOperationException
	 */
	@Transactional
	public PlatoEntity createPlato(PlatoEntity entity) throws IllegalOperationException {
		try {
			return platoRepository.save(entity);
		} catch (Exception e) {
			throw new IllegalOperationException("No se pudo agregar el PlatoEntity");
		}
	}

	/**
	 * Actualiza la información de una instancia de PlatoEntity.
	 * 
	 * @param platoID
	 * @param newPlato
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public PlatoEntity updatePlato(Long platoID, PlatoEntity newPlato) throws EntityNotFoundException {
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró el PlatoEntity.");
		}
		newPlato.setId(platoID);
		return platoRepository.save(newPlato);
	}

	/**
	 * Se borra un PlatoEntity de la base de datos según su ID.
	 * 
	 * @param platoID
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void deletePlato(Long platoID) throws EntityNotFoundException {
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró el PlatoEntity");
		}

		platoRepository.deleteById(platoID);
	}

}
