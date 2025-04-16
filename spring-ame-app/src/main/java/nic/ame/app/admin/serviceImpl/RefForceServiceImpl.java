package nic.ame.app.admin.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.RefForceService;

@Service
public class RefForceServiceImpl implements RefForceService {

	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private ForceRepo refForceRepo;
	
	@Override
	public String getForceNameByForceId(int forceId) {
	
		
		return refForceRepo.getForceName(forceId);
	}

	@Override
	public String getUnitNameByUnitId(int forceNo,String unitId) {
		
	String unitName=unitRepository.getUnitNameByUnitIdAndForceNo(unitId,forceNo);
		return unitName;
	}

	
}
