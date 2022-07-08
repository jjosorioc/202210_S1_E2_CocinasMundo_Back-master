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

import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PlatoRestauranteService.class)
public class PlatoRestauranteServiceTest {

	@Autowired
	private PlatoRestauranteService platoRestauranteService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private PlatoEntity plato = new PlatoEntity();
	private List<RestauranteEntity> restauranteList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from RestauranteEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PlatoEntity").executeUpdate();
	}

	private void insertData() {
		plato = factory.manufacturePojo(PlatoEntity.class);
		entityManager.persist(plato);
		for (int i = 0; i < 3; i++) {
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(entity);
			entity.getPlatos().add(plato);
			restauranteList.add(entity);
			plato.getRestaurantes().add(entity);
		}

	}

	@Test
	void testAddPlato() throws co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException,
			co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException {
		PlatoEntity newplato = factory.manufacturePojo(PlatoEntity.class);

		entityManager.persist(newplato);

		RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
		entityManager.persist(restaurante);

		platoRestauranteService.addRestaurante(newplato.getId(), restaurante.getId());

		RestauranteEntity lastrestaurante = platoRestauranteService.getRestaurante(newplato.getId(),
				restaurante.getId());
		assertEquals(restaurante.getId(), lastrestaurante.getId());
		assertEquals(restaurante.getCiudad(), lastrestaurante.getCiudad());
		assertEquals(restaurante.getCulturasCulinarias(), lastrestaurante.getCulturasCulinarias());
		assertEquals(restaurante.getDireccionRestaurante(), lastrestaurante.getDireccionRestaurante());
		assertEquals(restaurante.getNombreRestaurante(), lastrestaurante.getNombreRestaurante());
	}

	@Test
	void testAddInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity newplato = factory.manufacturePojo(PlatoEntity.class);
			entityManager.persist(newplato);
			platoRestauranteService.addRestaurante(newplato.getId(), 0L);
		});
	}

	@Test
	void testAddRestauranteInvalidPlato() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restaurante);
			platoRestauranteService.addRestaurante(0L, restaurante.getId());
		});
	}

	@Test
	void testGetRestaurantes() throws EntityNotFoundException {
		List<RestauranteEntity> restauranteEntities = platoRestauranteService.getRestaurantes(plato.getId());

		assertEquals(restauranteList.size(), restauranteEntities.size());

		for (int i = 0; i < restauranteList.size(); i++) {
			assertTrue(restauranteEntities.contains(restauranteList.get(0)));
		}
	}

	@Test
	void testGetRestaurantesInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoRestauranteService.getRestaurantes(0L);
		});
	}

	@Test
	void testGetRestaurante() throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = restauranteList.get(0);
		RestauranteEntity restaurante = platoRestauranteService.getRestaurante(plato.getId(),
				restauranteEntity.getId());
		assertNotNull(restaurante);

		assertEquals(restauranteEntity.getId(), restaurante.getId());
		assertEquals(restauranteEntity.getNombreRestaurante(), restaurante.getNombreRestaurante());
		assertEquals(restauranteEntity.getCiudad(), restaurante.getCiudad());
		assertEquals(restauranteEntity.getDireccionRestaurante(), restaurante.getDireccionRestaurante());
		assertEquals(restauranteEntity.getPlatos(), restaurante.getPlatos());
	}

	@Test
	void testGetInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoRestauranteService.getRestaurante(plato.getId(), 0L);
		});
	}

	@Test
	void testGetRestauranteInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity restauranteEntity = restauranteList.get(0);
			platoRestauranteService.getRestaurante(0L, restauranteEntity.getId());
		});
	}

	@Test
	void testGetNotAssociatedRestaurante() {
		assertThrows(IllegalOperationException.class, () -> {
			PlatoEntity newplato = factory.manufacturePojo(PlatoEntity.class);

			entityManager.persist(newplato);
			RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restaurante);
			platoRestauranteService.getRestaurante(newplato.getId(), restaurante.getId());
		});
	}

	@Test
	void testReplaceRestaurantes() throws EntityNotFoundException {
		List<RestauranteEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(entity);
			plato.getRestaurantes().add(entity);
			nuevaLista.add(entity);
		}
		platoRestauranteService.replaceRestaurantes(plato.getId(), nuevaLista);

		List<RestauranteEntity> restauranteEntities = platoRestauranteService.getRestaurantes(plato.getId());
		for (RestauranteEntity aNuevaLista : nuevaLista) {
			assertTrue(restauranteEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplaceRestaurantesInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<RestauranteEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
				entity.getPlatos().add(plato);
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			platoRestauranteService.replaceRestaurantes(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidRestaurantes() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<RestauranteEntity> nuevaLista = new ArrayList<>();
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			platoRestauranteService.replaceRestaurantes(plato.getId(), nuevaLista);
		});
	}

	@Test
	void testRemoveRestaurante() throws EntityNotFoundException {
		for (RestauranteEntity restaurante : restauranteList) {
			platoRestauranteService.removeRestaurante(plato.getId(), restaurante.getId());
		}
		assertTrue(platoRestauranteService.getRestaurantes(plato.getId()).isEmpty());
	}

	@Test
	void testRemoveInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoRestauranteService.removeRestaurante(plato.getId(), 0L);
		});
	}

	@Test
	void testRemoveRestauranteInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			platoRestauranteService.removeRestaurante(0L, restauranteList.get(0).getId());
		});
	}
}
