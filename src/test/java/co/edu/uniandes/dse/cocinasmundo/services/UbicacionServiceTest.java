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

import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(UbicacionService.class)
public class UbicacionServiceTest {
	
	@Autowired
	private UbicacionService ubicacionService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<UbicacionEntity> listaUbicaciones = new ArrayList<UbicacionEntity>();
	
	/**
	 * Métodos de inicialización
	 * -------------------------------------------------------------------------------------------------
	 */
	
	
	/**
	 * Configuración inicial
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	/**
	 * Limpia las tablas
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from UbicacionEntity").executeUpdate();
	}
	
	/**
	 * Inserta los datos pa las pruebas
	 */
	private void insertData() {
		for(int i=0; i<3; i++) {
			UbicacionEntity ubicacionEntity = factory.manufacturePojo(UbicacionEntity.class);
			entityManager.persist(ubicacionEntity);
			listaUbicaciones.add(ubicacionEntity);
		}
	}
	/**
	 * -------------------------------------------------------------------------------------------------
	 */

	/**
	 * Prueba para obtener las ubicaciones
	 */
	@Test
	void testGetUbicaciones() {
		List<UbicacionEntity> list = ubicacionService.getUbicaciones();
        assertEquals(list.size(), listaUbicaciones.size());
	}

	/**
	 * Prueba para crear la Ubicacion
	 */
	@Test
	void testCreateUbicacion() {
		UbicacionEntity nuevoUbicacion = factory.manufacturePojo(UbicacionEntity.class);
		UbicacionEntity ubicacionCreado = ubicacionService.createUbicacion(nuevoUbicacion);
		UbicacionEntity busqueda = entityManager.find(UbicacionEntity.class, ubicacionCreado.getId());
		
		assertEquals(nuevoUbicacion.getId(), busqueda.getId());
		assertEquals(nuevoUbicacion.getLatitudUbicacion(), busqueda.getLatitudUbicacion());
		assertEquals(nuevoUbicacion.getLongitudUbicacion(), busqueda.getLongitudUbicacion());
	}
	
	/**
	 * Prueba para obtener la Ubicacion
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testGetUbicacionId() throws EntityNotFoundException {
		UbicacionEntity nuevoUbicacion = factory.manufacturePojo(UbicacionEntity.class);
		UbicacionEntity ubicacionCreado = ubicacionService.createUbicacion(nuevoUbicacion);
		UbicacionEntity busqueda;
		
		busqueda = ubicacionService.getUbicacionId(ubicacionCreado.getId());
		assertEquals(nuevoUbicacion.getId(), busqueda.getId());
		assertEquals(nuevoUbicacion.getLatitudUbicacion(), busqueda.getLatitudUbicacion());
		assertEquals(nuevoUbicacion.getLongitudUbicacion(), busqueda.getLongitudUbicacion());
	}

	/**
	 * Prueba para actualizar la Ubicacion
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testUpdateUbicacion() throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = listaUbicaciones.get(0);
		UbicacionEntity pojoEntity = factory.manufacturePojo(UbicacionEntity.class);

		pojoEntity.setId(ubicacionEntity.getId());

		ubicacionService.updateUbicacion(ubicacionEntity.getId(), pojoEntity);

		UbicacionEntity response = entityManager.find(UbicacionEntity.class, ubicacionEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getLatitudUbicacion(), response.getLatitudUbicacion());
		assertEquals(pojoEntity.getLongitudUbicacion(), response.getLongitudUbicacion());
	}

	@Test
	void testDeleteUbicacion() throws EntityNotFoundException {
		UbicacionEntity ubicacionEntity = listaUbicaciones.get(0);
		ubicacionService.deleteUbicacion(ubicacionEntity.getId());
		UbicacionEntity deleted = entityManager.find(UbicacionEntity.class, ubicacionEntity.getId());
		assertNull(deleted);
	}
}
