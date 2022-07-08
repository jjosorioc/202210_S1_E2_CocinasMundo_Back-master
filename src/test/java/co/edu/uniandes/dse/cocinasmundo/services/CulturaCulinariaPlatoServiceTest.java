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
import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ CulturaCulinariaService.class, CulturaCulinariaPlatoService.class })
public class CulturaCulinariaPlatoServiceTest {

	@Autowired
	private CulturaCulinariaPlatoService culturaCulinariaPlatoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<CulturaCulinariaEntity> culturasList = new ArrayList<>();

	private List<PlatoEntity> platosList = new ArrayList<>();

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
				entity.getRecetasMasRepresentativas().add(platosList.get(i));
			}
		}
	}

	@Test
	void testAddPlato() throws EntityNotFoundException, IllegalOperationException {
		CulturaCulinariaEntity entity = culturasList.get(0);
		PlatoEntity platoEntity = platosList.get(1);
		PlatoEntity response = culturaCulinariaPlatoService.addPlato(entity.getId(), platoEntity.getId());

		assertNotNull(response);
		assertEquals(platoEntity.getId(), response.getId());
	}

	@Test
	void testAddInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			CulturaCulinariaEntity entity = culturasList.get(0);
			culturaCulinariaPlatoService.addPlato(0L, entity.getId());
		});
	}

	@Test
	void testAddPlatoInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity entity = platosList.get(1);
			culturaCulinariaPlatoService.addPlato(entity.getId(), 0L);
		});
	}

	@Test
	void testGetPlatos() throws EntityNotFoundException {
		List<PlatoEntity> list = culturaCulinariaPlatoService.getPlatos(culturasList.get(0).getId());
		assertEquals(1, list.size());
	}

	@Test
	void testGetPlatosInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			culturaCulinariaPlatoService.getPlatos(0L);
		});
	}

	@Test
	void testGetPlato() throws IllegalOperationException, EntityNotFoundException {
		CulturaCulinariaEntity entity = culturasList.get(0);
		PlatoEntity platoEntity = platosList.get(0);
		PlatoEntity response = culturaCulinariaPlatoService.getPlato(entity.getId(), platoEntity.getId());

		assertEquals(platoEntity.getId(), response.getId());
		assertEquals(platoEntity.getNombrePlato(), response.getNombrePlato());
		assertEquals(platoEntity.getDescripcionPlato(), response.getDescripcionPlato());
		assertEquals(platoEntity.getCulturaCulinaria(), response.getCulturaCulinaria());
		assertEquals(platoEntity.getPrecio(), response.getPrecio());
		assertEquals(platoEntity.getRecetas(), response.getRecetas());
		assertEquals(platoEntity.getRestaurantes(), response.getRestaurantes());
	}

	@Test
	void testGetPlatoInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			PlatoEntity platoEntity = platosList.get(0);
			culturaCulinariaPlatoService.getPlato(0L, platoEntity.getId());
		});
	}

	@Test
	void testGetInvalidPlato() {
		assertThrows(EntityNotFoundException.class, () -> {
			CulturaCulinariaEntity entity = culturasList.get(0);
			culturaCulinariaPlatoService.getPlato(entity.getId(), 0L);
		});
	}

	@Test
	void getBookNoAsociadoTest() {
		assertThrows(IllegalOperationException.class, () -> {
			CulturaCulinariaEntity entity = culturasList.get(0);
			PlatoEntity bookEntity = platosList.get(1);
			culturaCulinariaPlatoService.getPlato(entity.getId(), bookEntity.getId());
		});
	}

	@Test
	void testReplacePlatos() throws EntityNotFoundException {
		CulturaCulinariaEntity entity = culturasList.get(0);
		List<PlatoEntity> list = platosList.subList(1, 3);
		culturaCulinariaPlatoService.replacePlatos(entity.getId(), list);

		for (PlatoEntity Plato : list) {
			PlatoEntity b = entityManager.find(PlatoEntity.class, Plato.getId());
			assertTrue(b.getCulturaCulinaria().equals(entity));
		}
	}

	@Test
	void testReplaceInvalidPlatos() {
		assertThrows(EntityNotFoundException.class, () -> {
			CulturaCulinariaEntity entity = culturasList.get(0);

			List<PlatoEntity> platos = new ArrayList<>();
			PlatoEntity newPlato = factory.manufacturePojo(PlatoEntity.class);
			newPlato.setId(0L);
			platos.add(newPlato);

			culturaCulinariaPlatoService.replacePlatos(entity.getId(), platos);
		});
	}

	@Test
	void testReplacePlatosInvalidCultura() {
		assertThrows(EntityNotFoundException.class, () -> {
			List<PlatoEntity> list = platosList.subList(1, 3);
			culturaCulinariaPlatoService.replacePlatos(0L, list);
		});
	}
}
