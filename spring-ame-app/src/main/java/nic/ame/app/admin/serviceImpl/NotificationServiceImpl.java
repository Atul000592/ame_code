package nic.ame.app.admin.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.model.Notification;
import nic.ame.app.admin.model.NotificationFileUpload;
import nic.ame.app.admin.repository.NotificationFileUploadRepo;
import nic.ame.app.admin.repository.NotificationRepo;
import nic.ame.app.admin.service.NotificationService;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.GetIpAddressClient;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private ForcePersonalService forcePersonalService;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private NotificationFileUploadRepo notificationFileUploadRepo;

	@Override
	public boolean saveNotification(Notification notification, String loginForcePersonalId, HttpServletRequest request,
			MultipartFile file) {

		Optional<ForcePersonnelDto> forcePersonalOptional = forcePersonalService
				.findByForcePersonalId(loginForcePersonalId);
		System.out.println(">>>>>>>>>>>>>>>\n" + notification);
		if (!forcePersonalOptional.isEmpty()) {
			ForcePersonnelDto forcePersonnel = new ForcePersonnelDto();
			forcePersonnel = forcePersonalOptional.get();
//=======================================saving notification==============================================================//
			
			
			notification.setEntryByDesignation(forcePersonnel.getDesignation());
			notification.setEntryByName(forcePersonnel.getName());
			notification.setEntryByDesignation(forcePersonnel.getDesignation());
			notification.setEntryByForce_no(forcePersonnel.getForceNo());
			notification.setEntryByIrlaNo(forcePersonnel.getForceId());
			notification.setEntryIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));

		}
		
		
		Notification notificationsaved = notificationRepo.save(notification);
		
		
//========================================uploading file==================================================================//		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		

		//directory name
		String directoryName = CommonConstant.File_PATH_NOTIFICATION + File.separator + year;

		//creating directory with path directory name
		String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName, String.valueOf(year));

		//file name		
	    String fileName = notification.getOrderId() + file.getOriginalFilename();
		
		//file path name
		String savePath = path + File.separator + fileName;

		

		NotificationFileUpload notificationFileUpload = new NotificationFileUpload();
        //checking if notification is saved, save data in notification_file_upload table
		if (notificationsaved != null)

		{
			notificationFileUpload.setCreatedOn(new Date());
			notificationFileUpload.setFileName(fileName);
			notificationFileUpload.setFormat(file.getContentType());
			notificationFileUpload.setOrderId(notification.getOrderId());
			notificationFileUpload.setPathName(savePath);
			notificationFileUpload.setSize(Integer.parseInt(String.valueOf(file.getSize())));
			NotificationFileUpload fileUpload = notificationFileUploadRepo.save(notificationFileUpload);
//       uploading file 
			if (fileUpload != null) {
				try {
					FileCopyUtils.copy(file.getBytes(), new File(savePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return true;

		}

		else
			return false;
	}
//=========================================================================================================================//
	@Override
	public void updateNotificationByOrderId(String OrderId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<NotificationDto> getAllActiveNotification() {
		// TODO Auto-generated method stub
		
		List<Object> objectList=notificationRepo.getAllNotification();
		List<NotificationDto> notificationDtos=new ArrayList<>();
		if (objectList.isEmpty()) {
			return notificationDtos;
		}
		Iterator<Object> iterator = objectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			
			NotificationDto notificationDto=new NotificationDto();
			
			if (obj[0] != null) {
				notificationDto.setTitle(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				notificationDto.setDescription(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				notificationDto.setForceName(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
                Date date= (Date) obj[3];
				notificationDto.setOrderDate(date);
			}
			if (obj[4] != null) {
                
				notificationDto.setPath(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				notificationDto.setFileName(String.valueOf(obj[5]).trim());
			}
			
		   notificationDtos.add(notificationDto);
		}
		System.out.println(notificationDtos);
			return notificationDtos;
	}
	
	@Override
	public List<NotificationDto> getAllNotifictionForScrollView(int forceNo) {
		
		List<Object> objectList =notificationRepo.getAllNotifictionForScrollView(forceNo);
		List<NotificationDto> notificationDtos= new ArrayList<>();
		if(objectList.isEmpty()) {
			return notificationDtos;
			
			}
		
		Iterator<Object> objectListIterator=objectList.iterator();
		while(objectListIterator.hasNext()) {
			Object[] objectItem = (Object[])objectListIterator.next();
			
			NotificationDto notificationDto=new NotificationDto();
			if(objectItem[0]!=null) {
				notificationDto.setTitle(String.valueOf(objectItem[0]).trim());
			}
			if(objectItem[1]!=null) {
				notificationDto.setDescription(String.valueOf(objectItem[1]).trim());
			}
			if(objectItem[2]!=null) {
				String dateInitial=String.valueOf(objectItem[2]).trim();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date dateFinal=dateFormat.parse(dateInitial);
					notificationDto.setOrderDate(dateFinal);
				}
				catch(ParseException e) {
					e.printStackTrace();
					}
				
			}
			if(objectItem[3]!=null) {
				notificationDto.setPath(String.valueOf(objectItem[3]).trim());
			}
			if(objectItem[4]!=null) {
				notificationDto.setForceName(String.valueOf(objectItem[4]).trim());
			}
			notificationDtos.add(notificationDto);
			
		}
		
		
		
		return notificationDtos;
	}
	
//	==============================================view Notification========================================================//
	
	
		
	

}
