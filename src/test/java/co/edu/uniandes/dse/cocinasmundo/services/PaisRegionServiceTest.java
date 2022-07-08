package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.PaisRegionService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Pais - Region
 *
 * @author Tomas Angel
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PaisRegionService.class)

class PaisRegionServiceTest {

	@Autowired
	private PaisRegionService paisRegionService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PaisEntity pais = new PaisEntity();
	private List<RegionEntity> regionList = new ArrayList<>();

	
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from RegionEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {

		pais = factory.manufacturePojo(PaisEntity.class);
		entityManager.persist(pais);

		for (int i = 0; i < 3; i++) {
			RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(entity);
			entity.getPaises().add(pais);
			regionList.add(entity);
			pais.getRegiones().add(entity);	
		}
	}
	
	/**
	 * Prueba para asociar una region a un pais.
	 *
	 */
	@Test
	void testAddRegion() throws EntityNotFoundException, IllegalOperationException {
		PaisEntity newPais = factory.manufacturePojo(PaisEntity.class);
		entityManager.persist(newPais);
		
		RegionEntity region = factory.manufacturePojo(RegionEntity.class);
		entityManager.persist(region);
		
		paisRegionService.addRegion(newPais.getId(), region.getId());
		
		RegionEntity lastRegion = paisRegionService.getRegion(newPais.getId(), region.getId());
		assertEquals(region.getId(), lastRegion.getId());
		assertEquals(region.getNombre(), lastRegion.getNombre());
	}
	
	/**
	 * Prueba para asociar un region que no existe a un pais.
	 *
	 */
	@Test
	void testAddInvalidRegion() {
		assertThrows(EntityNotFoundException.class, ()->{
			PaisEntity newPais = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(newPais);
			paisRegionService.addRegion(newPais.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para asociar una region a un pais que no existe.
	 *
	 */
	@Test
	void testAddRegionInvalidPais() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			RegionEntity region = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(region);
			paisRegionService.addRegion(0L, region.getId());
		});
	}
	
	/**
	 * Prueba para consultar la lista de regiones de un pais.
	 */
	@Test
	void testGetRegiones() throws EntityNotFoundException {
		List<RegionEntity> regionEntities = paisRegionService.getRegiones(pais.getId());

		assertEquals(regionList.size(), regionEntities.size());

		for (int i = 0; i < regionList.size(); i++) {
			assertTrue(regionEntities.contains(regionList.get(0)));
		}
	}
	
	/**
	 * Prueba para consultar la lista de regiones de un pais que no existe.
	 */
	@Test
	void testGetRegionesInvalidPais(){
		assertThrows(EntityNotFoundException.class, ()->{
			paisRegionService.getRegiones(0L);
		});
	}
	
	/**
	 * Prueba para consultar una region de un pais.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetRegion() throws EntityNotFoundException, IllegalOperationException {
		RegionEntity regionEntity = regionList.get(0);
		RegionEntity region = paisRegionService.getRegion(pais.getId(), regionEntity.getId());
		assertNotNull(region);

		assertEquals(regionEntity.getId(), region.getId());
		assertEquals(regionEntity.getNombre(), region.getNombre());
	}
	
	/**
	 * Prueba para consultar un autor que no existe de un libro.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetInvalidAuthor()  {
		assertThrows(EntityNotFoundException.class, ()->{
			paisRegionService.getRegion(pais.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para consultar una region de un pais que no existe.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetRegionInvalidPais() {
		assertThrows(EntityNotFoundException.class, ()->{
			RegionEntity regionEntity = regionList.get(0);
			paisRegionService.getRegion(0L, regionEntity.getId());
		});
	}
	
	/**
	 * Prueba para obtener una region no asociada a un pais.
	 *
	 */
	@Test
	void testGetNotAssociatedRegion() {
		assertThrows(IllegalOperationException.class, ()->{
			PaisEntity newPais = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(newPais);
			RegionEntity region = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(region);
			paisRegionService.getRegion(newPais.getId(), region.getId());
		});
	}
	
	/**
	 * Prueba para actualizar las regiones de un pais.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceRegiones() throws EntityNotFoundException {
		List<RegionEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(entity);
			pais.getRegiones().add(entity);
			nuevaLista.add(entity);
		}
		paisRegionService.replaceRegiones(pais.getId(), nuevaLista);
		
		List<RegionEntity> regionEntities = paisRegionService.getRegiones(pais.getId());
		for (RegionEntity aNuevaLista : nuevaLista) {
			assertTrue(regionEntities.contains(aNuevaLista));
		}
	}
	
	/**
	 * Prueba para actualizar las regiones de un pais.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceRegiones2() throws EntityNotFoundException {
		List<RegionEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		paisRegionService.replaceRegiones(pais.getId(), nuevaLista);
		
		List<RegionEntity> authorEntities = paisRegionService.getRegiones(pais.getId());
		for (RegionEntity aNuevaLista : nuevaLista) {
			assertTrue(authorEntities.contains(aNuevaLista));
		}
	}
	
	/**
	 * Prueba para actualizar las regiones de un pais que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceRegionesInvalidPais(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<RegionEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
				entity.getPaises().add(pais);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paisRegionService.replaceRegiones(0L, nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar las regiones que no existen de un pais.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceInvalidRegiones() {
		assertThrows(EntityNotFoundException.class, ()->{
			List<RegionEntity> nuevaLista = new ArrayList<>();
			RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			paisRegionService.replaceRegiones(pais.getId(), nuevaLista);
		});
	}
	
	/**
	 * Prueba para actualizar una region de un pais que no existe.
	 *
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceAuthorsInvalidAuthor(){
		assertThrows(EntityNotFoundException.class, ()->{
			List<RegionEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				RegionEntity entity = factory.manufacturePojo(RegionEntity.class);
				entity.getPaises().add(pais);		
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			paisRegionService.replaceRegiones(0L, nuevaLista);
		});
	}

	/**
	 * Prueba desasociar una region con un pais.
	 *
	 */
	@Test
	void testRemoveRegion() throws EntityNotFoundException {
		for (RegionEntity region : regionList) {
			paisRegionService.removeRegion(pais.getId(), region.getId());
		}
		assertTrue(paisRegionService.getRegiones(pais.getId()).isEmpty());
	}
	
	/**
	 * Prueba desasociar una region que no existe con un pais.
	 *
	 */
	@Test
	void testRemoveInvalidRegoion(){
		assertThrows(EntityNotFoundException.class, ()->{
			paisRegionService.removeRegion(pais.getId(), 0L);
		});
	}
	
	/**
	 * Prueba desasociar una region con un pais que no existe.
	 *
	 */
	@Test
	void testRemoveRegionInvalidPais(){
		assertThrows(EntityNotFoundException.class, ()->{
			paisRegionService.removeRegion(0L, regionList.get(0).getId());
		});
	}

}
