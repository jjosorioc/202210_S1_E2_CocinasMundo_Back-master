package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.*;

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

import co.edu.uniandes.dse.cocinasmundo.entities.EstrellaMichelinEntity;
import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.services.EstrellaMichelinService;

import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EstrellaMichelinService.class)
public class EstrellaMichelinServiceTest {
	
	@Autowired
	private EstrellaMichelinService estrellaMichelinService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<EstrellaMichelinEntity> estrellaMichelinList = new ArrayList();
	
	/**
	 * Configuracion inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	/**
	 * Se limpian las tablas
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstrellaMichelinEntity").executeUpdate();
	}
	
	/**
	 * Se insertan los datos para la ejecucion de pruebas
	 */
	private void insertData() {
		for (int i=0; i<3; i++) {
			EstrellaMichelinEntity estrellaMichelinEntity = factory.manufacturePojo(EstrellaMichelinEntity.class);
			entityManager.persist(estrellaMichelinEntity);
			estrellaMichelinList.add(estrellaMichelinEntity);
		}
	}
	
	/**
	 * Prueba para obtener las estrellas michelin
	 */
	@Test
	void testGetEstrellasMichelin() {
		List<EstrellaMichelinEntity> list = estrellaMichelinService.getEstrellaMichelin();
		assertEquals(list.size(), estrellaMichelinList.size());
	}
	
	/**
	 * Prueba para la creacion de estrellas michelin
	 */
	@Test
	void testCreateEstrellasMichelin() {
		EstrellaMichelinEntity nuevaEstrellaMichelin = factory.manufacturePojo(EstrellaMichelinEntity.class);
		EstrellaMichelinEntity estrellaMichelinCreada = estrellaMichelinService.createEstrellaMichelin(nuevaEstrellaMichelin);
		EstrellaMichelinEntity busquedaEstrellaMichelin = entityManager.find(EstrellaMichelinEntity.class, estrellaMichelinCreada.getId());
	
		assertEquals(nuevaEstrellaMichelin.getId(), busquedaEstrellaMichelin.getId());
		assertEquals(nuevaEstrellaMichelin.getDescripcion(), busquedaEstrellaMichelin.getDescripcion());
		assertEquals(nuevaEstrellaMichelin.getFechaEntregada(), busquedaEstrellaMichelin.getFechaEntregada());
		assertEquals(nuevaEstrellaMichelin.getTipoEstrella(), busquedaEstrellaMichelin.getTipoEstrella());
	}
	
	/**
	 * Prueba para obtener la estrella michelin por su id
	 * @throws EntityNotFoundException
	 */
	@Test
	void testGetEstrellaMichelinId() throws EntityNotFoundException {
		EstrellaMichelinEntity nuevaEstrellaMichelin = factory.manufacturePojo(EstrellaMichelinEntity.class);
		EstrellaMichelinEntity estrellaMichelinCreada = estrellaMichelinService.createEstrellaMichelin(nuevaEstrellaMichelin);
		EstrellaMichelinEntity busquedaEstrellaMichelin;
		
		busquedaEstrellaMichelin = estrellaMichelinService.getEstrellaMichelinId(estrellaMichelinCreada.getId());
		
		assertEquals(nuevaEstrellaMichelin.getId(), busquedaEstrellaMichelin.getId());
		assertEquals(nuevaEstrellaMichelin.getDescripcion(), busquedaEstrellaMichelin.getDescripcion());
		assertEquals(nuevaEstrellaMichelin.getFechaEntregada(), busquedaEstrellaMichelin.getFechaEntregada());
		assertEquals(nuevaEstrellaMichelin.getTipoEstrella(), busquedaEstrellaMichelin.getTipoEstrella());
	}
	
	/** 
	 * Prueba para actualizar una estrella michelin
	 * @throws EntityNotFoundException
	 */
	@Test
	void testUpdateEstrellaMichelin() throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinList.get(0);
		EstrellaMichelinEntity pojoEntity = factory.manufacturePojo(EstrellaMichelinEntity.class);
		
		pojoEntity.setId(estrellaMichelinEntity.getId());
		
		estrellaMichelinService.updateEstrellaMichelin(estrellaMichelinEntity.getId(), pojoEntity);
		
		EstrellaMichelinEntity response = entityManager.find(EstrellaMichelinEntity.class, estrellaMichelinEntity.getId());
		
		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getDescripcion(), response.getDescripcion());
		assertEquals(pojoEntity.getFechaEntregada(), response.getFechaEntregada());
		assertEquals(pojoEntity.getTipoEstrella(), response.getTipoEstrella());
	}
	
	/**
	 * Prueba para eliminar una estrella michelin
	 * @throws EntityNotFoundException
	 */
	@Test
	void testDeleteEstrellaMichelin() throws EntityNotFoundException {
		EstrellaMichelinEntity estrellaMichelinEntity = estrellaMichelinList.get(0);
		estrellaMichelinService.deleteEstrellaMichelin(estrellaMichelinEntity.getId());
		EstrellaMichelinEntity deleted = entityManager.find(EstrellaMichelinEntity.class, estrellaMichelinEntity.getId());
		assertNull(deleted);
	}

}


