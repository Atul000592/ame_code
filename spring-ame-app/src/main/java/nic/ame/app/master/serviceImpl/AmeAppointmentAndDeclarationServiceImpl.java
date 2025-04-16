package nic.ame.app.master.serviceImpl;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.AppointmentListDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.ApplicationStateDescription;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.AmeAppointmentAndDeclarationService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.sms.SMSConfigurationConstantsAME;
import nic.ame.app.sms.SMSTemplateDto;
import nic.ame.app.sms.SMSTemplateService;
import nic.ame.app.sms.SmsResponse;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateAlertAndNotification;
import nic.ame.master.util.GetIpAddressClient;



@Service
public class AmeAppointmentAndDeclarationServiceImpl implements AmeAppointmentAndDeclarationService {

	
	@Autowired
	private TTAppointmentAmeRepo appointmentAmeRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private SMSTemplateService smsTemplateService;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private RefForceService refForceService;
	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;
	
	@Autowired
	private ApplicationStateDescriptionRepository ApplicationStateDescriptionRepository;
	
	@Autowired
	private RefMedicalExamTypeRepo medicalExamTypeRepo;

	
	
	


	@Override
	public void saveAmeAppointmentDetails(String candidateForcePersonnelId, String forcePersonnelIdLogin, String fromDate,
			String toDate, String remark,String boardId,HttpServletRequest httpServletRequest)  {
		Date fromDate1 = null;
		Date toDate1 = null;
		
	Optional<ForcePersonnel> candidateForcePersonalData=forcePersonnelRepository.getByForcePersonnelId(candidateForcePersonnelId);
		
	
		TTAppointmentAme ame=new TTAppointmentAme();
		ame.setCreatedOn(Calendar.getInstance().getTime());
		ame.setCreatedBy(forcePersonnelIdLogin);
		ame.setForcePersonalId(candidateForcePersonnelId);
		ame.setRemark(remark);
		ame.setForceNo(candidateForcePersonalData.get().getForceNo());
		ame.setUnitNo(Integer.parseInt(candidateForcePersonalData.get().getUnit()));
		ame.setIrlaNo(candidateForcePersonalData.get().getForceId());
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		  DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		ame.setIsAppointmentValid(1);
		try {
			fromDate1=dateFormat.parse(fromDate);
			toDate1=dateFormat.parse(toDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		ame.setToDate(toDate1);
		ame.setFromDate(fromDate1);
		ame.setBoardId(boardId);
	
	    if(appointmentAmeRepo.save(ame)!=null) {
	    	AlertAndNotification alertAndNotification= new AlertAndNotification();
	    	Optional<ApplicationStateDescription> applicationStateDescription=ApplicationStateDescriptionRepository.findById(10);
	    	//applicationStateDescription.setId(10);
	    	alertAndNotification.setApplicationStateDescription(applicationStateDescription.get());
	    	
	    	alertAndNotification.setReceiverForcePersonnelId(candidateForcePersonnelId);
	    	alertAndNotification.setReceiverEmail(candidateForcePersonalData.get().getEmailId());
	    	alertAndNotification.setReceiverMobileNumber(candidateForcePersonalData.get().getMobileNumber());
	    	Optional<RefMedicalExamType> examType=medicalExamTypeRepo.findById(1L);
	    	
	    	alertAndNotification.setExamType(examType.get());
	    	
	    	alertAndNotification.setSendByForcePersonnelId(forcePersonnelIdLogin);
	    	alertAndNotification.setSendFrom(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
	    	alertAndNotification.setSendBy(forcePersonnelIdLogin);
	    	alertAndNotification.setSendOn(Calendar.getInstance().getTime());
	    	
	    	alertAndNotification.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
	    	alertAndNotification.setLastModifiedBy(forcePersonnelIdLogin);
	    	alertAndNotification.setLastModifiedOn(Calendar.getInstance().getTime());
	    	

			SMSTemplateDto smsTemplateDto = new SMSTemplateDto();
			
			MedicalBoard medicalBoardDetail = medicalBoardRepo.getByBoardId(boardId);
			
			String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardDetail.getBoardAtForceNo()),medicalBoardDetail.getPlace());
		
			 ForcePersonnelDto candidateForcePersonnel = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonnelId).get();
		     
			 smsTemplateDto.setIrlaNo(candidateForcePersonnel.getForceId());
			 smsTemplateDto.setRank(candidateForcePersonnel.getRank());
			 smsTemplateDto.setName(candidateForcePersonnel.getName());
			 
			 
			  LocalDate dateToDateSMS = LocalDate.parse(toDate, inputFormatter);
			  LocalDate dateFromDateSMS = LocalDate.parse(fromDate, inputFormatter);
			 
			  
			  smsTemplateDto.setToDate(String.valueOf(dateToDateSMS.format(outputFormatter)));
			 smsTemplateDto.setFromDate(String.valueOf(dateFromDateSMS.format(outputFormatter)));
			 
			 smsTemplateDto.setYear(medicalBoardDetail.getBoardYear());
			 smsTemplateDto.setBoardUnitPlace(boardPlace);
			 smsTemplateDto.setMobileNumber(String.valueOf(candidateForcePersonalData.get().getMobileNumber()));
			 smsTemplateDto.setCountryIsdCode("91");
			 SmsResponse smsResponse=smsTemplateService.preAmeSMS(smsTemplateDto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_PRE_AME_SMS);
			 if(smsResponse!=null) {
				 alertAndNotification.setIsSuccessSMS(1);
				 alertAndNotification.setIsSuccessSMSOn(Calendar.getInstance().getTime());
				 alertAndNotification.setMessage(smsResponse.getMessage());
				 alertAndNotificationRepository.save(alertAndNotification);
			 }
			 else {
				 alertAndNotification.setIsSuccessSMS(0);
				 alertAndNotification.setMessage(smsResponse.getMessage());
                 alertAndNotificationRepository.save(alertAndNotification);
			 }
	    	
	    	
	    }
	}

