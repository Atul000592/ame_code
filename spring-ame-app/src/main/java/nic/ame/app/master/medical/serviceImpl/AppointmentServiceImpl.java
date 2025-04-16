package nic.ame.app.master.medical.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.AmeAppointmentDto;
import nic.ame.app.master.medical.service.AppointmentService;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.master.util.CompareDateUtil;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private TTAppointmentAmeRepo appointmentAmeRepo;
	
	
	@Override
	public Optional<TTAppointmentAme> getAmeAppointmentStatus(String forcePersonalId,String boardId) {
		
		Optional<TTAppointmentAme> optional=appointmentAmeRepo.getAppointmentStatusForForcePersonal(forcePersonalId.trim(),boardId);
		if(!optional.isEmpty()) {
			return optional;
		}
		
		return Optional.empty();
	}

	@Override
	public int updateDeclarationStatus(String boardId, String forcePersonalId) {
	
		return 0;
	}

	@Override
	public int createAppointmentfortheForcePersonalByAme(List<AmeAppointmentDto> Data) {
		
		return 0;
	}
	
	public AmeAppointmentDto checkValidationForAppointmentDate(Optional<TTAppointmentAme> optional) {
		
		AmeAppointmentDto f=new AmeAppointmentDto();
		
		if(!optional.isEmpty()) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+optional.get().getFromDate());
		    if(CompareDateUtil.ValidateDate(optional.get().getFromDate())==1) {
		    	f.setBoardId(optional.get().getBoardId());
		    	f.setForcePersonalId(optional.get().getForcePersonalId());
		    	f.setFromDate(optional.get().getFromDate());
		    	return f;
		    }
		    else {
			      return null;
		    }
		}
		
		return null;
	}

}
