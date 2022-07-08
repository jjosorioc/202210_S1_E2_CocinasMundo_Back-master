package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.repositories.IngredienteRepository;
import co.edu.uniandes.dse.cocinasmundo.repositories.RecetaRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Ingrediente y Receta.
 *
 * @author Alejandro Tovar
 */

@Slf4j
@Service
public class RecetaIngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private RecetaRepository recetaRepository;
	
	@Transactional
	public IngredienteEntity addIngrediente(Long recetaId, Long ingredienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle un ingrediente a la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);

		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no fue encontrada.");

		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("El ingrediente no fue encontrado");

		ingredienteEntity.get().getRecetas().add(recetaEntity.get());
		log.info("Termina proceso de asociarle un ingrediente a la receta con id = {0}", recetaId);
		return ingredienteEntity.get();
	}
	
	@Transactional
	public List<IngredienteEntity> getIngredientes(Long recetaId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todos los ingredientes de la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no fue encontrada.");

		List<IngredienteEntity> ingredientes = ingredienteRepository.findAll();
		List<IngredienteEntity> ingredienteList = new ArrayList<>();

		for (IngredienteEntity b : ingredientes) {
			if (b.getRecetas().contains(recetaEntity.get())) {
				ingredienteList.add(b);
			}
		}
		log.info("Termina proceso de consultar todos los ingredientes de la receta con id = {0}", recetaId);
		return ingredienteList;
	}
	
	@Transactional
	public IngredienteEntity getIngrediente(Long recetaId, Long ingredienteId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar el ingrediente con id = {0} de la receta con id = " + recetaId, ingredienteId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);

		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la receta.");

		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el ingrediente");

		log.info("Termina proceso de consultar el ingrediente con id = {0} de la receta con id = " + recetaId, ingredienteId);
		if (ingredienteEntity.get().getRecetas().contains(recetaEntity.get()))
			return ingredienteEntity.get();

		throw new IllegalOperationException("El ingrediente no esta asociado a la receta");
	}
	
	@Transactional
	public List<IngredienteEntity> addIngredientes(Long recetaId, List<IngredienteEntity> ingredientes) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar los ingredientes asociados a la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no se encontro");

		for (IngredienteEntity ingrediente : ingredientes) {
			Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingrediente.getId());
			if (ingredienteEntity.isEmpty())
				throw new EntityNotFoundException("El libro no se encontro");

			if (!ingredienteEntity.get().getRecetas().contains(recetaEntity.get()))
				ingredienteEntity.get().getRecetas().add(recetaEntity.get());
		}
		log.info("Finaliza proceso de reemplazar los ingredientes asociados a la receta con id = {0}", recetaId);
		return ingredientes;
	}
	
	@Transactional
	public void removeIngrediente(Long recetaId, Long ingredienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar un ingrediente de la receta con id = {0}", recetaId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro la receta");

		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el ingrediente.");

		ingredienteEntity.get().getRecetas().remove(recetaEntity.get());
		log.info("Finaliza proceso de borrar un ingrediente de la receta con id = {0}", recetaId);
	}
}
