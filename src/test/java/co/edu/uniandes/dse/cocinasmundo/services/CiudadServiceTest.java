package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.*;

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
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(CiudadService.class)
class CiudadTest {
	
	@Autowired
	private CiudadService ciudadService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<CiudadEntity> listaCiudades = new ArrayList<CiudadEntity>();
	

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from CiudadEntity").executeUpdate();
	}
	

	private void insertData() {
		for(int i=0; i<3; i++) {
			CiudadEntity ciudadEntity = factory.manufacturePojo(CiudadEntity.class);
			entityManager.persist(ciudadEntity);
			listaCiudades.add(ciudadEntity);
		}
	}

	
	@Test
	void testGetCiudades() {
		List<CiudadEntity> list = ciudadService.getCiudades();
        assertEquals(list.size(), listaCiudades.size());
	}


	@Test
	void testCreateCiudad() {
		CiudadEntity nuevaCiudad = factory.manufacturePojo(CiudadEntity.class);
		CiudadEntity ciudadCreada = ciudadService.createCiudad(nuevaCiudad);
		CiudadEntity busqueda = entityManager.find(CiudadEntity.class, ciudadCreada.getId());
		
		assertEquals(nuevaCiudad.getId(), busqueda.getId());
		assertEquals(nuevaCiudad.getNombre(), busqueda.getNombre());
		assertEquals(nuevaCiudad.getPais(), busqueda.getPais());
		assertEquals(nuevaCiudad.getRestaurantes(), busqueda.getRestaurantes());
		assertEquals(nuevaCiudad.getUbicacion(), busqueda.getUbicacion());
	}
	

	@Test
	void testGetCiudadId() throws EntityNotFoundException {
		CiudadEntity nuevaCiudad = factory.manufacturePojo(CiudadEntity.class);
		CiudadEntity regionCreada = ciudadService.createCiudad(nuevaCiudad);
		CiudadEntity busqueda;
		
		busqueda = ciudadService.getCiudadId(regionCreada.getId());
		assertEquals(nuevaCiudad.getNombre(), busqueda.getNombre());
		assertEquals(nuevaCiudad.getPais(), busqueda.getPais());
		assertEquals(nuevaCiudad.getRestaurantes(), busqueda.getRestaurantes());
		assertEquals(nuevaCiudad.getUbicacion(), busqueda.getUbicacion());
	}
	

	@Test
	void testUpdateCiudad() throws EntityNotFoundException {
		CiudadEntity ciudadEntity = listaCiudades.get(0);
		CiudadEntity pojoEntity = factory.manufacturePojo(CiudadEntity.class);

		pojoEntity.setId(ciudadEntity.getId());

		ciudadService.updateCiudad(ciudadEntity.getId(), pojoEntity);

		CiudadEntity response = entityManager.find(CiudadEntity.class, ciudadEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getPais(), response.getPais());
		assertEquals(pojoEntity.getUbicacion(), response.getUbicacion());
		assertEquals(pojoEntity.getRestaurantes(), response.getRestaurantes());
	}

	@Test
	void testDeleteCiudad() throws EntityNotFoundException {
		CiudadEntity ciudadEntity = listaCiudades.get(0);
		ciudadService.deleteCiudad(ciudadEntity.getId());
		CiudadEntity deleted = entityManager.find(CiudadEntity.class, ciudadEntity.getId());
		assertNull(deleted);
	}

}



