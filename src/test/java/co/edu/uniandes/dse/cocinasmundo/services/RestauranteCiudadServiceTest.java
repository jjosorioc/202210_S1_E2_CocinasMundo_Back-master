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
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ RestauranteService.class, RestauranteCiudadService.class })

public class RestauranteCiudadServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RestauranteCiudadService restauranteCiudadService;

	@Autowired
	private RestauranteService restauranteService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<RestauranteEntity> restaurantesList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from RestauranteEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restaurante);
			restaurantesList.add(restaurante);
		}
		for (int i = 0; i < 3; i++) {
			CiudadEntity entity = factory.manufacturePojo(CiudadEntity.class);
			entityManager.persist(entity);
			ciudadesList.add(entity);
			if (i == 0) {
				restaurantesList.get(i).setCiudad(entity);
			}
		}
	}

	@Test
	void testReplaceCiudad() throws EntityNotFoundException {
		RestauranteEntity entity = restaurantesList.get(0);
		restauranteCiudadService.replaceCiudad(entity.getId(), ciudadesList.get(1).getId());
		entity = restauranteService.getRestauranteId(entity.getId());

		assertEquals(entity.getCiudad(), ciudadesList.get(1));
	}

	@Test
	void testReplaceCiudadInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			restauranteCiudadService.replaceCiudad(0L, ciudadesList.get(1).getId());
		});
	}

	@Test
	void testReplaceInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity entity = restaurantesList.get(0);
			restauranteCiudadService.replaceCiudad(entity.getId(), 0L);
		});
	}

	@Test
	void testRemoveCiudad() throws EntityNotFoundException {
		restauranteCiudadService.removeCiudad(restaurantesList.get(0).getId());
		RestauranteEntity response = restauranteService.getRestauranteId(restaurantesList.get(0).getId());
		assertNull(response.getCiudad());
	}

	@Test
	void testRemoveCiudadInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			restauranteCiudadService.removeCiudad(0L);
		});
	}
}
