package nic.ame.app.master.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.board.member.repository.PhysicalMeasurmentRepo;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.service.PhysicalMeasurmentService;

@Service
public class PhysicalMeasurmentServiceImpl implements PhysicalMeasurmentService{

	@Autowired
	private PhysicalMeasurmentRepo physicalMeasurmentRepo;
	
	public PhysicalMeasurement getPhysicalMeasurementData(String ameId) {
		
		Optional<PhysicalMeasurement> optional=physicalMeasurmentRepo.findById(ameId);
		PhysicalMeasurement physicalMeasurement=new PhysicalMeasurement();
		if(!optional.isEmpty()) {
			physicalMeasurement=optional.get();
	        return physicalMeasurement;
		}
		
		return physicalMeasurement;
		
	}
	
}
