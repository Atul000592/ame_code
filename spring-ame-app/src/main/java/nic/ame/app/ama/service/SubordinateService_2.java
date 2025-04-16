package nic.ame.app.ama.service;

import java.util.List;

import nic.ame.app.master.model.AmeDeclarationIndividualModel;

public interface SubordinateService_2 {

	
	public List<AmeDeclarationIndividualModel> getDeclarationListForAMA(String amaForcePersonalId,String roleName);
	public List<AmeDeclarationIndividualModel> getDeclarationListForAMANew(String amaForcePersonalId,String roleName);

	
	
}
