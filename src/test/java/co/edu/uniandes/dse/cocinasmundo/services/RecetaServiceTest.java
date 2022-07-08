package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(RecetaService.class)
class RecetaServiceTest {
	
	@Autowired
	private RecetaService recetaService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<RecetaEntity> listaRecetas = new ArrayList<RecetaEntity>();
	
	/**
	 * Métodos de inicialización
	 * -------------------------------------------------------------------------------------------------
	 */
	
	
	/**
	 * Configuración inicial
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	/**
	 * Limpia las tablas
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from RecetaEntity").executeUpdate();
	}
	
	/**
	 * Inserta los datos pa las pruebas
	 */
	private void insertData() {
		for(int i=0; i<3; i++) {
			RecetaEntity recetaEntity = factory.manufacturePojo(RecetaEntity.class);
			entityManager.persist(recetaEntity);
			listaRecetas.add(recetaEntity);
		}
	}
	/**
	 * -------------------------------------------------------------------------------------------------
	 */

	/**
	 * Prueba para obtener las recetas
	 */
	@Test
	void testGetRecetas() {
		List<RecetaEntity> list = recetaService.getRecetas();
        assertEquals(list.size(), listaRecetas.size());
	}

	/**
	 * Prueba para crear la receta
	 */
	@Test
	void testCreateReceta() {
		RecetaEntity nuevaReceta = factory.manufacturePojo(RecetaEntity.class);
		RecetaEntity recetaCreada = recetaService.createReceta(nuevaReceta);
		RecetaEntity busqueda = entityManager.find(RecetaEntity.class, recetaCreada.getId());
		
		assertEquals(nuevaReceta.getId(), busqueda.getId());
		assertEquals(nuevaReceta.getNombre(), busqueda.getNombre());
		assertEquals(nuevaReceta.getDescripcion(), busqueda.getDescripcion());
	}
	
	/**
	 * Prueba para obtener la receta
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testGetRecetaId() throws EntityNotFoundException {
		RecetaEntity nuevaReceta = factory.manufacturePojo(RecetaEntity.class);
		RecetaEntity recetaCreada = recetaService.createReceta(nuevaReceta);
		RecetaEntity busqueda = entityManager.find(RecetaEntity.class, recetaCreada.getId());
		
		busqueda = recetaService.getRecetaId(recetaCreada.getId());
		assertEquals(nuevaReceta.getId(), busqueda.getId());
		assertEquals(nuevaReceta.getNombre(), busqueda.getNombre());
		assertEquals(nuevaReceta.getDescripcion(), busqueda.getDescripcion());
	}

	/**
	 * Prueba para actualizar la Receta
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testUpdateReceta() throws EntityNotFoundException {
		RecetaEntity recetaEntity = listaRecetas.get(0);
		RecetaEntity pojoEntity = factory.manufacturePojo(RecetaEntity.class);

		pojoEntity.setId(recetaEntity.getId());

		recetaService.updateReceta(recetaEntity.getId(), pojoEntity);

		RecetaEntity response = entityManager.find(RecetaEntity.class, recetaEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getDescripcion(), response.getDescripcion());
		assertEquals(pojoEntity.getPreparacion(), response.getPreparacion());
	}

	/**
	 * Prueba para borrar la Receta
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testDeleteReceta() throws EntityNotFoundException {
		RecetaEntity recetaEntity = listaRecetas.get(0);
		recetaService.deleteReceta(recetaEntity.getId());
		RecetaEntity deleted = entityManager.find(RecetaEntity.class, recetaEntity.getId());
		assertNull(deleted);
	}

}