	@Override
	public List<AppointmentListDto> getAppointmentDtoList(String boardId) {

		List<AppointmentListDto> appointmentDto=new ArrayList<>();
		
	   List<Object>  objects=appointmentAmeRepo.getListOfForcePersonalWithAppointmentProvided(boardId);
	 
		if(objects.isEmpty()) {
			return appointmentDto;
		}
       	Iterator<Object> iterator=objects.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AppointmentListDto appointmentListDto=new AppointmentListDto();
			
			if (obj[0] != null) {
				appointmentListDto.setBoardId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				appointmentListDto.setIrlaNo(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				appointmentListDto.setName(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				appointmentListDto.setForceName(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				appointmentListDto.setUnitName(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				
				if(Integer.parseInt(String.valueOf(obj[5]).trim())==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_PENDING))) {
					appointmentListDto.setDeclarationStatus("Pending");
				}else if(Integer.parseInt(String.valueOf(obj[5]).trim())==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_NA)))  {
					appointmentListDto.setDeclarationStatus("NA");
				}
				else{
					appointmentListDto.setDeclarationStatus("Completed");
				}
				
			}
			if (obj[6] != null) {
				appointmentListDto.setFromDate((Date) obj[6]);
			}
			if (obj[7] != null) {
				appointmentListDto.setToDate((Date) obj[7]);
			}
			if (obj[8] != null) {
				if(String.valueOf(obj[8]).equalsIgnoreCase("M"))
					appointmentListDto.setGender("Male");
				else
					appointmentListDto.setGender("Female");
			}
			if (obj[9] != null) {
				appointmentListDto.setAppointmentStatusCode(Integer.parseInt(String.valueOf(obj[9]).trim()));
				if(appointmentListDto.getAppointmentStatusCode()==0) {
					
					appointmentListDto.setAppointmentStatusDescription("Invalid");
				}
				else if(appointmentListDto.getAppointmentStatusCode()==1) {
					appointmentListDto.setAppointmentStatusDescription("Valid");
					
				}
				
				else if(appointmentListDto.getAppointmentStatusCode()==2) {
					appointmentListDto.setAppointmentStatusDescription("Not appeared after declaration");
					
				}
				
				else if(appointmentListDto.getAppointmentStatusCode()==3) {
					appointmentListDto.setAppointmentStatusDescription("Rejected by board member");
					
				}
				else if(appointmentListDto.getAppointmentStatusCode()==4) {
					appointmentListDto.setAppointmentStatusDescription("Invalid for review(AME)");
					
				}
				
				else if(appointmentListDto.getAppointmentStatusCode()==5) {
					appointmentListDto.setAppointmentStatusDescription("Not appeared for AME within given time period");
					
				}
				else if(appointmentListDto.getAppointmentStatusCode()==6) {
					appointmentListDto.setAppointmentStatusDescription("AME reviewed for same year");
					
				}
			}
			
			if (obj[10] != null) {
				appointmentListDto.setForcePersonalId(String.valueOf(obj[10]).trim());
			}
			if (obj[11] != null) {
				appointmentListDto.setRescheduleFlag(Boolean.parseBoolean(String.valueOf(obj[11]).trim()));
			}
			
		appointmentDto.add(appointmentListDto);
	}
	
		return appointmentDto;

	}

	@Override
	public int appointmentCompletedCount(String boardId) {
	int count=0;
	count=appointmentAmeRepo.getAppointmentCompleteCount(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED)), boardId);
		return count;
	}

	@Override
	public int appointmentPendingCount(String boardId) {
		int count=0;
		count=appointmentAmeRepo.getAppointmentPendingCount(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PENDING)), boardId);
		return count;
	}

}
