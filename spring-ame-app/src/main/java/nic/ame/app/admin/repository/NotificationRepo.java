package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.admin.model.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long>{

	
	@Query(nativeQuery = true,value = "select tn.title,tn.description,rf.force_name,tn.order_date,tnf.path_name,tnf.file_name \n"
			+ "from tt_notification tn \n"
			+ "inner join tt_notification_file_upload tnf on tnf.order_id = tn.order_id\n"
			+ "inner join tm_force rf on tn.ministry_department=rf.force_no \n"
			+ "order by tn.entry_date desc")
	        public List<Object> getAllNotification();

//===========================================Notification scroll view RK=============================================================
	
	@Query(nativeQuery=true,value="select n.title,n.description,n.order_date,nfu.path_name,rf.force_name \n"
			+ "from tt_notification as n \n"
			+ "inner join  tt_notification_file_upload as nfu \n"
			+ "on n.order_id=nfu.order_id\n"
			+ "inner join tm_force as rf\n"
			+ "on n.entry_by_force_no=rf.force_no\n"
			+ "where n.visible_from_date <= CURRENT_DATE\n"
			+ "and n.visible_to_date >= CURRENT_DATE\n"
			+ "and n.entry_by_force_no=n.ministry_department\n"
			+ "and n.entry_by_force_no=?1")
	        public List<Object> getAllNotifictionForScrollView(int forceNo);
}
