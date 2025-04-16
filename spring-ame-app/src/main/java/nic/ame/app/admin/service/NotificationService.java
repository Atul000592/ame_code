package nic.ame.app.admin.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.model.Notification;

public interface NotificationService {
	
    public boolean saveNotification(Notification notification,String loginForcePersonalId,HttpServletRequest request,MultipartFile file);
	
	public void updateNotificationByOrderId(String OrderId);
	
	public List<NotificationDto> getAllActiveNotification();
	
	public List<NotificationDto> getAllNotifictionForScrollView(int forceNo);

}
