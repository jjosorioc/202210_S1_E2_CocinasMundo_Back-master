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

import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(PaisService.class)
public class PaisServiceTest {

	@Autowired
	private PaisService paisService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaisEntity> listaPaises = new ArrayList<PaisEntity>();

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
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			PaisEntity paisEntity = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(paisEntity);
			listaPaises.add(paisEntity);
		}
	}

	@Test
	void testGetPaises() {
		List<PaisEntity> list = paisService.getPaises();
		assertEquals(list.size(), listaPaises.size());
	}

	@Test
	void testCreatePais() throws IllegalOperationException {
		PaisEntity nuevoPais = factory.manufacturePojo(PaisEntity.class);

		PaisEntity paisCreado = paisService.createPais(nuevoPais);

		PaisEntity busqueda = entityManager.find(PaisEntity.class, paisCreado.getId());

		assertEquals(nuevoPais.getId(), busqueda.getId());
		assertEquals(nuevoPais.getNombre(), busqueda.getNombre());
		assertEquals(nuevoPais.getNombre(), busqueda.getNombre());
	}

	@Test
	void testGetPaisByID() throws IllegalOperationException {
		PaisEntity nuevoPais = factory.manufacturePojo(PaisEntity.class);

		PaisEntity paisCreado = paisService.createPais(nuevoPais);

		PaisEntity busqueda;

		try {
			busqueda = paisService.getPaisByID(paisCreado.getId());

			assertEquals(nuevoPais.getId(), busqueda.getId());
			assertEquals(nuevoPais.getNombre(), busqueda.getNombre());
			assertEquals(nuevoPais.getNombre(), busqueda.getNombre());
		} catch (EntityNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdatePais() throws EntityNotFoundException {
		PaisEntity paisEntity = listaPaises.get(0);
		PaisEntity pojoEntity = factory.manufacturePojo(PaisEntity.class);

		pojoEntity.setId(paisEntity.getId());

		paisService.updatePais(paisEntity.getId(), pojoEntity);

		PaisEntity response = entityManager.find(PaisEntity.class, paisEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getCiudades(), response.getCiudades());
		assertEquals(pojoEntity.getUbicacion(), response.getUbicacion());

		// Se podrían agregar otros métodos
	}

}
