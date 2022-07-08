package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.*;

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

import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(IngredienteService.class)
class IngredienteServiceTest {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<IngredienteEntity> listaIngredientes = new ArrayList<IngredienteEntity>();
	
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
		entityManager.getEntityManager().createQuery("delete from IngredienteEntity").executeUpdate();
	}
	
	/**
	 * Inserta los datos pa las pruebas
	 */
	private void insertData() {
		for(int i=0; i<3; i++) {
			IngredienteEntity ingredienteEntity = factory.manufacturePojo(IngredienteEntity.class);
			entityManager.persist(ingredienteEntity);
			listaIngredientes.add(ingredienteEntity);
		}
	}
	/**
	 * -------------------------------------------------------------------------------------------------
	 */

	/**
	 * Prueba para obtener los ingredientes
	 */
	@Test
	void testGetIngredientes() {
		List<IngredienteEntity> list = ingredienteService.getIngredientes();
        assertEquals(list.size(), listaIngredientes.size());
	}

	/**
	 * Prueba para crear el ingrediente
	 */
	@Test
	void testCreateIngrediente() {
		IngredienteEntity nuevoIngrediente = factory.manufacturePojo(IngredienteEntity.class);
		IngredienteEntity ingredienteCreado = ingredienteService.createIngrediente(nuevoIngrediente);
		IngredienteEntity busqueda = entityManager.find(IngredienteEntity.class, ingredienteCreado.getId());
		
		assertEquals(nuevoIngrediente.getId(), busqueda.getId());
		assertEquals(nuevoIngrediente.getNombre(), busqueda.getNombre());
		assertEquals(nuevoIngrediente.getDescripcion(), busqueda.getDescripcion());
	}
	
	/**
	 * Prueba para obtener el ingrediente
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testGetIngredienteId() throws EntityNotFoundException {
		IngredienteEntity nuevoIngrediente = factory.manufacturePojo(IngredienteEntity.class);
		IngredienteEntity ingredienteCreado = ingredienteService.createIngrediente(nuevoIngrediente);
		IngredienteEntity busqueda;
		
		busqueda = ingredienteService.getIngredienteId(ingredienteCreado.getId());
		assertEquals(nuevoIngrediente.getId(), busqueda.getId());
		assertEquals(nuevoIngrediente.getNombre(), busqueda.getNombre());
		assertEquals(nuevoIngrediente.getDescripcion(), busqueda.getDescripcion());
	}

	/**
	 * Prueba para actualizar el Ingrediente
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testUpdateIngrediente() throws EntityNotFoundException {
		IngredienteEntity ingredienteEntity = listaIngredientes.get(0);
		IngredienteEntity pojoEntity = factory.manufacturePojo(IngredienteEntity.class);

		pojoEntity.setId(ingredienteEntity.getId());

		ingredienteService.updateIngrediente(ingredienteEntity.getId(), pojoEntity);

		IngredienteEntity response = entityManager.find(IngredienteEntity.class, ingredienteEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getDescripcion(), response.getDescripcion());
		assertEquals(pojoEntity.getHistoria(), response.getHistoria());
		assertEquals(pojoEntity.getCategoria(), response.getCategoria());
	}

	@Test
	void testDeleteIngrediente() throws EntityNotFoundException {
		IngredienteEntity ingredienteEntity = listaIngredientes.get(0);
		ingredienteService.deleteIngrediente(ingredienteEntity.getId());
		IngredienteEntity deleted = entityManager.find(IngredienteEntity.class, ingredienteEntity.getId());
		assertNull(deleted);
	}

}
