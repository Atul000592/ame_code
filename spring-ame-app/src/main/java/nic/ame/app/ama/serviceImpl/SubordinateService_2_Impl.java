package nic.ame.app.ama.serviceImpl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.ama.service.SubordinateService_2;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;

@Service
public class SubordinateService_2_Impl  implements SubordinateService_2 {

	
	@Autowired
	private AmeDeclarationIndividualModelRepoUser declarationIndividualModelRepoUser;
	
	@Override
	public List<AmeDeclarationIndividualModel> getDeclarationListForAMA(String amaForcePersonalId,String roleName) {
		List<AmeDeclarationIndividualModel> individualModelsList=declarationIndividualModelRepoUser.findByAMADeclarationList(amaForcePersonalId, roleName);
		
		if(individualModelsList.size()==0) {
			return Collections.emptyList();
		}
		
		return individualModelsList;
	}
	
	@Override
	public List<AmeDeclarationIndividualModel> getDeclarationListForAMANew(String amaForcePersonalId,String roleName) {
		List<AmeDeclarationIndividualModel> individualModelsList=declarationIndividualModelRepoUser.findByAMADeclarationListNew(amaForcePersonalId, roleName);
		if(individualModelsList.size()==0) {
			return Collections.emptyList();
		}
		
		return individualModelsList;
	}

}
