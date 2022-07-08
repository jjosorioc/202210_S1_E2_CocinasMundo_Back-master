package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.CiudadEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.CiudadRepository;

@Service
public class CiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;
	
	
    @Transactional
    public List<CiudadEntity> getCiudades() {
            return ciudadRepository.findAll();
    }
    
    
    @Transactional
    public CiudadEntity getCiudadId(Long ciudadId) throws EntityNotFoundException {
    	
    		Optional<CiudadEntity> ciudad = ciudadRepository.findById(ciudadId);
    		if (ciudad.isEmpty()) {
    			throw new EntityNotFoundException("La ciudad buscada no existe");
    		}
            return ciudad.get();
    }
    
  
    @Transactional
    public CiudadEntity createCiudad(CiudadEntity ciudad) {
            return ciudadRepository.save(ciudad);
    }
    
  
	@Transactional
	public CiudadEntity updateCiudad(Long ciudadId, CiudadEntity ciudad) throws EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty()) {
			throw new EntityNotFoundException("La ciudad a actualizar no existe");
		}
		ciudad.setId(ciudadId);
		return ciudadRepository.save(ciudad);
	}
	
	
	@Transactional
	public void deleteCiudad(Long ciudadId) throws EntityNotFoundException {
		Optional<CiudadEntity> ciudadEntity = ciudadRepository.findById(ciudadId);
		if (ciudadEntity.isEmpty()) {
			throw new EntityNotFoundException("La ciudad a eliminar no existe");
		}
		ciudadRepository.deleteById(ciudadId);
	}
}
