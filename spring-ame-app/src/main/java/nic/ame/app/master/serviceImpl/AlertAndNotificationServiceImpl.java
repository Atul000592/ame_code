package nic.ame.app.master.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.IndividualAlertAndNotificationDto;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.service.AlertAndNotificationService;


@Service
public class AlertAndNotificationServiceImpl implements AlertAndNotificationService {
    
	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;
	
	
	@Override
	public List<IndividualAlertAndNotificationDto> findListOfAlertsAndNotificationsByForcePersonnelId(
			String forcePersonnelId) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		List<Object> objectListForIndividualAlertAndNotification=alertAndNotificationRepository.getListOfAlertsAndNotificationsByForcePersonnelId(forcePersonnelId);
		List<IndividualAlertAndNotificationDto> individualAlertAndNotificationDtoList= new ArrayList<>();
		Iterator<Object> iterator = objectListForIndividualAlertAndNotification.iterator();
		while (iterator.hasNext()) {
			IndividualAlertAndNotificationDto individualAlertAndNotificationDto = new IndividualAlertAndNotificationDto();
			Object[] obj = (Object[]) iterator.next();
			if(obj[0]!=null) {
				individualAlertAndNotificationDto.setExamType(String.valueOf(obj[0]).trim());
				
			}
			if(obj[1]!=null) {
				individualAlertAndNotificationDto.setTitle(String.valueOf(obj[1]).trim());
				
			}
			if(obj[2]!=null) {
				individualAlertAndNotificationDto.setDescription(String.valueOf(obj[2]).trim());
				
			}
			if(obj[3]!=null) {
				
				
				individualAlertAndNotificationDto.setDateOfEvent(simpleDateFormat.format((Date)obj[3]).trim());
				
			}
			individualAlertAndNotificationDtoList.add(individualAlertAndNotificationDto);
		}
		
		
		
		return individualAlertAndNotificationDtoList;
	}


	@Override
	public List<IndividualAlertAndNotificationDto> alertAndNotifications(String forcePersonnelId) {
		List<AlertAndNotification> objectListForIndividualAlertAndNotification=	
				alertAndNotificationRepository.findByReceiverForcePersonnelIdOrderByLastModifiedOnDesc(forcePersonnelId);
		List<IndividualAlertAndNotificationDto> individualAlertAndNotificationDtoList= new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		if(objectListForIndividualAlertAndNotification.size()>0) {
			for (AlertAndNotification alertAndNotification : objectListForIndividualAlertAndNotification) {
				IndividualAlertAndNotificationDto individualAlertAndNotificationDto=new IndividualAlertAndNotificationDto();
				individualAlertAndNotificationDto.setExamType(alertAndNotification.getExamType().getExamName().toString());
				individualAlertAndNotificationDto.setTitle(alertAndNotification.getApplicationStateDescription().getDescription());
				individualAlertAndNotificationDto.setDescription(alertAndNotification.getMessage());
				individualAlertAndNotificationDto.setDateOfEvent(simpleDateFormat.format(alertAndNotification.getLastModifiedOn()));
				individualAlertAndNotificationDtoList.add(individualAlertAndNotificationDto);
				
				
			}
		}
		
		
		
		return individualAlertAndNotificationDtoList;

		 
	}

}
