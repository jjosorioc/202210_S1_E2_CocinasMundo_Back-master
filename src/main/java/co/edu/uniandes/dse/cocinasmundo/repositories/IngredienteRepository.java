package co.edu.uniandes.dse.cocinasmundo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.IngredienteEntity;

/**
 * Interfaz que persiste al ingrediente
 *
 * @author Alejandro Tovar
 *
 */
@Repository
public interface IngredienteRepository extends JpaRepository<IngredienteEntity, Long> {

}