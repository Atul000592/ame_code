package nic.ame.master.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.model.AmeDeclarationDtoResponse;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;



@Service
public class AllDeclarationRecord {

	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	public AmeDeclarationDtoResponse getalldeclaration(String forcePersonalId,String unitId) {
		
		
		return  new AmeDeclarationDtoResponse();
		
	}
	
	
		
	
}
