package nic.ame.app.admin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.UnitAndForceService;

@Service
public class UnitAndForceServiceImpl implements UnitAndForceService {
	
	@Autowired
	private UnitRepository unitRepository;

	@Override
	public List<UnitModel> getUnitDropDown(int forceId) {
		List<UnitModel> unitList=unitRepository.getUnitDataByForceId(forceId);
		if(unitList.size()<0) {
			return null;
		}
		return unitList;
	}
	
	
	

}
