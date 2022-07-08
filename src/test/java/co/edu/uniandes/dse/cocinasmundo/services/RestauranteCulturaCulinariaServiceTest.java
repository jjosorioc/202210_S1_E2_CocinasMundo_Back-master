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
@Import({ RestauranteCulturaCulinariaService.class, CulturaCulinariaService.class })
public class RestauranteCulturaCulinariaServiceTest {
	@Autowired
	private RestauranteCulturaCulinariaService restauranteCulturaCulinariaService;

	@Autowired
	private CulturaCulinariaService culturaCulinariaService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private RestauranteEntity restaurante = new RestauranteEntity();

	private List<CulturaCulinariaEntity> culturaCulinariaList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
	}

	private void insertData() {
		restaurante = factory.manufacturePojo(RestauranteEntity.class);
		entityManager.persist(restaurante);

		for (int i = 0; i < 3; i++) {
			CulturaCulinariaEntity entity = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entity.getRestaurantes().add(restaurante);
			entityManager.persist(entity);
			culturaCulinariaList.add(entity);
			restaurante.getCulturasCulinarias().add(entity);

		}
	}

	@Test
	void testAddCultura() throws IllegalOperationException, EntityNotFoundException {
		CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);

		culturaCulinariaService.createCulturaCulinaria(newCulturaCulinaria);

		CulturaCulinariaEntity culturaCulinariaEntity = restauranteCulturaCulinariaService.addCulturaCulinaria(restaurante.getId(), newCulturaCulinaria.getId());
		assertNotNull(culturaCulinariaEntity);

		assertEquals(culturaCulinariaEntity.getId(), newCulturaCulinaria.getId());
		assertEquals(culturaCulinariaEntity.getNombreCultura(), newCulturaCulinaria.getNombreCultura());
		assertEquals(culturaCulinariaEntity.getDescripcionCultura(), newCulturaCulinaria.getDescripcionCultura());
		assertEquals(culturaCulinariaEntity.getCalificacionCultura(), newCulturaCulinaria.getCalificacionCultura());
		assertEquals(culturaCulinariaEntity.getPais(), newCulturaCulinaria.getPais());
		assertEquals(culturaCulinariaEntity.getRegion(), newCulturaCulinaria.getRegion());

		CulturaCulinariaEntity lastCultura = restauranteCulturaCulinariaService.getCulturaCulinaria(restaurante.getId(), newCulturaCulinaria.getId());
		assertEquals(lastCultura.getId(), newCulturaCulinaria.getId());
		assertEquals(lastCultura.getNombreCultura(), newCulturaCulinaria.getNombreCultura());
		assertEquals(lastCultura.getDescripcionCultura(), newCulturaCulinaria.getDescripcionCultura());
		assertEquals(lastCultura.getCalificacionCultura(), newCulturaCulinaria.getCalificacionCultura());
		assertEquals(lastCultura.getPais(), newCulturaCulinaria.getPais());
		assertEquals(lastCultura.getRegion(), newCulturaCulinaria.getRegion());
	}

	@Test
	void testAddCulturaInvalidAuthor() {
		assertThrows(EntityNotFoundException.class, () -> {
			CulturaCulinariaEntity newCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);

			culturaCulinariaService.createCulturaCulinaria(newCulturaCulinaria);
			restauranteCulturaCulinariaService.addCulturaCulinaria(0L, newCulturaCulinaria.getId());
		});
	}

	@Test
	void testAddInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			restauranteCulturaCulinariaService.addCulturaCulinaria(restaurante.getId(), 0L);
		});
	}

	@Test
	void testGetCulturas() throws EntityNotFoundException {
		List<CulturaCulinariaEntity> culturaCulinariaEntities = restauranteCulturaCulinariaService.getCulturasCulinarias(restaurante.getId());

		assertEquals(culturaCulinariaList.size(), culturaCulinariaEntities.size());

		for (int i = 0; i < culturaCulinariaList.size(); i++) {
			assertTrue(culturaCulinariaEntities.contains(culturaCulinariaList.get(0)));
		}
	}

	@Test
	void testGetCulturasInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			restauranteCulturaCulinariaService.getCulturasCulinarias(0L);
		});
	}

	@Test
	void testGetCultura() throws EntityNotFoundException, IllegalOperationException {
		CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaList.get(0);
		CulturaCulinariaEntity culturaCulinaria = restauranteCulturaCulinariaService.getCulturaCulinaria(restaurante.getId(), culturaCulinariaEntity.getId());
		assertNotNull(culturaCulinaria);

		assertEquals(culturaCulinariaEntity.getId(), culturaCulinaria.getId());
		assertEquals(culturaCulinariaEntity.getNombreCultura(), culturaCulinaria.getNombreCultura());
		assertEquals(culturaCulinariaEntity.getDescripcionCultura(), culturaCulinaria.getDescripcionCultura());
		assertEquals(culturaCulinariaEntity.getCalificacionCultura(), culturaCulinaria.getCalificacionCultura());
		assertEquals(culturaCulinariaEntity.getPais(), culturaCulinaria.getPais());
		assertEquals(culturaCulinariaEntity.getRegion(), culturaCulinaria.getRegion());
	}

	@Test
	void testGetCulturaInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			CulturaCulinariaEntity culturaCulinariaEntity = culturaCulinariaList.get(0);
			restauranteCulturaCulinariaService.getCulturaCulinaria(0L, culturaCulinariaEntity.getId());
		});
	}

	@Test
	void testGetInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			restauranteCulturaCulinariaService.getCulturaCulinaria(restaurante.getId(), 0L);
		});
	}

	@Test
	void testGetCulturaNotAssociatedAuthor() {
		assertThrows(IllegalOperationException.class, () -> {
			RestauranteEntity restauranteEntity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restauranteEntity);

			CulturaCulinariaEntity culturaCulinariaEntity = factory.manufacturePojo(CulturaCulinariaEntity.class);

			entityManager.persist(culturaCulinariaEntity);

			restauranteCulturaCulinariaService.getCulturaCulinaria(restauranteEntity.getId(), culturaCulinariaEntity.getId());
		});
	}

	@Test
	void testReplaceCulturas() throws EntityNotFoundException, IllegalOperationException {
		List<CulturaCulinariaEntity> nuevaLista = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			CulturaCulinariaEntity entity = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entity.getRestaurantes().add(restaurante);

			culturaCulinariaService.createCulturaCulinaria(entity);
			nuevaLista.add(entity);
		}
		restauranteCulturaCulinariaService.replaceCulturasCulinarias(restaurante.getId(), nuevaLista);
		List<CulturaCulinariaEntity> culturaCulinariaEntities = restauranteCulturaCulinariaService.getCulturasCulinarias(restaurante.getId());
		for (CulturaCulinariaEntity aNuevaLista : nuevaLista) {
			assertTrue(culturaCulinariaEntities.contains(aNuevaLista));
		}
	}

	@Test
	void testReplaceCulturaInvalidRestaurante() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<CulturaCulinariaEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				CulturaCulinariaEntity entity = factory.manufacturePojo(CulturaCulinariaEntity.class);

				culturaCulinariaService.createCulturaCulinaria(entity);
				nuevaLista.add(entity);
			}
			restauranteCulturaCulinariaService.replaceCulturasCulinarias(0L, nuevaLista);
		});
	}

	@Test
	void testReplaceInvalidCulturas() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<CulturaCulinariaEntity> nuevaLista = new ArrayList<>();
			CulturaCulinariaEntity entity = factory.manufacturePojo(CulturaCulinariaEntity.class);

			entity.setId(0L);
			nuevaLista.add(entity);
			restauranteCulturaCulinariaService.replaceCulturasCulinarias(restaurante.getId(), nuevaLista);
		});
	}

	@Test
	void testRemoveCultura() throws EntityNotFoundException {
		for (CulturaCulinariaEntity cultura : culturaCulinariaList) {
			restauranteCulturaCulinariaService.removeCulturaCulinaria(restaurante.getId(), cultura.getId());
		}
		assertTrue(restauranteCulturaCulinariaService.getCulturasCulinarias(restaurante.getId()).isEmpty());
	}
}
