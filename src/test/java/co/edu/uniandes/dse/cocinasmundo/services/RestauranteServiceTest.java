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

import co.edu.uniandes.dse.cocinasmundo.entities.RestauranteEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(RestauranteService.class)
public class RestauranteServiceTest {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<RestauranteEntity> listaRestaurantes = new ArrayList<RestauranteEntity>();
	
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
		entityManager.getEntityManager().createQuery("delete from RestauranteEntity").executeUpdate();
	}
	
	/**
	 * Inserta los datos para las pruebas
	 */
	private void insertData() {
		for(int i=0; i<3; i++) {
			RestauranteEntity restauranteEntity = factory.manufacturePojo(RestauranteEntity.class);
			entityManager.persist(restauranteEntity);
			listaRestaurantes.add(restauranteEntity);
		}
	}
	
	/**
	 * Prueba para obtener los restaurantes
	 */
	@Test
	void testGetRestaurantes() {
		List<RestauranteEntity> list = restauranteService.getRestaurantes();
        assertEquals(list.size(), listaRestaurantes.size());
	}

	/**
	 * Prueba para crear el Restaurante
	 */
	@Test
	void testCreateRestaurante() {
		RestauranteEntity nuevoRestaurante = factory.manufacturePojo(RestauranteEntity.class);
		RestauranteEntity restauranteCreado = restauranteService.createRestaurante(nuevoRestaurante);
		RestauranteEntity busqueda = entityManager.find(RestauranteEntity.class, restauranteCreado.getId());
		
		assertEquals(nuevoRestaurante.getId(), busqueda.getId());
		assertEquals(nuevoRestaurante.getNombreRestaurante(), busqueda.getNombreRestaurante());
		assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		assertEquals(nuevoRestaurante.getDireccionRestaurante(), busqueda.getDireccionRestaurante());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		
	}
	
	/**
	 * Prueba para obtener el restaurante
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testGetRestauranteId() throws EntityNotFoundException {
		RestauranteEntity nuevoRestaurante = factory.manufacturePojo(RestauranteEntity.class);
		RestauranteEntity restauranteCreado = restauranteService.createRestaurante(nuevoRestaurante);
		RestauranteEntity busqueda;
		
		busqueda = restauranteService.getRestauranteId(restauranteCreado.getId());
		assertEquals(nuevoRestaurante.getId(), busqueda.getId());
		assertEquals(nuevoRestaurante.getNombreRestaurante(), busqueda.getNombreRestaurante());
		assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		assertEquals(nuevoRestaurante.getDireccionRestaurante(), busqueda.getDireccionRestaurante());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
	}

	/**
	 * Prueba para actualizar el Restaurante
	 * @throws EntityNotFoundException 
	 */
	@Test
	void testUpdateRestaurante() throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = listaRestaurantes.get(0);
		RestauranteEntity pojoEntity = factory.manufacturePojo(RestauranteEntity.class);

		pojoEntity.setId(restauranteEntity.getId());

		restauranteService.updateRestaurante(restauranteEntity.getId(), pojoEntity);

		RestauranteEntity response = entityManager.find(RestauranteEntity.class, restauranteEntity.getId());

		assertEquals(pojoEntity.getId(), response.getId());
		assertEquals(pojoEntity.getNombreRestaurante(), response.getNombreRestaurante());
		assertEquals(pojoEntity.getCiudad(), response.getCiudad());
		assertEquals(pojoEntity.getDireccionRestaurante(), response.getDireccionRestaurante());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
		//assertEquals(nuevoRestaurante.getCiudad(), busqueda.getCiudad());
	}

	@Test
	void testDeleteRestaurante() throws EntityNotFoundException {
		RestauranteEntity restauranteEntity = listaRestaurantes.get(0);
		restauranteService.deleteRestaurante(restauranteEntity.getId());
		RestauranteEntity deleted = entityManager.find(RestauranteEntity.class, restauranteEntity.getId());
		assertNull(deleted);
	}
	
}
