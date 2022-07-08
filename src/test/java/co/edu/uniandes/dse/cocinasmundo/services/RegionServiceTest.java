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

import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(RegionService.class)
class RegionTest {
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<RegionEntity> listaRegiones = new ArrayList<RegionEntity>();
	

	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	

	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from RegionEntity").executeUpdate();
	}
	

	private void insertData() {
		for(int i=0; i<3; i++) {
			RegionEntity regionEntity = factory.manufacturePojo(RegionEntity.class);
			entityManager.persist(regionEntity);
			listaRegiones.add(regionEntity);
		}
	}

	
	@Test
	void testGetRegiones() {
		List<RegionEntity> list = regionService.getRegiones();
        assertEquals(list.size(), listaRegiones.size());
	}


	@Test
	void testCreateRegion() {
		RegionEntity nuevaRegion = factory.manufacturePojo(RegionEntity.class);
		RegionEntity regionCreada = regionService.createRegion(nuevaRegion);
		RegionEntity busqueda = entityManager.find(RegionEntity.class, regionCreada.getId());
		
		assertEquals(nuevaRegion.getId(), busqueda.getId());
		assertEquals(nuevaRegion.getNombre(), busqueda.getNombre());
		assertEquals(nuevaRegion.getPaises(), busqueda.getPaises());
		assertEquals(nuevaRegion.getCulturasCulinarias(), busqueda.getCulturasCulinarias());
	}
	

	@Test
	void testGetRegionId() throws EntityNotFoundException {
		RegionEntity nuevaRegion = factory.manufacturePojo(RegionEntity.class);
		RegionEntity regionCreada = regionService.createRegion(nuevaRegion);
		RegionEntity busqueda;
		
		busqueda = regionService.getRegionId(regionCreada.getId());
		assertEquals(nuevaRegion.getId(), busqueda.getId());
		assertEquals(nuevaRegion.getNombre(), busqueda.getNombre());
		assertEquals(nuevaRegion.getPaises(), busqueda.getPaises());
		assertEquals(nuevaRegion.getCulturasCulinarias(), busqueda.getCulturasCulinarias());
	}
	

	@Test
	void testUpdateRegion() throws EntityNotFoundException {
		RegionEntity regionEntity = listaRegiones.get(0);
		RegionEntity pojoEntity = factory.manufacturePojo(RegionEntity.class);

		pojoEntity.setId(regionEntity.getId());

		regionService.updateRegion(regionEntity.getId(), pojoEntity);

		RegionEntity response = entityManager.find(RegionEntity.class, regionEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombre(), response.getNombre());
		assertEquals(pojoEntity.getPaises(), response.getPaises());
		assertEquals(pojoEntity.getCulturasCulinarias(), response.getCulturasCulinarias());
	}

	@Test
	void testDeleteRegion() throws EntityNotFoundException {
		RegionEntity regionEntity = listaRegiones.get(0);
		regionService.deleteRegion(regionEntity.getId());
		RegionEntity deleted = entityManager.find(RegionEntity.class, regionEntity.getId());
		assertNull(deleted);
	}

}


