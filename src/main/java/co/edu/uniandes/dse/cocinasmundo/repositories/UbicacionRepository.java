package co.edu.uniandes.dse.cocinasmundo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.UbicacionEntity;
/**
 * Interfaz que persiste la ubicacion
*/
@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Long>{
    
}
