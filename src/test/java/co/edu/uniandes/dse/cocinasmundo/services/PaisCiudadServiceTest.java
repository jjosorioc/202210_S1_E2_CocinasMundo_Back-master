package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PaisService.class, PaisCiudadService.class })

public class PaisCiudadServiceTest {

	@Autowired
	private PaisCiudadService paisCiudadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaisEntity> paisesList = new ArrayList<>();

	private List<CiudadEntity> ciudadesList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from CiudadEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			CiudadEntity ciudad = factory.manufacturePojo(CiudadEntity.class);
			entityManager.persist(ciudad);
			ciudadesList.add(ciudad);
		}
		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(entity);
			paisesList.add(entity);
			if (i == 0) {
				ciudadesList.get(i).setPais(entity);
				entity.getCiudades().add(ciudadesList.get(i));
			}
		}
	}

	@Test
	void testAddCiudad() throws EntityNotFoundException {
		PaisEntity entity = paisesList.get(0);
		CiudadEntity ciudadEntity = ciudadesList.get(1);
		CiudadEntity response = paisCiudadService.addCiudad(entity.getId(), ciudadEntity.getId());

		assertNotNull(response);
		assertEquals(ciudadEntity.getId(), response.getId());
	}

	@Test
	void testAddInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity entity = paisesList.get(0);
			paisCiudadService.addCiudad(0L, entity.getId());
		});
	}

	@Test
	void testAddCiudadInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity entity = ciudadesList.get(1);
			paisCiudadService.addCiudad(entity.getId(), 0L);
		});
	}

	@Test
	void testGetCiudades() throws EntityNotFoundException {
		List<CiudadEntity> list = paisCiudadService.getCiudades(paisesList.get(0).getId());
		assertEquals(1, list.size());
	}

	@Test
	void testGetCiudadesInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			paisCiudadService.getCiudades(0L);
		});
	}

	@Test
	void testGetCiudad() throws IllegalOperationException, EntityNotFoundException {
		PaisEntity entity = paisesList.get(0);
		CiudadEntity ciudadEntity = ciudadesList.get(0);
		CiudadEntity response = paisCiudadService.getCiudad(entity.getId(), ciudadEntity.getId());

		assertEquals(ciudadEntity.getId(), response.getId());
		assertEquals(ciudadEntity.getNombre(), response.getNombre());
		assertEquals(ciudadEntity.getPais(), response.getPais());
		assertEquals(ciudadEntity.getUbicacion(), response.getUbicacion());
		assertEquals(ciudadEntity.getRestaurantes(), response.getRestaurantes());
	}

	@Test
	void testGetCiudadInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity ciudadEntity = ciudadesList.get(0);
			paisCiudadService.getCiudad(0L, ciudadEntity.getId());
		});
	}

	@Test
	void testGetInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity entity = paisesList.get(0);
			paisCiudadService.getCiudad(entity.getId(), 0L);
		});
	}

	@Test
	void getCiudadNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			PaisEntity entity = paisesList.get(0);
			CiudadEntity ciudadEntity = ciudadesList.get(1);
			paisCiudadService.getCiudad(entity.getId(), ciudadEntity.getId());
		});
	}

	@Test
	void testReplaceCiudades() throws EntityNotFoundException {
		PaisEntity entity = paisesList.get(0);
		List<CiudadEntity> list = ciudadesList.subList(1, 3);
		paisCiudadService.replaceCiudades(entity.getId(), list);

		for (CiudadEntity c : list) {
			CiudadEntity b = entityManager.find(CiudadEntity.class, c.getId());
			assertTrue(b.getPais().equals(entity));
		}
	}

	@Test
	void testReplaceInvalidCiudades() {
		assertThrows(EntityNotFoundException.class, () -> {
			PaisEntity entity = paisesList.get(0);

			List<CiudadEntity> ciudades = new ArrayList<>();
			CiudadEntity newCiudad = factory.manufacturePojo(CiudadEntity.class);
			newCiudad.setId(0L);
			ciudades.add(newCiudad);

			paisCiudadService.replaceCiudades(entity.getId(), ciudades);
		});
	}

	@Test
	void testReplaceCiudadesInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<CiudadEntity> list = ciudadesList.subList(1, 3);
			paisCiudadService.replaceCiudades(0L, list);
		});
	}
}

