package co.edu.uniandes.dse.cocinasmundo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.PlatoEntity;

@Repository
public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {

}
