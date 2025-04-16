package nic.ame.app.master.serviceImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.ForcePersonnelService;

@Service
public class ForcePersonnelServiceImpl implements ForcePersonnelService {

	@Autowired
	ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private RefForceService refForceService;
	
	@Override
	public Optional<ForcePersonnelDto> getForcePersonnelDetailsByForcePersonnelId(String forcePersonalId) {
	   
	    Optional<Object> forcePersonnelDtoObjectOptional = forcePersonnelRepository.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);

	   
	    if (forcePersonnelDtoObjectOptional.isPresent() && forcePersonnelDtoObjectOptional.get() instanceof Object[]) {
	        Object[] obj = (Object[]) forcePersonnelDtoObjectOptional.get();

	        
	        ForcePersonnelDto forcePersonnelDto = new ForcePersonnelDto();

	      
	        if (obj.length > 0 && obj[0] != null) {
	            forcePersonnelDto.setForcePersonalId(String.valueOf(obj[0]));
	        }

	        if (obj.length > 1 && obj[1] != null) {
	            forcePersonnelDto.setForceId(String.valueOf(obj[1]));
	        }

	        if (obj.length > 2 && obj[2] != null) {
	            forcePersonnelDto.setName(String.valueOf(obj[2]));
	        }

	        if (obj.length > 3 && obj[3] != null) {
	            forcePersonnelDto.setForceNo(Integer.parseInt(String.valueOf(obj[3])));
	        }

	        if (obj.length > 4 && obj[4] != null) {
	            forcePersonnelDto.setLastAmeDate((Date)obj[4]);
	        }

	        if (obj.length > 5 && obj[5] != null) {
	            forcePersonnelDto.setJoiningDate((Date)obj[5]);
	        }

	        if (obj.length > 6 && obj[6] != null) {
	            forcePersonnelDto.setDojPresentRank((Date)obj[6]);
	        }

	        if (obj.length > 7 && obj[7] != null) {
	            forcePersonnelDto.setDob((Date)obj[7]);
	        }

	        if (obj.length > 8 && obj[8] != null) {
	            forcePersonnelDto.setEmailId(String.valueOf(obj[8]));
	        }

	        if (obj.length > 9 && obj[9] != null) {
	            forcePersonnelDto.setGazettedNonGazettedFlag(String.valueOf(obj[9]));
	        }

	        if (obj.length > 10 && obj[10] != null) {
	            forcePersonnelDto.setMobileNumber(BigInteger.valueOf(Long.parseLong((String.valueOf(obj[10])))));
	        }

	        if (obj.length > 11 && obj[11] != null) {
	            forcePersonnelDto.setLastAmeShape(String.valueOf(obj[11]));
	        }

	        if (obj.length > 12 && obj[12] != null) {
	            forcePersonnelDto.setPreviousAmeCategory(String.valueOf(obj[12]));
	        }

	        if (obj.length > 13 && obj[13] != null) {
	            forcePersonnelDto.setAmePlace(String.valueOf(obj[13]));
	        }

	        if (obj.length > 14 && obj[14] != null) {
	            forcePersonnelDto.setRank(String.valueOf(obj[14]));
	            forcePersonnelDto.setDesignation(String.valueOf(obj[14]));
	        }

	        if (obj.length > 15 && obj[15] != null) {
	            forcePersonnelDto.setMaritalStatus(String.valueOf(obj[15]));
	        }

	        if (obj.length > 16 && obj[16] != null) {
	            forcePersonnelDto.setGender(String.valueOf(obj[16]));
	        }

	        if (obj.length > 17 && obj[17] != null) {
	            forcePersonnelDto.setUnit(String.valueOf(obj[17]));
	        }
	        
	        if (obj.length > 18 && obj[18] != null) {
	            forcePersonnelDto.setUnitName(String.valueOf(obj[18]));
	        }
	        
	        if (obj.length > 19 && obj[19] != null) {
	            forcePersonnelDto.setAttachUnit(String.valueOf(obj[19]));
	            forcePersonnelDto.setAttachUnitName(refForceService.getUnitNameByUnitId(forcePersonnelDto.getForceNo(),forcePersonnelDto.getAttachUnit()));
	        }
	        
	        if (obj.length > 20 && obj[20] != null) {
	            forcePersonnelDto.setJobStatus(String.valueOf(obj[20]));
	        }

	        if (obj.length > 21 && obj[21] != null) {
	            forcePersonnelDto.setForceName(String.valueOf(obj[21]));
	        }

	        if (obj.length> 22 && obj[22] != null) {
	            forcePersonnelDto.setForceCodeName(String.valueOf(obj[22]));
	        }
	        
	        if (obj.length > 23 && obj[23] != null) {
	            forcePersonnelDto.setRankSubCode(String.valueOf(obj[23]));
	           }

	      
	        return Optional.of(forcePersonnelDto);
	    }

	   
	    return Optional.empty();
	}

	@Override
	public Optional<ForcePersonnelDto> getForcePersonnelDetailsByIrlaNo(String irlaNo) {
		   Optional<Object> forcePersonnelDtoObjectOptional = forcePersonnelRepository.getForcePersonnelDetailsByIralNo(irlaNo);

		   
		    if (forcePersonnelDtoObjectOptional.isPresent() && forcePersonnelDtoObjectOptional.get() instanceof Object[]) {
		        Object[] obj = (Object[]) forcePersonnelDtoObjectOptional.get();

		        
		        ForcePersonnelDto forcePersonnelDto = new ForcePersonnelDto();

		      
		        if (obj.length > 0 && obj[0] != null) {
		            forcePersonnelDto.setForcePersonalId(String.valueOf(obj[0]));
		        }

		        if (obj.length > 1 && obj[1] != null) {
		            forcePersonnelDto.setForceId(String.valueOf(obj[1]));
		        }

		        if (obj.length > 2 && obj[2] != null) {
		            forcePersonnelDto.setName(String.valueOf(obj[2]));
		        }

		        if (obj.length > 3 && obj[3] != null) {
		            forcePersonnelDto.setForceNo(Integer.parseInt(String.valueOf(obj[3])));
		        }

		        if (obj.length > 4 && obj[4] != null) {
		            forcePersonnelDto.setLastAmeDate((Date)obj[4]);
		        }

		        if (obj.length > 5 && obj[5] != null) {
		            forcePersonnelDto.setJoiningDate((Date)obj[5]);
		        }

		        if (obj.length > 6 && obj[6] != null) {
		            forcePersonnelDto.setDojPresentRank((Date)obj[6]);
		        }

		        if (obj.length > 7 && obj[7] != null) {
		            forcePersonnelDto.setDob((Date)obj[7]);
		        }

		        if (obj.length > 8 && obj[8] != null) {
		            forcePersonnelDto.setEmailId(String.valueOf(obj[8]));
		        }

		        if (obj.length > 9 && obj[9] != null) {
		            forcePersonnelDto.setGazettedNonGazettedFlag(String.valueOf(obj[9]));
		        }

		        if (obj.length > 10 && obj[10] != null) {
		            forcePersonnelDto.setMobileNumber(BigInteger.valueOf(Long.parseLong((String.valueOf(obj[10])))));
		        }

		        if (obj.length > 11 && obj[11] != null) {
		            forcePersonnelDto.setLastAmeShape(String.valueOf(obj[11]));
		        }

		        if (obj.length > 12 && obj[12] != null) {
		            forcePersonnelDto.setPreviousAmeCategory(String.valueOf(obj[12]));
		        }

		        if (obj.length > 13 && obj[13] != null) {
		            forcePersonnelDto.setAmePlace(String.valueOf(obj[13]));
		        }

		        if (obj.length > 14 && obj[14] != null) {
		            forcePersonnelDto.setRank(String.valueOf(obj[14]));
		            forcePersonnelDto.setDesignation(String.valueOf(obj[14]));
		        }

		        if (obj.length > 15 && obj[15] != null) {
		            forcePersonnelDto.setMaritalStatus(String.valueOf(obj[15]));
		        }

		        if (obj.length > 16 && obj[16] != null) {
		            forcePersonnelDto.setGender(String.valueOf(obj[16]));
		        }

		        if (obj.length > 17 && obj[17] != null) {
		            forcePersonnelDto.setUnit(String.valueOf(obj[17]));
		        }
		        
		        if (obj.length > 18 && obj[18] != null) {
		            forcePersonnelDto.setUnitName(String.valueOf(obj[18]));
		        }
		        
		        if (obj.length > 19 && obj[19] != null) {
		            forcePersonnelDto.setAttachUnit(String.valueOf(obj[19]));
		            forcePersonnelDto.setAttachUnitName(refForceService.getUnitNameByUnitId(forcePersonnelDto.getForceNo(),forcePersonnelDto.getAttachUnit()));
		        }
		        
		        if (obj.length > 20 && obj[20] != null) {
		            forcePersonnelDto.setJobStatus(String.valueOf(obj[20]));
		        }

		        if (obj.length > 21 && obj[21] != null) {
		            forcePersonnelDto.setForceName(String.valueOf(obj[21]));
		        }

		        if (obj.length> 22 && obj[22] != null) {
		            forcePersonnelDto.setForceCodeName(String.valueOf(obj[22]));
		        }
		        
		        if (obj.length > 23 && obj[23] != null) {
		            forcePersonnelDto.setRankSubCode(String.valueOf(obj[23]));
		           }

		      
		        return Optional.of(forcePersonnelDto);
		    }

		   
		    return Optional.empty();
	}

}
