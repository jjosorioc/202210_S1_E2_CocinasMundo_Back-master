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

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ PlatoService.class, PlatoCulturaCulinariaService.class })
public class PlatoCulturaCulinariaServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PlatoCulturaCulinariaService platoCulturaCulinariaService;

	@Autowired
	private PlatoService platoService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<CulturaCulinariaEntity> culturasList = new ArrayList<>();

	private List<PlatoEntity> platosList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from PlatoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			PlatoEntity plato = factory.manufacturePojo(PlatoEntity.class);
			entityManager.persist(plato);
			platosList.add(plato);
		}
		for (int i = 0; i < 3; i++) {
			CulturaCulinariaEntity entity = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entityManager.persist(entity);
			culturasList.add(entity);
			if (i == 0) {
				platosList.get(i).setCulturaCulinaria(entity);
			}
		}
	}

	@Test
	void testReplaceCulturaCulinaria() throws EntityNotFoundException {
		PlatoEntity entity = platosList.get(0);
		platoCulturaCulinariaService.replaceCulturaCulinaria(entity.getId(), culturasList.get(1).getId());
		entity = platoService.getPlatoByID(entity.getId());

		assertEquals(entity.getCulturaCulinaria(), culturasList.get(1));
	}

	@Test
	void testReplaceCulturaInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoCulturaCulinariaService.replaceCulturaCulinaria(0L, culturasList.get(1).getId());
		});
	}

	@Test
	void testReplaceInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity entity = platosList.get(0);
			platoCulturaCulinariaService.replaceCulturaCulinaria(entity.getId(), 0L);
		});
	}

	@Test
	void testRemoveCultura() throws EntityNotFoundException {
		platoCulturaCulinariaService.removeCulturaCulinaria(platosList.get(0).getId());
		PlatoEntity response = platoService.getPlatoByID(platosList.get(0).getId());
		assertNull(response.getCulturaCulinaria());
	}

	@Test
	void testRemoveCulturaInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoCulturaCulinariaService.removeCulturaCulinaria(0L);
		});
	}
}
