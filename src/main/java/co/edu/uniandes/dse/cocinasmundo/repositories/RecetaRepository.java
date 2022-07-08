package co.edu.uniandes.dse.cocinasmundo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.RecetaEntity;

/**
 * Interfaz que persiste a la receta
 *
 * @author Alejandro Tovar
 *
 */
@Repository
public interface RecetaRepository extends JpaRepository<RecetaEntity, Long> {

}
