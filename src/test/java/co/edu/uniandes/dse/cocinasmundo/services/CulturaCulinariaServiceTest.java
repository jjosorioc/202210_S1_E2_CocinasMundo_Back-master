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

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(CulturaCulinariaService.class)
class CulturaCulinariaTest {
	
	@Autowired
	private CulturaCulinariaService culturaCulinariaService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<CulturaCulinariaEntity> listaCulturasCulinarias = new ArrayList<CulturaCulinariaEntity>();
	

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
	}
	

	private void insertData() {
		for(int i=0; i<3; i++) {
			CulturaCulinariaEntity culturaCulinariaEntity = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entityManager.persist(culturaCulinariaEntity);
			listaCulturasCulinarias.add(culturaCulinariaEntity);
		}
	}

	
	@Test
	void testGetCulturaCulinaria() {
		List<CulturaCulinariaEntity> list = culturaCulinariaService.getCulturasCulinarias();
        assertEquals(list.size(), listaCulturasCulinarias.size());
	}


	@Test
	void testCreateCulturaCulinaria() {
		CulturaCulinariaEntity nuevoCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
		CulturaCulinariaEntity culturaCulinariaCreado = culturaCulinariaService.createCulturaCulinaria(nuevoCulturaCulinaria);
		CulturaCulinariaEntity busqueda = entityManager.find(CulturaCulinariaEntity.class, culturaCulinariaCreado.getId());
		
		assertEquals(nuevoCulturaCulinaria.getId(), busqueda.getId());
		assertEquals(nuevoCulturaCulinaria.getNombreCultura(), busqueda.getNombreCultura());
		assertEquals(nuevoCulturaCulinaria.getDescripcionCultura(), busqueda.getDescripcionCultura());
	}
	

	@Test
	void testGetCulturaCulinariaId() throws EntityNotFoundException {
		CulturaCulinariaEntity nuevaCulturaCulinaria = factory.manufacturePojo(CulturaCulinariaEntity.class);
		CulturaCulinariaEntity culturaCulinariaCreado = culturaCulinariaService.createCulturaCulinaria(nuevaCulturaCulinaria);
		CulturaCulinariaEntity busqueda;
		
		busqueda = culturaCulinariaService.getCulturaCulinariaId(culturaCulinariaCreado.getId());
		assertEquals(nuevaCulturaCulinaria.getId(), busqueda.getId());
		assertEquals(nuevaCulturaCulinaria.getNombreCultura(), busqueda.getNombreCultura());
		assertEquals(nuevaCulturaCulinaria.getDescripcionCultura(), busqueda.getDescripcionCultura());
	}

	@Test
	void testUpdateCulturaCulinaria() throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = listaCulturasCulinarias.get(0);
		CulturaCulinariaEntity pojoEntity = factory.manufacturePojo(CulturaCulinariaEntity.class);

		pojoEntity.setId(culturaCulinariaEntity.getId());

		culturaCulinariaService.updateCulturaCulinaria(culturaCulinariaEntity.getId(), pojoEntity);

		CulturaCulinariaEntity response = entityManager.find(CulturaCulinariaEntity.class, culturaCulinariaEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombreCultura(), response.getNombreCultura());
		assertEquals(pojoEntity.getDescripcionCultura(), response.getDescripcionCultura());
	}

	@Test
	void testDeleteIngrediente() throws EntityNotFoundException {
		CulturaCulinariaEntity culturaCulinariaEntity = listaCulturasCulinarias.get(0);
		culturaCulinariaService.deleteCulturaCulinaria(culturaCulinariaEntity.getId());
		CulturaCulinariaEntity deleted = entityManager.find(CulturaCulinariaEntity.class, culturaCulinariaEntity.getId());
		assertNull(deleted);
	}

}

