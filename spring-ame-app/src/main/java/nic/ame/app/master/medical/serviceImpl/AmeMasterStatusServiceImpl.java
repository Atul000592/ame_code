package nic.ame.app.master.medical.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.board.member.repository.AmeMasterStatusRepo;
import nic.ame.app.master.medical.dto.AmeShapeDto;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.service.AmeMasterStatusService;

@Service
public class AmeMasterStatusServiceImpl implements AmeMasterStatusService{

	@Autowired
	private AmeMasterStatusRepo ameMasterStatusRepo;
	
	@Override
	public AmeMasterStatus getAmeMasterStatus(String ameId) {
		AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		Optional<AmeMasterStatus> optional=ameMasterStatusRepo.findByAmeId(ameId);
		if(!optional.isEmpty()) {
			ameMasterStatus=optional.get();
		return ameMasterStatus;
		}
		return ameMasterStatus;
	}

	@Override
	public AmeShapeDto CheckForShapeStatusAndValues(String shape) {
		
		return null;
	}

}
