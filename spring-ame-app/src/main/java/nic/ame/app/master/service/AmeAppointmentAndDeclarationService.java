package nic.ame.app.master.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.master.dto.AppointmentListDto;

public interface AmeAppointmentAndDeclarationService {
	
	
	
 public void saveAmeAppointmentDetails(String candidateForcePersonalId,String forcepersonalIdLogin,String fromDate,String toDate,String remark,String boardId,HttpServletRequest request);
 public List<AppointmentListDto> getAppointmentDtoList(String boardId);
 public int appointmentCompletedCount(String boardId);
 public int appointmentPendingCount(String boardId);
	

}
