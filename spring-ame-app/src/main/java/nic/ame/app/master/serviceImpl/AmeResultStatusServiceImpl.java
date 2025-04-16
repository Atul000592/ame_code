package nic.ame.app.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.master.dto.AmeResultStatusDto;

import nic.ame.app.master.repository.IndividualUnitMappedToControllingRepository;
import nic.ame.app.master.service.AmeResultStatusService;

@Service
public class AmeResultStatusServiceImpl implements AmeResultStatusService{
   
	@Autowired
	private IndividualUnitMappedToControllingRepository individualUnitMappedToControllingRepository;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	@Override
	public List<AmeResultStatusDto> getAmeResultStatus(String forcePersonnel) {
		List<Object> AmeResultStatusDtoObjectList=individualUnitMappedToControllingRepository.getResultStatus(forcePersonnel);
		List<AmeResultStatusDto> AmeResultStatusDtoList= new ArrayList<>();
		Iterator<Object> iterator = AmeResultStatusDtoObjectList.iterator();
		
		while(iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			AmeResultStatusDto AmeResultStatusItem = new AmeResultStatusDto();
			if (obj[0] != null) {
				AmeResultStatusItem.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				AmeResultStatusItem.setForcePersonnelId(String.valueOf(obj[1]).trim());
			}if (obj[2] != null) {
				AmeResultStatusItem.setIrlaNo(String.valueOf(obj[2]).trim());
			}if (obj[3] != null) {
				AmeResultStatusItem.setShape(String.valueOf(obj[3]).trim());
			}if (obj[4] != null) {
				AmeResultStatusItem.setCreatedOn((Date)obj[4]);
			}if (obj[5] != null) {
				AmeResultStatusItem.setDeclarationFilePath(String.valueOf(obj[5]).trim());
			}if (obj[6] != null) {
				AmeResultStatusItem.setAmefinalReportFilePath(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				AmeResultStatusItem.setBoardId(String.valueOf(obj[7]).trim());
				AmeResultStatusItem.setBoardYear(medicalBoardRepo.getBoardYear(String.valueOf(obj[7]).trim()));
			}
			AmeResultStatusDtoList.add(AmeResultStatusItem);
			
			
		}
		
		
		return AmeResultStatusDtoList;
	}

}
