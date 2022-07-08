package co.edu.uniandes.dse.cocinasmundo.services;

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

@Slf4j
@Service
public class IngredienteRecetaService {


	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private RecetaRepository recetaRepository;
	
	@Transactional
	public RecetaEntity addReceta(Long ingredienteId, Long recetaId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociarle una receta a un ingrediente con id = {0}", ingredienteId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no se encontro");

		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("El ingrediente no se encontro");

		ingredienteEntity.get().getRecetas().add(recetaEntity.get());
		log.info("Termina proceso de asociarle una receta a un ingrediente con id = {0}", ingredienteId);
		return recetaEntity.get();
	}
	
	@Transactional
	public List<RecetaEntity> getRecetas(Long ingredienteId) throws EntityNotFoundException {
		log.info("Inicia proceso de consultar todas las recetaes con un ingrediente con id = {0}", ingredienteId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("No se encontro el ingrediente");
		log.info("Finaliza proceso de consultar todas las recetaes de un ingrediente con id = {0}", ingredienteId);
		return ingredienteEntity.get().getRecetas();
	}
	
	@Transactional
	public RecetaEntity getReceta(Long ingredienteId, Long recetaId)
			throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de consultar la receta del ingrediente con id = {0}", ingredienteId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);

		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no se encontro");

		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("El ingrediente no se encontro");
		log.info("Termina proceso de consultar la receta del ingrediente con id = {0}", ingredienteId);
		if (ingredienteEntity.get().getRecetas().contains(recetaEntity.get()))
			return recetaEntity.get();

		throw new IllegalOperationException("The author is not associated to the book");
	}
	
	@Transactional
	public List<RecetaEntity> replaceRecetas(Long ingredienteId, List<RecetaEntity> list) throws EntityNotFoundException {
		log.info("Inicia proceso de reemplazar las recetaes del ingrediente con id = {0}", ingredienteId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);
		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("El ingrediente no se encontro");

		for (RecetaEntity receta : list) {
			Optional<RecetaEntity> recetaEntity = recetaRepository.findById(receta.getId());
			if (recetaEntity.isEmpty())
				throw new EntityNotFoundException("La receta no se encontro");

			if (!ingredienteEntity.get().getRecetas().contains(recetaEntity.get()))
				ingredienteEntity.get().getRecetas().add(recetaEntity.get());
		}
		log.info("Termina proceso de reemplazar las recetaes del ingrediente con id = {0}", ingredienteId);
		return getRecetas(ingredienteId);
	}
	
	@Transactional
	public void removeReceta(Long ingredienteId, Long recetaId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar una receta del ingrediente con id = {0}", ingredienteId);
		Optional<RecetaEntity> recetaEntity = recetaRepository.findById(recetaId);
		Optional<IngredienteEntity> ingredienteEntity = ingredienteRepository.findById(ingredienteId);

		if (recetaEntity.isEmpty())
			throw new EntityNotFoundException("La receta no se encontro");

		if (ingredienteEntity.isEmpty())
			throw new EntityNotFoundException("El ingrediente no se encontro");

		ingredienteEntity.get().getRecetas().remove(recetaEntity.get());

		log.info("Termina proceso de borrar una receta del ingrediente con id = {0}", ingredienteId);
	}
	
}
