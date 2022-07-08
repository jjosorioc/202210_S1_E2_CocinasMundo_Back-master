package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ CiudadService.class, CiudadPaisService.class })

public class CiudadPaisServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CiudadPaisService ciudadPaisService;

	@Autowired
	private CiudadService ciudadService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaisEntity> paisesList = new ArrayList<>();

	private List<CiudadEntity> ciudadesList = new ArrayList<>();

	/**
	 * Config inicial de la prueba.
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
		entityManager.getEntityManager().createQuery("delete from CiudadEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
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
			}
		}
	}

	@Test
	void testReplacePais() throws EntityNotFoundException {
		CiudadEntity entity = ciudadesList.get(0);
		ciudadPaisService.replacePais(entity.getId(), paisesList.get(1).getId());
		entity = ciudadService.getCiudadId(entity.getId());

		assertEquals(entity.getPais(), paisesList.get(1));
	}

	@Test
	void testReplacePaisInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			ciudadPaisService.replacePais(0L, paisesList.get(1).getId());
		});
	}

	@Test
	void testReplaceInvalidPais() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity entity = ciudadesList.get(0);
			ciudadPaisService.replacePais(entity.getId(), 0L);
		});
	}

	@Test
	void testRemovePais() throws EntityNotFoundException {
		ciudadPaisService.removePais(ciudadesList.get(0).getId());
		CiudadEntity response = ciudadService.getCiudadId(ciudadesList.get(0).getId());
		assertNull(response.getPais());
	}

	@Test
	void testRemovePaisInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			ciudadPaisService.removePais(0L);
		});
	}
}
