package co.edu.uniandes.dse.cocinasmundo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({CulturaCulinariaService.class, CulturaCulinariaPaisService.class})
public class CulturaCulinariaPaisServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CulturaCulinariaPaisService culturaCulinariaPaisService;

	@Autowired
	private CulturaCulinariaService culturaCulinariaService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<PaisEntity> paissList = new ArrayList<>();
	private List<CulturaCulinariaEntity> culturaCulinariasList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from CulturaCulinariaEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from PaisEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			CulturaCulinariaEntity culturaCulinarias = factory.manufacturePojo(CulturaCulinariaEntity.class);
			entityManager.persist(culturaCulinarias);
			culturaCulinariasList.add(culturaCulinarias);
		}
		for (int i = 0; i < 3; i++) {
			PaisEntity entity = factory.manufacturePojo(PaisEntity.class);
			entityManager.persist(entity);
			paissList.add(entity);
			if (i == 0) {
				culturaCulinariasList.get(i).setPais(entity);
			}
		}
	}

	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Pais.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplacePais() throws EntityNotFoundException {
		CulturaCulinariaEntity entity = culturaCulinariasList.get(0);
		culturaCulinariaPaisService.replacePais(entity.getId(), paissList.get(1).getId());
		entity = culturaCulinariaService.getCulturaCulinariaId(entity.getId());
		assertEquals(entity.getPais(), paissList.get(1));
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Pais con un libro que no existe
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplacePaisInvalidCulturaCulinaria() {
		assertThrows(EntityNotFoundException.class, ()->{
			culturaCulinariaPaisService.replacePais(0L, paissList.get(1).getId());
		});
	}
	
	/**
	 * Prueba para remplazar las instancias de CulturaCulinarias asociadas a una instancia de
	 * Pais que no existe.
	 * 
	 * @throws EntityNotFoundException
	 */
	@Test
	void testReplaceInvalidPais() {
		assertThrows(EntityNotFoundException.class, ()->{
			CulturaCulinariaEntity entity = culturaCulinariasList.get(0);
			culturaCulinariaPaisService.replacePais(entity.getId(), 0L);
		});
	}

	/**
	 * Prueba para desasociar un CulturaCulinaria existente de un Pais existente
	 * 
	 * @throws EntityNotFoundException
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	void testRemovePais() throws EntityNotFoundException {
		culturaCulinariaPaisService.removePais(culturaCulinariasList.get(0).getId());
		CulturaCulinariaEntity response = culturaCulinariaService.getCulturaCulinariaId(culturaCulinariasList.get(0).getId());
		assertNull(response.getPais());
	}
	
	/**
	 * Prueba para desasociar un CulturaCulinaria que no existe de un Pais
	 * 
	 * @throws EntityNotFoundException
	 *
	 * @throws co.edu.uniandes.csw.culturaCulinariastore.exceptions.BusinessLogicException
	 */
	@Test
	void testRemovePaisInvalidCulturaCulinaria() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, ()->{
			culturaCulinariaPaisService.removePais(0L);
		});
	}
	
}