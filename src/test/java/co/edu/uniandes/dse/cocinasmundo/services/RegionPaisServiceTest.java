package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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

import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.cocinasmundo.services.RegionPaisService;
import co.edu.uniandes.dse.cocinasmundo.services.PaisService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Region - Paises
 *
 * @author Tomas Angel
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ RegionPaisService.class, PaisService.class })
class RegionPaisServiceTest {
	
	@Autowired
	private RegionPaisService regionPaisService;

	@Autowired
	private PaisService paisService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private RegionEntity region = new RegionEntity();
	private List<PaisEntity> paisList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from RegionEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {

		region = factory.manufacturePojo(RegionEntity.class);
		entityManager.persist(region);

		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entity.getRegiones().add(region);
			entityManager.persist(entity);
			paisList.add(entity);
			region.getPaises().add(entity);
		}
	}
	
	/**
	 * Prueba para asociar un pais a una region.
	 *
	 */
	@Test
	void testAddPais() throws EntityNotFoundException, IllegalOperationException {
		PaisEntity newPais = factory.manufacturePojo(PaisEntity.class);
		paisService.createPais(newPais);

		PaisEntity paisEntity = regionPaisService.addPais(region.getId(), newPais.getId());
		assertNotNull(paisEntity);

		assertEquals(paisEntity.getId(), newPais.getId());
		assertEquals(paisEntity.getNombre(), newPais.getNombre());

		PaisEntity lastPais = regionPaisService.getPais(region.getId(), newPais.getId());

		assertEquals(lastPais.getId(), newPais.getId());
		assertEquals(lastPais.getNombre(), newPais.getNombre());
	}
	
	/**
	 * Prueba para asociar un pais a una region que no existe.
	 *
	 */

	@Test
	void testAddPaisInvalidRegion() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity newPais = factory.manufacturePojo(PaisEntity.class);
			paisService.createPais(newPais);
			regionPaisService.addPais(0L, newPais.getId());
		});
	}

	/**
	 * Prueba para asociar un pais que no existe a una region.
	 *
	 */
	@Test
	void testAddInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			regionPaisService.addPais(region.getId(), 0L);
		});
	}
	
	/**
	 * Prueba para consultar la lista de paises de una region.
	 */
	@Test
	void testGetPaises() throws EntityNotFoundException {
		List<PaisEntity>paisEntities = regionPaisService.getPaises(region.getId());

		assertEquals(paisList.size(), paisEntities.size());

		for (int i = 0; i < paisList.size(); i++) {
			assertTrue(paisEntities.contains(paisList.get(0)));
		}
	}

	/**
	 * Prueba para consultar la lista de paises de una region que no existe.
	 */
	@Test
	void testGetPaisesInvalidRegion() {
		assertThrows(EntityNotFoundException.class, () -> {
			regionPaisService.getPaises(0L);
		});
	}
	
	/**
	 * Prueba para consultar un pais de una region.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetPais() throws EntityNotFoundException, IllegalOperationException {
		PaisEntity paisEntity = paisList.get(0);
		PaisEntity pais = regionPaisService.getPais(region.getId(), paisEntity.getId());
		assertNotNull(pais);

		assertEquals(paisEntity.getId(), pais.getId());
		assertEquals(paisEntity.getNombre(), pais.getNombre());
	}

	/**
	 * Prueba para consultar un pais de una region que no existe.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetPaisInvalidRegion() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity paisEntity = paisList.get(0);
			regionPaisService.getPais(0L, paisEntity.getId());
		});
	}
	
	/**
	 * Prueba para consultar un pais que no existe de una region.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			regionPaisService.getPais(region.getId(), 0L);
		});
	}

	/**
	 * Prueba para consultar un pais que no está asociado a una region.
	 *
	 * @throws throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testGetPaisNotAssociatedRegion() {
		assertThrows(IllegalOperationException.class, () -> {
			RegionEntity regionEntity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(regionEntity);

			PaisEntity paisEntity = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(paisEntity);

			regionPaisService.getPais(regionEntity.getId(), paisEntity.getId());
		});
	}
	
	/**
	 * Prueba para actualizar los paises de una region.
	 *
	 * @throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testReplacePaises() throws EntityNotFoundException, IllegalOperationException {
		List<PaisEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entity.getRegiones().add(region);
			paisService.createPais(entity);
			nuevaLista.add(entity);
		}
		regionPaisService.addPaises(region.getId(), nuevaLista);
		List<PaisEntity> paisesEntities = regionPaisService.getPaises(region.getId());
		for (PaisEntity aNuevaLista : nuevaLista) {
			assertTrue(paisesEntities.contains(aNuevaLista));
		}
	}
	
	/**
	 * Prueba para actualizar los paises de una region.
	 *
	 * @throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testReplacePaises2() throws EntityNotFoundException, IllegalOperationException {
		List<PaisEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			paisService.createPais(entity);
			nuevaLista.add(entity);
		}
		regionPaisService.addPaises(region.getId(), nuevaLista);
		List<PaisEntity> paisEntities = regionPaisService.getPaises(region.getId());
		for (PaisEntity aNuevaLista : nuevaLista) {
			assertTrue(paisEntities.contains(aNuevaLista));
		}
	}
	
	/**
	 * Prueba para actualizar los paises de una region que no existe.
	 *
	 * @throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testReplacePaisesInvalidRegion() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PaisEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
				paisService.createPais(entity);
				nuevaLista.add(entity);
			}
			regionPaisService.addPaises(0L, nuevaLista);
		});
	}

	/**
	 * Prueba para actualizar los paises que no existen de una region.
	 *
	 * @throws EntityNotFoundException, IllegalOperationException
	 */
	@Test
	void testReplaceInvalidPaises() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PaisEntity> nuevaLista = new ArrayList<>();
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			regionPaisService.addPaises(region.getId(), nuevaLista);
		});
	}
	
	/**
	 * Prueba desasociar un pais con una region.
	 *
	 */
	@Test
	void testRemovePais() throws EntityNotFoundException {
		for (PaisEntity pais : paisList) {
			regionPaisService.removePais(region.getId(), pais.getId());
		}
		assertTrue(regionPaisService.getPaises(region.getId()).isEmpty());
	}

	/**
	 * Prueba desasociar un pais con una region que no existe.
	 *
	 */
	@Test
	void testRemovePaisInvalidRegion() {
		assertThrows(EntityNotFoundException.class, () -> {
			for (PaisEntity pais : paisList) {
				regionPaisService.removePais(0L, pais.getId());
			}
		});
	}
	
	/**
	 * Prueba desasociar un pais que no existe con una region.
	 *
	 */
	@Test
	void testRemoveInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			regionPaisService.removePais(region.getId(), 0L);
		});
	}

}
