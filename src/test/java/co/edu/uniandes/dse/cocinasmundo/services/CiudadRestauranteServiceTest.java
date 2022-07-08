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
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ CiudadService.class, CiudadRestauranteService.class })

public class CiudadRestauranteServiceTest {

	@Autowired
	private CiudadRestauranteService ciudadRestauranteService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<RestauranteEntity>restaurantesList = new ArrayList<>();

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
				entity.getRestaurantes().add(restaurantesList.get(i));
			}
		}
	}

	@Test
	void testAddRestaurante() throws EntityNotFoundException {
		CiudadEntity entity = ciudadesList.get(0);
		RestauranteEntity restauranteEntity = restaurantesList.get(1);
		RestauranteEntity response = ciudadRestauranteService.addRestaurante(entity.getId(), restauranteEntity.getId());

		assertNotNull(response);
		assertEquals(restauranteEntity.getId(), response.getId());
	}

	@Test
	void testAddInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity entity = ciudadesList.get(0);
			ciudadRestauranteService.addRestaurante(0L, entity.getId());
		});
	}

	@Test
	void testAddRestauranteInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity entity = restaurantesList.get(1);
			ciudadRestauranteService.addRestaurante(entity.getId(), 0L);
		});
	}

	@Test
	void testGetRestaurantes() throws EntityNotFoundException {
		List<RestauranteEntity> list = ciudadRestauranteService.getRestaurantes(ciudadesList.get(0).getId());
		assertEquals(1, list.size());
	}

	@Test
	void testGetRestaurantesInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			ciudadRestauranteService.getRestaurantes(0L);
		});
	}

	@Test
	void testGetRestaurante() throws IllegalOperationException, EntityNotFoundException {
		CiudadEntity entity = ciudadesList.get(0);
		RestauranteEntity restauranteEntity = restaurantesList.get(0);
		RestauranteEntity response = ciudadRestauranteService.getRestaurante(entity.getId(), restauranteEntity.getId());

		assertEquals(restauranteEntity.getId(), response.getId());
		assertEquals(restauranteEntity.getNombreRestaurante(), response.getNombreRestaurante());
		assertEquals(restauranteEntity.getDireccionRestaurante(), response.getDireccionRestaurante());
		assertEquals(restauranteEntity.getCiudad(), response.getCiudad());
		assertEquals(restauranteEntity.getCulturasCulinarias(), response.getCulturasCulinarias());
		assertEquals(restauranteEntity.getPlatos(), response.getPlatos());
		assertEquals(restauranteEntity.getEstrellaMichellin(), response.getEstrellaMichellin());
		
	}

	@Test
	void testGetRestauranteInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity restauranteEntity = restaurantesList.get(0);
			ciudadRestauranteService.getRestaurante(0L, restauranteEntity.getId());
		});
	}

	@Test
	void testGetInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity entity = ciudadesList.get(0);
			ciudadRestauranteService.getRestaurante(entity.getId(), 0L);
		});
	}

	@Test
	void getRestauranteNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			CiudadEntity entity = ciudadesList.get(0);
			RestauranteEntity restauranteEntity = restaurantesList.get(1);
			ciudadRestauranteService.getRestaurante(entity.getId(), restauranteEntity.getId());
		});
	}

	@Test
	void testReplaceRestaurantes() throws EntityNotFoundException {
		CiudadEntity entity = ciudadesList.get(0);
		List<RestauranteEntity> list = restaurantesList.subList(1, 3);
		ciudadRestauranteService.replaceRestaurantes(entity.getId(), list);

		for (RestauranteEntity r : list) {
			RestauranteEntity b = entityManager.find(RestauranteEntity.class, r.getId());
			assertTrue(b.getCiudad().equals(entity));
		}
	}

	@Test
	void testReplaceInvalidRestaurantes() {
		assertThrows(EntityNotFoundException.class, () -> {
			CiudadEntity entity = ciudadesList.get(0);

			List<RestauranteEntity> restaurantes = new ArrayList<>();
			RestauranteEntity newRestaurante = factory.manufacturePojo(RestauranteEntity.class);
			newRestaurante.setId(0L);
			restaurantes.add(newRestaurante);

			ciudadRestauranteService.replaceRestaurantes(entity.getId(), restaurantes);
		});
	}

	@Test
	void testReplaceRestaurantesInvalidCiudad() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<RestauranteEntity> list = restaurantesList.subList(1, 3);
			ciudadRestauranteService.replaceRestaurantes(0L, list);
		});
	}
}

