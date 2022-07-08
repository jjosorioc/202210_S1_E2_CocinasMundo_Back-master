package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.cocinasmundo.entities.CulturaCulinariaEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CulturaCulinariaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class CulturaCulinariaService {
	
	@Autowired
    private CulturaCulinariaRepository culturaCulinariaRepository;  
	
	@Transactional
    public List<CulturaCulinariaEntity> getCulturasCulinarias() {
            return culturaCulinariaRepository.findAll();
    }
    
    @Transactional
    public CulturaCulinariaEntity getCulturaCulinariaId(Long ccId) throws EntityNotFoundException {
        Optional<CulturaCulinariaEntity> culturaCulinaria = culturaCulinariaRepository.findById(ccId);
        if (culturaCulinaria.isEmpty()) {
            throw new EntityNotFoundException("La cultura culinaria buscada no existe");
        }
        return culturaCulinaria.get();
}

    @Transactional
    public CulturaCulinariaEntity createCulturaCulinaria(CulturaCulinariaEntity culturaCulinaria) {
            return culturaCulinariaRepository.save(culturaCulinaria);
    }
    
    
    @Transactional
    public CulturaCulinariaEntity updateCulturaCulinaria(Long culturaCulinariaId, CulturaCulinariaEntity culturaCulinaria) throws EntityNotFoundException {
        Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
        if (culturaCulinariaEntity.isEmpty()) {
            throw new EntityNotFoundException("La cultura culinaria a actualizar no existe");
        }
        culturaCulinaria.setId(culturaCulinariaId);
        return culturaCulinariaRepository.save(culturaCulinaria);
    }
    

    @Transactional
    public void deleteCulturaCulinaria(Long culturaCulinariaId) throws EntityNotFoundException {
        Optional<CulturaCulinariaEntity> culturaCulinariaEntity = culturaCulinariaRepository.findById(culturaCulinariaId);
        if (culturaCulinariaEntity.isEmpty()) {
            throw new EntityNotFoundException("La cultura culinaria a eliminar no existe");
        }
        culturaCulinariaRepository.deleteById(culturaCulinariaId);
    }
}
