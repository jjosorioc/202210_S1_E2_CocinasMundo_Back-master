package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.PlatoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlatoCulturaCulinariaService {

	@Autowired
	private PlatoRepository platoRepository;

	@Autowired
	private CulturaCulinariaRepository culturaCulinariaRepository;

	/**
	 * Reemplazar la Cultura Culinaria de un Plato.
	 * 
	 * @param platoID   id del plato que se quiere actualizar
	 * @param culturaID El id de la nueva cultura culinaria
	 * @return el nuevo plato
	 */
	@Transactional
	public PlatoEntity replaceCulturaCulinaria(Long platoID, Long culturaID) throws EntityNotFoundException {

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");
		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository.findById(culturaID);
		if (culturaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró la cultura culinaria");

		platoEntity.get().setCulturaCulinaria(culturaEntity.get());

		return platoEntity.get();
	}

	/**
	 * Borrar un plato de una cultura culinaria. Este método se utiliza para borrar
	 * la relación de un plato
	 * 
	 * @param platoID El plato que se desea borrar de la cultura culinaria
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removeCulturaCulinaria(Long platoID) throws EntityNotFoundException {

		Optional<PlatoEntity> platoEntity = platoRepository.findById(platoID);
		if (platoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el plato");

		Optional<CulturaCulinariaEntity> culturaEntity = culturaCulinariaRepository
				.findById(platoEntity.get().getCulturaCulinaria().getId());
		culturaEntity.ifPresent(cultura -> cultura.getRecetasMasRepresentativas().remove(platoEntity.get()));

		platoEntity.get().setCulturaCulinaria(null); // NULL
	}
}
