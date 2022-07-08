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
import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ RegionService.class, RegionCulturaCulinariaService.class })
class RegionCulturaCulinariaServiceTest {

	@Autowired
	private RegionCulturaCulinariaService regionCulturaCulinariaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<RegionEntity> regionsList = new ArrayList<>();
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
		entityManager.getEntityManager().createQuery("delete from RegionEntity").executeUpdate();
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
			RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(entity);
			regionsList.add(entity);
			if (i == 0) {
				culturasCulinariasList.get(i).setRegion(entity);
				entity.getCulturasCulinarias().add(culturasCulinariasList.get(i));
			}
		}
	}

	/**
	 * Prueba para asociar un CulturaCulinaria existente a un Region.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddCulturaCulinaria() throws EntityNotFoundException , IllegalOperationException {
		RegionEntity entity = regionsList.get(0);
		CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
		CulturaCulinariaEntity response = regionCulturaCulinariaService.addCulturaCulinaria(culturaCulinariaEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(culturaCulinariaEntity.getId(), response.getId());
	}
	
	/**
	 * Prueba para asociar un CulturaCulinaria que no existe a un Region.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddInvalidCulturaCulinaria() {
		assertThrows(EntityNotFoundException.class, ()->{
			RegionEntity entity = regionsList.get(0);
			regionCulturaCulinariaService.addCulturaCulinaria(0L, entity.getId());
		});
	}
	
	/**
	 * Prueba para asociar un CulturaCulinaria a una Region que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddCulturaCulinariaInvalidRegion() {
		assertThrows(EntityNotFoundException.class, ()->{
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
			regionCulturaCulinariaService.addCulturaCulinaria(culturaCulinariaEntity.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar un CulturaCulinaria que ya tiene una región asignada.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testAddCulturaCulinariaConRegion() {
		assertThrows(IllegalOperationException.class, ()->{
			RegionEntity entity = regionsList.get(0);
			CulturaCulinariaEntity cultura = culturasCulinariasList.get(1);
			regionCulturaCulinariaService.addCulturaCulinaria(cultura.getId(), entity.getId());
			
			RegionEntity entity2 = regionsList.get(1);
			regionCulturaCulinariaService.addCulturaCulinaria(cultura.getId(), entity2.getId());
		});
	}

	/**
	 * Prueba para obtener una colección de instancias de CulturaCulinarias asociadas a una
	 * instancia Region.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetCulturaCulinarias() throws EntityNotFoundException {
		List<CulturaCulinariaEntity> list = regionCulturaCulinariaService.getCulturasCulinarias(regionsList.get(0).getId());
		assertEquals(1, list.size());
	}
	
	/**
	 * Prueba para obtener una colección de instancias de CulturaCulinarias asociadas a una
	 * instancia Region que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */

	@Test
	void testGetCulturaCulinariasInvalidRegion() {
		assertThrows(EntityNotFoundException.class,()->{
			regionCulturaCulinariaService.getCulturasCulinarias(0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de CulturaCulinaria asociada a una instancia Region.
	 * 
	 * @throws IllegalOperationException
	 * @throws EntityNotFoundException
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	void testGetCulturaCulinaria() throws EntityNotFoundException {
		RegionEntity entity = regionsList.get(0);
		CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(0);
		CulturaCulinariaEntity response = regionCulturaCulinariaService.getCulturaCulinaria(entity.getId(), culturaCulinariaEntity.getId());

		assertEquals(culturaCulinariaEntity.getId(), response.getId());
		assertEquals(culturaCulinariaEntity.getNombreCultura(), response.getNombreCultura());
		assertEquals(culturaCulinariaEntity.getDescripcionCultura(), response.getDescripcionCultura());
		assertEquals(culturaCulinariaEntity.getCalificacionCultura(), response.getCalificacionCultura());
	}
	
	/**
	 * Prueba para obtener una instancia de CulturaCulinaria asociada a una instancia Region que no existe.
	 * 
	 * @throws EntityNotFoundException
	 *
	 */
	@Test
	void testGetCulturaCulinariaInvalidRegion()  {
		assertThrows(EntityNotFoundException.class, ()->{
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(0);
			regionCulturaCulinariaService.getCulturaCulinaria(0L, culturaCulinariaEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una instancia de CulturaCulinaria que no existe asociada a una instancia Region.
	 * 
	 * @throws EntityNotFoundException
	 * 
	 */
	@Test
	void testGetInvalidCulturaCulinaria()  {
		assertThrows(EntityNotFoundException.class, ()->{
			RegionEntity entity = regionsList.get(0);
			regionCulturaCulinariaService.getCulturaCulinaria(entity.getId(), 0L);
		});
	}

	/**
	 * Prueba para obtener una instancia de CulturaCulinarias asociada a una instancia Region
	 * que no le pertenece.
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	public void getCulturaCulinariaNoAsociadoTest() {
		assertThrows(EntityNotFoundException.class, () -> {
			RegionEntity entity = regionsList.get(0);
			CulturaCulinariaEntity culturaCulinariaEntity = culturasCulinariasList.get(1);
			regionCulturaCulinariaService.getCulturaCulinaria(entity.getId(), culturaCulinariaEntity.getId());
		});
	}

	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Region.
	 */
	@Test
	void testReplaceCulturaCulinarias() throws EntityNotFoundException {
		RegionEntity entity = regionsList.get(0);
		List<CulturaCulinariaEntity> list = culturasCulinariasList.subList(1, 3);
		regionCulturaCulinariaService.replaceCulturasCulinarias(entity.getId(), list);

		for (CulturaCulinariaEntity culturaCulinaria : list) {
			CulturaCulinariaEntity b = entityManager.find(CulturaCulinariaEntity.class, culturaCulinaria.getId());
			assertTrue(b.getRegion().equals(entity));
		}
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias que no existen asociadas a una instancia de
	 * Region.
	 */
	@Test
	void testReplaceInvalidCulturaCulinarias() {
		assertThrows(EntityNotFoundException.class, ()->{
			RegionEntity entity = regionsList.get(0);
			
			List<CulturaCulinariaEntity> culturaCulinarias = new ArrayList<>();
			CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
			newCulturaCulinaria.setId(0L);
			culturaCulinarias.add(newCulturaCulinaria);
			
			regionCulturaCulinariaService.replaceCulturasCulinarias(entity.getId(), culturaCulinarias);
		});
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Region que no existe.
	 */
	@Test
	void testReplaceCulturaCulinariasInvalidRegion() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			List<CulturaCulinariaEntity> list = culturasCulinariasList.subList(1, 3);
			regionCulturaCulinariaService.replaceCulturasCulinarias(0L, list);
		});
	}
}