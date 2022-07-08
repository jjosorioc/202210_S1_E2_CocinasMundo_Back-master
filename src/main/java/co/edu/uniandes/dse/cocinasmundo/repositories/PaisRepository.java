package co.edu.uniandes.dse.cocinasmundo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.PaisEntity;

@Repository
public interface PaisRepository extends JpaRepository<PaisEntity, Long> {
	List<PaisEntity> findByNombre(String nombre);
}
