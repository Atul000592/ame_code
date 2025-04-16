package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.AlertAndNotification;

@Repository
public interface AlertAndNotificationRepository extends JpaRepository<AlertAndNotification, Integer> {
	@Query(nativeQuery = true,value="select rmet.exam_name, tasd.description, taan.message, taan.last_modified_on\r\n"
			+ "			from tt_alert_and_notification as taan\r\n"
			+ "			inner join ref_medical_exam_type  as rmet\r\n"
			+ "			on taan.exam_type = rmet.id\r\n"
			+ "			inner join tm_application_state_description as tasd\r\n"
			+ "			on taan.header= tasd.id\r\n"
			+ "			where taan.receiver_force_personnel_id=?1\r\n"
			+ "			Order by taan.last_modified_on DESC")
	List<Object> getListOfAlertsAndNotificationsByForcePersonnelId(String forcePersonnelId);
	
	List<AlertAndNotification> findByReceiverForcePersonnelIdOrderByLastModifiedOnDesc(String forcePersonnelId);

}
