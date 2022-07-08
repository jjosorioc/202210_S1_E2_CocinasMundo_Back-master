package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PlatoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CulturaCulinariaPlatoService {
	@Autowired
	private PlatoRepository platoRepository;

	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	/*
	 * Métodos
	 */

	/**
	 * Asocia un Plato existente a una Cultura Culinaria.
	 * 
	 * @param culturaID Identificador de la instancia de CulturaCulinaria
	 * @param platoID   Identificador de la instancia de Plato
	 * @return Instancia de PlatoEntity que fue asociada a CulturaCulinaria
	 * @throws EntityNotFoundException
	 * @throws IllegalOperationException
	 */
	@Transactional
	public PlatoEntity addPlato(Long culturaID, Long platoID)
			throws EntityNotFoundException, IllegalOperationException {
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);

		if (culturaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		if (culturaEntity.get().getRecetasMasRepresentativas().contains(platoEntity.get())) {
			throw new IllegalOperationException("Esta cultura ya contiene el plato!");
		}

		platoEntity.get().setCulturaCulinaria(culturaEntity.get());
		// culturaEntity.get().getRecetasMasRepresentativas().add(platoEntity.get());
		return platoEntity.get();
	}

	/**
	 * Retorna todos los platos asociados a una cultura culinaria
	 * 
	 * @param culturaID El ID de la editorial buscada
	 * @return Colección de instancias de PlatoEntity asociadas a la instancia de
	 *         CulturaCulinaria
	 */
	@Transactional
	public List<PlatoEntity> getPlatos(Long culturaID) throws EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		if (culturaEntity.isEmpty()) {
			throw new EntityNotFoundException("No se encontró la Cultura Culinaria");
		}

		return culturaEntity.get().getRecetasMasRepresentativas();
	}

	/**
	 * Obtiene una instancia de PlatoEntity asociada a una instancia de
	 * CulturaCulinaria
	 * 
	 * @param culturaID Identificador de la instancia de CulturaCulinaria
	 * @param platoID   Identificador de la instancia de Plato
	 * @return La entidad de Plato de la CulturaCulinaria
	 * @throws IllegalOperationException
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public PlatoEntity getPlato(Long culturaID, Long platoID)
			throws IllegalOperationException, EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		if (culturaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		if (culturaEntity.get().getRecetasMasRepresentativas().contains(platoEntity.get())) {
			return platoEntity.get();
		}

		throw new IllegalOperationException("Este Plato no está asociado con la CulturaCulinaria");
	}

	/**
	 * Reemplazar platos de una cultura culinaria
	 * 
	 * @param culturaID ID de la cultura culinaria que se quiere actualizar
	 * @param platos    Lista de platos que serán los de la cultura culinaria
	 * @return Lista de platos actualizados.
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public List<PlatoEntity> replacePlatos(Long culturaID, List<PlatoEntity> platos) throws EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		if (culturaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		for (PlatoEntity p : platos) {
			Optional<PlatoEntity> platoEntity = platoRepository.findById(p.getId());
			if (platoEntity.isEmpty())
				throw new EntityNotFoundException("No se encontró el plato");

			platoEntity.get().setCulturaCulinaria(culturaEntity.get());
		}

		return platos;
	}

	/**
	 * 
	 * @param culturaID
	 * @param platoID
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removePlato(Long culturaID, Long platoID) throws EntityNotFoundException {
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		if (culturaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		culturaEntity.get().getRecetasMasRepresentativas().remove(platoEntity.get());
		platoEntity.get().setCulturaCulinaria(null);

	}

}
