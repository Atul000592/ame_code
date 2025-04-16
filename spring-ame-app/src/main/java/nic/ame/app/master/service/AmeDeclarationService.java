package nic.ame.app.master.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import jakarta.transaction.Transactional;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.dto.AppointmentListDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.RequestDto;
import nic.ame.app.master.model.ResponseDto;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.constant.CommonConstant;


@Service
@Transactional
public class AmeDeclarationService {

	
	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;
	

	
	
	
	
	public ResponseDto savedeclaration(RequestDto requestDto  ) {
		
		ameDeclarationIndividualDetailsRepo.save(requestDto.getAmeDeclarationIndividualDetails());
		ameDeclarationRepository.save(requestDto.getDeclarationIndividualModel());
		
		
		
		return new ResponseDto();
		
	}
	
	
	public List<AmeApplicationFlowStatusDto> getCandidateApplicationStatusFlow(String candidateForcePersonnelId){
		
		
		
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList = new ArrayList<>();
		List<Object> objectsList= ameDeclarationRepository.getAmeStatusFLowForIndividual(candidateForcePersonnelId);
		
		if(objectsList.isEmpty()) {
			return ameApplicationFlowStatusDtosList;
		}
       	Iterator<Object> iterator=objectsList.iterator();
       
		while(iterator.hasNext()) {
			
		    Object[] obj=(Object[]) iterator.next();
       		
		    AmeApplicationFlowStatusDto ameApplicationFlowStatusDto=new AmeApplicationFlowStatusDto();
			
			if (obj[0] != null) {
                ameApplicationFlowStatusDto.setAmeId((String.valueOf(obj[0])).trim());
			}
			if (obj[1] != null) {
				 ameApplicationFlowStatusDto.setForceId((String.valueOf(obj[1])).trim()); 
			}
			if (obj[2] != null) {
				 ameApplicationFlowStatusDto.setForcePersonalId((String.valueOf(obj[2])).trim());
				
			}
			ameApplicationFlowStatusDtosList.add(ameApplicationFlowStatusDto);
		
	}
		return ameApplicationFlowStatusDtosList;
		
	}
}
