package co.edu.uniandes.dse.cocinasmundo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.cocinasmundo.entities.RegionEntity;
import co.edu.uniandes.dse.cocinasmundo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.cocinasmundo.repositories.RegionRepository;

@Service
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	
    @Transactional
    public List<RegionEntity> getRegiones() {
            return regionRepository.findAll();
    }
    
    
    @Transactional
    public RegionEntity getRegionId(Long regionId) throws EntityNotFoundException {
    	
    		Optional<RegionEntity> region = regionRepository.findById(regionId);
    		if (region.isEmpty()) {
    			throw new EntityNotFoundException("La región buscada no existe");
    		}
            return region.get();
    }
    
  
    @Transactional
    public RegionEntity createRegion(RegionEntity region) {
            return regionRepository.save(region);
    }
    
  
	@Transactional
	public RegionEntity updateRegion(Long regionId, RegionEntity region) throws EntityNotFoundException {
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty()) {
			throw new EntityNotFoundException("La región a actualizar no existe");
		}
		region.setId(regionId);
		return regionRepository.save(region);
	}
	
	
	@Transactional
	public void deleteRegion(Long regionId) throws EntityNotFoundException {
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);
		if (regionEntity.isEmpty()) {
			throw new EntityNotFoundException("La región a eliminar no existe");
		}
		regionRepository.deleteById(regionId);
	}
}
