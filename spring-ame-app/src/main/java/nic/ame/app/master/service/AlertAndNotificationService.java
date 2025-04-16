package nic.ame.app.master.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.IndividualAlertAndNotificationDto;
import nic.ame.app.master.model.AlertAndNotification;


@Service
public interface AlertAndNotificationService {
	
	List<IndividualAlertAndNotificationDto> findListOfAlertsAndNotificationsByForcePersonnelId(String forcePersonnelId);
	List<IndividualAlertAndNotificationDto> alertAndNotifications(String forcePersonnelId);

}
