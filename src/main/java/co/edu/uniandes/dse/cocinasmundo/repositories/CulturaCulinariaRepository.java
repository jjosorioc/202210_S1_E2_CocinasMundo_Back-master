package co.edu.uniandes.dse.cocinasmundo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;

@Repository

public interface CulturaCulinariaRepository extends JpaRepository<CulturaCulinariaEntity, Long>{

}
