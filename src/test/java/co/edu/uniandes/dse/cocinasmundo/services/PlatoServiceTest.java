package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PlatoService.class)
public class PlatoServiceTest {

	@Autowired
	private PlatoService platoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PlatoEntity> listaPlatos = new ArrayList<PlatoEntity>();

	/**
	 * Métodos de inicialización
	 * 
	 * 
	 */

	@BeforeEach
	void setUp() throws Exception {
		clearData();
		insertData();
	}

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PlatoEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			PlatoEntity platoEntity = factory.manufacturePojo(PlatoEntity.class);
			entityManager.persist(platoEntity);
			listaPlatos.add(platoEntity);
		}
	}

	@Test
	void testGetPlatos() {
		List<PlatoEntity> list = platoService.getPlatos();
		assertEquals(list.size(), listaPlatos.size());
	}

	@Test
	void testCreatePlato() throws IllegalOperationException {
		PlatoEntity nuevoPlato = factory.manufacturePojo(PlatoEntity.class);

		PlatoEntity platoCreado = platoService.createPlato(nuevoPlato);

		PlatoEntity busqueda = entityManager.find(PlatoEntity.class, platoCreado.getId());

		assertEquals(nuevoPlato.getId(), busqueda.getId());
		assertEquals(nuevoPlato.getNombrePlato(), busqueda.getNombrePlato());
		assertEquals(nuevoPlato.getDescripcionPlato(), busqueda.getDescripcionPlato());
	}

	@Test
	void testGetPlatoByID() throws IllegalOperationException {
		PlatoEntity nuevoPlato = factory.manufacturePojo(PlatoEntity.class);

		PlatoEntity platoCreado = platoService.createPlato(nuevoPlato);

		PlatoEntity busqueda;

		try {
			busqueda = platoService.getPlatoByID(platoCreado.getId());

			assertEquals(nuevoPlato.getId(), busqueda.getId());
			assertEquals(nuevoPlato.getNombrePlato(), busqueda.getNombrePlato());
			assertEquals(nuevoPlato.getDescripcionPlato(), busqueda.getDescripcionPlato());
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdatePlato() throws EntityNotFoundException {
		PlatoEntity platoEntity = listaPlatos.get(0);
		PlatoEntity pojoEntity = factory.manufacturePojo(PlatoEntity.class);

		pojoEntity.setId(platoEntity.getId());

		platoService.updatePlato(platoEntity.getId(), pojoEntity);

		PlatoEntity response = entityManager.find(PlatoEntity.class, platoEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombrePlato(), response.getNombrePlato());
		assertEquals(pojoEntity.getCulturaCulinaria(), response.getCulturaCulinaria());
		assertEquals(pojoEntity.getRestaurantes(), response.getRestaurantes());

		// Se podrían agregar otros métodos
	}
}
