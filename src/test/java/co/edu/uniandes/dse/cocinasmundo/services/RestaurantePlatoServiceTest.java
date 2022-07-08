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
@Import({ RestaurantePlatoService.class, PlatoService.class })
public class RestaurantePlatoServiceTest {

	@Autowired
	private RestaurantePlatoService restaurantePlatoService;

	@Autowired
	private PlatoService platoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private RestauranteEntity restaurante = new RestauranteEntity();

	private List<PlatoEntity> platoList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from RestauranteEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PlatoEntity").executeUpdate();
	}

	private void insertData() {
		restaurante = factory.manufacturePojo(RestauranteEntity.class);
		entityManager.persist(restaurante);

		for (int i = 0; i < 3; i++) {
			PlatoEntity entity = factory.manufacturePojo(PlatoEntity.class);
			entity.getRestaurantes().add(restaurante);
			entityManager.persist(entity);
			platoList.add(entity);
			restaurante.getPlatos().add(entity);

		}
	}

	@Test
	void testAddPlato() throws IllegalOperationException, EntityNotFoundException {
		PlatoEntity newPlato = factory.manufacturePojo(PlatoEntity.class);

		platoService.createPlato(newPlato);

		PlatoEntity platoEntity = restaurantePlatoService.addPlato(restaurante.getId(), newPlato.getId());
		assertNotNull(platoEntity);

		assertEquals(platoEntity.getId(), newPlato.getId());
		assertEquals(platoEntity.getCulturaCulinaria(), newPlato.getCulturaCulinaria());
		assertEquals(platoEntity.getDescripcionPlato(), newPlato.getDescripcionPlato());
		assertEquals(platoEntity.getNombrePlato(), newPlato.getNombrePlato());
		assertEquals(platoEntity.getPrecio(), newPlato.getPrecio());

		PlatoEntity lastPlato = restaurantePlatoService.getPlato(restaurante.getId(), newPlato.getId());
		assertEquals(lastPlato.getId(), newPlato.getId());
		assertEquals(lastPlato.getCulturaCulinaria(), newPlato.getCulturaCulinaria());
		assertEquals(lastPlato.getDescripcionPlato(), newPlato.getDescripcionPlato());
		assertEquals(lastPlato.getNombrePlato(), newPlato.getNombrePlato());
		assertEquals(lastPlato.getPrecio(), newPlato.getPrecio());
	}

	@Test
	void testAddPlatoInvalidAuthor() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity newPlato = factory.manufacturePojo(PlatoEntity.class);

			platoService.createPlato(newPlato);
			restaurantePlatoService.addPlato(0L, newPlato.getId());
		});
	}

	@Test
	void testAddInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			restaurantePlatoService.addPlato(restaurante.getId(), 0L);
		});
	}

	@Test
	void testGetPlatos() throws EntityNotFoundException {
		List<PlatoEntity> platoEntities = restaurantePlatoService.getPlatos(restaurante.getId());

		assertEquals(platoList.size(), platoEntities.size());

		for (int i = 0; i < platoList.size(); i++) {
			assertTrue(platoEntities.contains(platoList.get(0)));
		}
	}

	@Test
	void testGetPlatosInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			restaurantePlatoService.getPlatos(0L);
		});
	}

	@Test
	void testGetPlato() throws EntityNotFoundException, IllegalOperationException {
		PlatoEntity platoEntity = platoList.get(0);
		PlatoEntity plato = restaurantePlatoService.getPlato(restaurante.getId(), platoEntity.getId());
		assertNotNull(plato);

		assertEquals(platoEntity.getId(), plato.getId());
		assertEquals(platoEntity.getNombrePlato(), plato.getNombrePlato());
		assertEquals(platoEntity.getCulturaCulinaria(), plato.getCulturaCulinaria());
		assertEquals(platoEntity.getDescripcionPlato(), plato.getDescripcionPlato());
		assertEquals(platoEntity.getPrecio(), plato.getPrecio());
	}

	@Test
	void testGetPlatoInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity platoEntity = platoList.get(0);
			restaurantePlatoService.getPlato(0L, platoEntity.getId());
		});
	}

	@Test
	void testGetInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			restaurantePlatoService.getPlato(restaurante.getId(), 0L);
		});
	}

	@Test
	void testGetplatoNotAssociatedAuthor() {
		assertThrows(IllegalOperationException.class, () -> {
			RestauranteEntity restauranteEntity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restauranteEntity);

			PlatoEntity platoEntity = factory.manufacturePojo(PlatoEntity.class);

			entityManager.persist(platoEntity);

			restaurantePlatoService.getPlato(restauranteEntity.getId(), platoEntity.getId());
		});
	}

	@Test
	void testReplacePlatos() throws EntityNotFoundException, IllegalOperationException {
		List<PlatoEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			PlatoEntity entity = factory.manufacturePojo(PlatoEntity.class);
			entity.getRestaurantes().add(restaurante);

			platoService.createPlato(entity);
			nuevaLista.add(entity);
		}
		restaurantePlatoService.replacePlatos(restaurante.getId(), nuevaLista);
		List<PlatoEntity> platoEntities = restaurantePlatoService.getPlatos(restaurante.getId());
		for (PlatoEntity aNuevaLista : nuevaLista) {
			assertTrue(platoEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplacePlatoInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PlatoEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				PlatoEntity entity = factory.manufacturePojo(PlatoEntity.class);

				platoService.createPlato(entity);
				nuevaLista.add(entity);
			}
			restaurantePlatoService.replacePlatos(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidPlatos() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PlatoEntity> nuevaLista = new ArrayList<>();
			PlatoEntity entity = factory.manufacturePojo(PlatoEntity.class);

			entity.setId(0L);
			nuevaLista.add(entity);
			restaurantePlatoService.replacePlatos(restaurante.getId(), nuevaLista);
		});
	}

	@Test
	void testRemovePlato() throws EntityNotFoundException {
		for (PlatoEntity plato : platoList) {
			restaurantePlatoService.removePlato(restaurante.getId(), plato.getId());
		}
		assertTrue(restaurantePlatoService.getPlatos(restaurante.getId()).isEmpty());
	}
}
