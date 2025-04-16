package nic.ame.app.master.service;


import java.util.List;

import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.dto.IndividuaUnitMappedToControllingAmeAppointment;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;

public interface AmeAppointmentService {
	
	
  public List<AmeDeclarationIndividualModel>getAppointmentPendingList(String forcePersonalId,String RoleName);
  
  public List<AmeDeclarationIndividualModel> getAppointmentProvidedList(String forcePersonalId,String RoleName);
  
  public List<AmeDeclarationIndividualDto>getAmeAppointmentCompletedDetails(String boardId);
  public List<AmeDeclarationIndividualDto>getAmeApplicationStatusDetails(String boardId);


  
  List<IndividuaUnitMappedToControllingAmeAppointment> findAllWithCompletedAppointment(String controllingOfficer);
  
  List<IndividuaUnitMappedToControllingAmeAppointment> findAllWithPendingAppointment();
  
 //  public List<AmeDeclarationIndividualDto> findDataForAMAAppointmentList(String boardId);
   
	

}
