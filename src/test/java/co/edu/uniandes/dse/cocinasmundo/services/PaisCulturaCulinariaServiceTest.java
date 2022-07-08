package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PaisService.class, PaisCulturaCulinariaService.class })
class PaisCulturaCulinariaServiceTest {

	@Autowired
	private PaisCulturaCulinariaService paisCulturaCulinariaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaisEntity> paissList = new ArrayList<>();
	private List<CulturaCulinariaEntity> culturasCulinariasList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			CulturaCulinariaEntity culturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entityManager.persist(culturaCulinaria);
			culturasCulinariasList.add(culturaCulinaria);
		}

		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(entity);
			paissList.add(entity);
			if (i == 0) {
				culturasCulinariasList.get(i).setPais(entity);
				entity.getCulturasCulinarias().add(culturasCulinariasList.get(i));
			}
		}
	}

	/**
	 * Prueba para asociar un CulturaCulinaria existente a un Pais.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddCulturaCulinaria() throws EntityNotFoundException , IllegalOperationException {
		PaisEntity entity = paissList.get(0);
		CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
		CulturaCulinariaEntity response = paisCulturaCulinariaService.addCulturaCulinaria(culturaCulinariaEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(culturaCulinariaEntity.getId(), response.getId());
	}
	
	/**
	 * Prueba para asociar un CulturaCulinaria que no existe a un Pais.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddInvalidCulturaCulinaria() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaisEntity entity = paissList.get(0);
			paisCulturaCulinariaService.addCulturaCulinaria(0L, entity.getId());
		});
	}
	
	/**
	 * Prueba para asociar un CulturaCulinaria a una Pais que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddCulturaCulinariaInvalidPais() {
		assertThrows(EntityNotFoundException.class, ()->{
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
			paisCulturaCulinariaService.addCulturaCulinaria(culturaCulinariaEntity.getId(), 0L);
		});
	}

	/**
	 * Prueba para obtener una colección de instancias de CulturaCulinarias asociadas a una
	 * instancia Pais.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetCulturaCulinarias() throws EntityNotFoundException {
		List<CulturaCulinariaEntity> list = paisCulturaCulinariaService.getCulturasCulinarias(paissList.get(0).getId());
		assertEquals(1, list.size());
	}
	
	/**
	 * Prueba para obtener una colección de instancias de CulturaCulinarias asociadas a una
	 * instancia Pais que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetCulturaCulinariasInvalidPais() {
		assertThrows(EntityNotFoundException.class,()->{
			paisCulturaCulinariaService.getCulturasCulinarias(0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de CulturaCulinaria asociada a una instancia Pais.
	 * 
	 * @throws IllegalOperationException
	 * @throws EntityNotFoundException
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	void testGetCulturaCulinaria() throws EntityNotFoundException {
		PaisEntity entity = paissList.get(0);
		CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(0);
		CulturaCulinariaEntity response = paisCulturaCulinariaService.getCulturaCulinaria(entity.getId(), culturaCulinariaEntity.getId());

		assertEquals(culturaCulinariaEntity.getId(), response.getId());
		assertEquals(culturaCulinariaEntity.getNombreCultura(), response.getNombreCultura());
		assertEquals(culturaCulinariaEntity.getDescripcionCultura(), response.getDescripcionCultura());
		assertEquals(culturaCulinariaEntity.getCalificacionCultura(), response.getCalificacionCultura());
	}
	
	/**
	 * Prueba para obtener una instancia de CulturaCulinaria asociada a una instancia Pais que no existe.
	 * 
	 * @throws EntityNotFoundException
	 *
	 */
	@Test
	void testGetCulturaCulinariaInvalidPais()  {
		assertThrows(EntityNotFoundException.class, ()->{
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(0);
			paisCulturaCulinariaService.getCulturaCulinaria(0L, culturaCulinariaEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una instancia de CulturaCulinaria que no existe asociada a una instancia Pais.
	 * 
	 * @throws EntityNotFoundException
	 * 
	 */
	@Test
	void testGetInvalidCulturaCulinaria()  {
		assertThrows(EntityNotFoundException.class, ()->{
			PaisEntity entity = paissList.get(0);
			paisCulturaCulinariaService.getCulturaCulinaria(entity.getId(), 0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de CulturaCulinarias asociada a una instancia Pais
	 * que no le pertenece.
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	public void getCulturaCulinariaNoAsociadoTest() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity entity = paissList.get(0);
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
			paisCulturaCulinariaService.getCulturaCulinaria(entity.getId(), culturaCulinariaEntity.getId());
		});
	}

	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Pais.
	 */
	@Test
	void testReplaceCulturaCulinarias() throws EntityNotFoundException {
		PaisEntity entity = paissList.get(0);
		List<CulturaCulinariaEntity> list = culturasCulinariasList.subList(1, 3);
		paisCulturaCulinariaService.replaceCulturasCulinarias(entity.getId(), list);

		for (CulturaCulinariaEntity culturaCulinaria : list) {
			CulturaCulinariaEntity b = entityManager.find(CulturaCulinariaEntity.class, culturaCulinaria.getId());
			assertTrue(b.getPais().equals(entity));
		}
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias que no existen asociadas a una instancia de
	 * Pais.
	 */
	@Test
	void testReplaceInvalidCulturaCulinarias() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaisEntity entity = paissList.get(0);
			
			List<CulturaCulinariaEntity> culturaCulinarias = new ArrayList<>();
			CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
			newCulturaCulinaria.setId(0L);
			culturaCulinarias.add(newCulturaCulinaria);
			
			paisCulturaCulinariaService.replaceCulturasCulinarias(entity.getId(), culturaCulinarias);
		});
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Pais que no existe.
	 */
	@Test
	void testReplaceCulturaCulinariasInvalidPais() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<CulturaCulinariaEntity> list = culturasCulinariasList.subList(1, 3);
			paisCulturaCulinariaService.replaceCulturasCulinarias(0L, list);
		});
	}
}