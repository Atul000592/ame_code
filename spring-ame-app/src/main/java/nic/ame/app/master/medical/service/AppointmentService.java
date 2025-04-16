package nic.ame.app.master.medical.service;

import java.util.List;
import java.util.Optional;

import nic.ame.app.master.dto.AmeAppointmentDto;

import nic.ame.app.master.model.TTAppointmentAme;

public interface AppointmentService {
	
	
	Optional<TTAppointmentAme> getAmeAppointmentStatus(String forcePersonalId,String boardId);
	
    public int updateDeclarationStatus(String boardId,String forcePersonalId);
    
    public int createAppointmentfortheForcePersonalByAme(List<AmeAppointmentDto> Data);
    
    public AmeAppointmentDto checkValidationForAppointmentDate(Optional<TTAppointmentAme> optional);
	
}
