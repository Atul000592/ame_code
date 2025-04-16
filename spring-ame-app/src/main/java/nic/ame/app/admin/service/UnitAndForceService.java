package nic.ame.app.admin.service;

import java.util.List;

import nic.ame.app.admin.model.UnitModel;

public interface UnitAndForceService {

	
	List<UnitModel> getUnitDropDown(int forceId);
}
