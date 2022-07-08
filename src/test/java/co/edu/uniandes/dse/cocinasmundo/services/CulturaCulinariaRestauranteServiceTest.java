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

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(CulturaCulinariaRestauranteService.class)
public class CulturaCulinariaRestauranteServiceTest {
	@Autowired
	private CulturaCulinariaRestauranteService culturaCulinariaRestauranteService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private CulturaCulinariaEntity culturaCulinaria = new CulturaCulinariaEntity();
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
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
	}
	
	private void insertData() {
		culturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
		entityManager.persist(culturaCulinaria);
		for (int i = 0; i < 3; i++) {
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(entity);
			entity.getCulturasCulinarias().add(culturaCulinaria);
			restauranteList.add(entity);
			culturaCulinaria.getRestaurantes().add(entity);
		}

	}
	
	@Test
	void testAddCulturaCulinaria() throws co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException,
			co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException {
		CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);

		entityManager.persist(newCulturaCulinaria);

		RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
		entityManager.persist(restaurante);

		culturaCulinariaRestauranteService.addRestaurante(newCulturaCulinaria.getId(), restaurante.getId());

		RestauranteEntity lastrestaurante = culturaCulinariaRestauranteService.getRestaurante(newCulturaCulinaria.getId(),
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
			CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entityManager.persist(newCulturaCulinaria);
			culturaCulinariaRestauranteService.addRestaurante(newCulturaCulinaria.getId(), 0L);
		});
	}

	@Test
	void testAddRestauranteInvalidCultura() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restaurante);
			culturaCulinariaRestauranteService.addRestaurante(0L, restaurante.getId());
		});
	}
	
	@Test
	void testGetRestaurantes() throws EntityNotFoundException {
		List<RestauranteEntity> restauranteEntities = culturaCulinariaRestauranteService.getRestaurantes(culturaCulinaria.getId());

		assertEquals(restauranteList.size(), restauranteEntities.size());

		for (int i = 0; i < restauranteList.size(); i++) {
			assertTrue(restauranteEntities.contains(restauranteList.get(0)));
		}
	}

	@Test
	void testGetRestaurantesInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			culturaCulinariaRestauranteService.getRestaurantes(0L);
		});
	}
	
	@Test
	void testGetRestaurante() throws EntityNotFoundException, IllegalOperationException {
		RestauranteEntity restauranteEntity = restauranteList.get(0);
		RestauranteEntity restaurante = culturaCulinariaRestauranteService.getRestaurante(culturaCulinaria.getId(),
				restauranteEntity.getId());
		assertNotNull(restaurante);

		assertEquals(restauranteEntity.getId(), restaurante.getId());
		assertEquals(restauranteEntity.getNombreRestaurante(), restaurante.getNombreRestaurante());
		assertEquals(restauranteEntity.getCiudad(), restaurante.getCiudad());
		assertEquals(restauranteEntity.getDireccionRestaurante(), restaurante.getDireccionRestaurante());
		assertEquals(restauranteEntity.getCulturasCulinarias(), restaurante.getCulturasCulinarias());
	}

	@Test
	void testGetInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			culturaCulinariaRestauranteService.getRestaurante(culturaCulinaria.getId(), 0L);
		});
	}

	@Test
	void testGetRestauranteInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			RestauranteEntity restauranteEntity = restauranteList.get(0);
			culturaCulinariaRestauranteService.getRestaurante(0L, restauranteEntity.getId());
		});
	}

	@Test
	void testGetNotAssociatedRestaurante() {
		assertThrows(IllegalOperationException.class, () -> {
			CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);

			entityManager.persist(newCulturaCulinaria);
			RestauranteEntity restaurante = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restaurante);
			culturaCulinariaRestauranteService.getRestaurante(newCulturaCulinaria.getId(), restaurante.getId());
		});
	}

	@Test
	void testReplaceRestaurantes() throws EntityNotFoundException {
		List<RestauranteEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(entity);
			culturaCulinaria.getRestaurantes().add(entity);
			nuevaLista.add(entity);
		}
		culturaCulinariaRestauranteService.replaceRestaurantes(culturaCulinaria.getId(), nuevaLista);

		List<RestauranteEntity> restauranteEntities = culturaCulinariaRestauranteService.getRestaurantes(culturaCulinaria.getId());
		for (RestauranteEntity aNuevaLista : nuevaLista) {
			assertTrue(restauranteEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplaceRestaurantesInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<RestauranteEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
				entity.getCulturasCulinarias().add(culturaCulinaria);
				entityManager.persist(entity);
				nuevaLista.add(entity);
			}
			culturaCulinariaRestauranteService.replaceRestaurantes(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidRestaurantes() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<RestauranteEntity> nuevaLista = new ArrayList<>();
			RestauranteEntity entity = factory.manufacturePojo(RestauranteEntity.class);
			entity.setId(0L);
			nuevaLista.add(entity);
			culturaCulinariaRestauranteService.replaceRestaurantes(culturaCulinaria.getId(), nuevaLista);
		});
	}

	@Test
	void testRemoveRestaurante() throws EntityNotFoundException {
		for (RestauranteEntity restaurante : restauranteList) {
			culturaCulinariaRestauranteService.removeRestaurante(culturaCulinaria.getId(), restaurante.getId());
		}
		assertTrue(culturaCulinariaRestauranteService.getRestaurantes(culturaCulinaria.getId()).isEmpty());
	}

	@Test
	void testRemoveInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			culturaCulinariaRestauranteService.removeRestaurante(culturaCulinaria.getId(), 0L);
		});
	}

	@Test
	void testRemoveRestauranteInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			culturaCulinariaRestauranteService.removeRestaurante(0L, restauranteList.get(0).getId());
		});
	}
}
