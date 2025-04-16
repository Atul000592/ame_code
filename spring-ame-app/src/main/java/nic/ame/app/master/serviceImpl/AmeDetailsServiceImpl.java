package nic.ame.app.master.serviceImpl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.AmeDetailsDto;
import nic.ame.app.master.repository.AmeDetailsRepository;
import nic.ame.app.master.service.AmeDetailsService;

@Service
public class AmeDetailsServiceImpl implements AmeDetailsService {

	@Autowired
	AmeDetailsRepository ameDetailsRepository;
	
	
	@Override
	public Optional<AmeDetailsDto> getCandidateAmeDetailsByAmeId(String ameId) {
		
	Optional<Object> ameDetailsDtoObjectList=ameDetailsRepository.getCandidateAmeDetailsByAmeId(ameId);
	AmeDetailsDto ameDetailsDto= new AmeDetailsDto();      
	    if(ameDetailsDtoObjectList.isPresent()) {
	    	Object[] obj =(Object[])ameDetailsDtoObjectList.get();

	        if (obj.length > 0 && obj[0] != null) {
	        	ameDetailsDto.setForcePersonalId(String.valueOf(obj[0]).trim());        
	        	}
	        
	        if (obj.length > 0 && obj[1] != null) {
	        	ameDetailsDto.setForceId(String.valueOf(obj[1]).trim());        
	        	}
	        
	        if (obj.length > 0 && obj[2] != null) {
	        	ameDetailsDto.setName(String.valueOf(obj[2]).trim());        
	        	}
	        
	        if (obj.length > 0 && obj[3] != null) {
	        	ameDetailsDto.setDob((Date)obj[3]);        
	        	}
	        if (obj.length > 0 && obj[4] != null) {
	        	ameDetailsDto.setAge(Integer.parseInt(String.valueOf(obj[4]).trim()));        
	        	}
	        if (obj.length > 0 && obj[5] != null) {
	        	ameDetailsDto.setUnitName(String.valueOf(obj[5]).trim());        
	        	}
	        if (obj.length > 0 && obj[6] != null) {
	        	ameDetailsDto.setForceName(String.valueOf(obj[6]).trim());        
	        	}
	        if (obj.length > 0 && obj[7] != null) {
	        	ameDetailsDto.setForceCodeName(String.valueOf(obj[7]).trim());        
	        	}
	        
	        if (obj.length > 0 && obj[8] != null) {
	        	ameDetailsDto.setLastAmeDate((Date)obj[8]);        
	        	}
	        if (obj.length > 0 && obj[9] != null) {
	        	ameDetailsDto.setAmePlace(String.valueOf(obj[9]).trim());        
	        	}
	        if (obj.length > 0 && obj[10] != null) {
	        	ameDetailsDto.setJoiningDate((Date)obj[10]);        
	        	}
	        if (obj.length > 0 && obj[11] != null) {
	        	ameDetailsDto.setGazettedNonGazettedFlag(String.valueOf(obj[11]).trim());        
	        	}
	        
            if (obj.length > 0 && obj[12] != null) {
	        	ameDetailsDto.setRank(String.valueOf(obj[12]).trim());
	        	ameDetailsDto.setDesignation(String.valueOf(obj[12]).trim());
	        	}
            if (obj.length > 0 && obj[13] != null) {
	        	ameDetailsDto.setGender(String.valueOf(obj[13]).trim());        
	        	}
	    	
	    	
	    	
	    }	
		
		return Optional.ofNullable(ameDetailsDto);
	}

}
