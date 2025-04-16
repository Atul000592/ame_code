package nic.ame.app.esign;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.ApplicationStateDescription;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.sms.SMSConfigurationConstantsAME;
import nic.ame.app.sms.SMSTemplateDto;
import nic.ame.app.sms.SMSTemplateService;
import nic.ame.app.sms.SmsResponse;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateAlertAndNotification;

@Service
public class EsignAmeService {
	
	Logger logger=LoggerFactory.getLogger(EsignAmeService.class);
	
@Autowired
private SMSTemplateService smsTemplateService;

@Autowired
private RefMedicalExamTypeRepo refMedicalExamTypeRepo;

@Autowired
private CreateAlertAndNotification createAlertAndNotification;

@Autowired
private ApplicationStateDescriptionRepository applicationStateDescriptionRepository;

@Autowired
private AlertAndNotificationRepository alertAndNotificationRepository;

@Autowired
private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;

@Autowired
private ForcePersonnelService forcePersonnelService;

	
    public  void sendAMEResultSMSAndCreateNotificationForCandidate(String MedicalCategory,
    		Optional<ForcePersonnel> forcePersonnelOptional,HttpServletRequest request,String ameId){
		
    	
    	
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
		SMSTemplateDto smsTemplateDto = new SMSTemplateDto();
		smsTemplateDto.setAmeCompletedOnDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
		smsTemplateDto.setCategoryAwarded(MedicalCategory);
		smsTemplateDto.setIrlaNo(forcePersonnelOptional.get().getForceId());
		smsTemplateDto.setMobileNumber(String.valueOf(forcePersonnelOptional.get().getMobileNumber()));
		smsTemplateDto.setCountryIsdCode("91");

		SmsResponse smsResponse=smsTemplateService.ameSMS(smsTemplateDto, SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_AME_SMS);
		logger.info("setting alert........!00001");
	
     	Optional<ApplicationStateDescription> applicationStateDescription=applicationStateDescriptionRepository.findById(55);
    	
    	Optional<RefMedicalExamType> examType=refMedicalExamTypeRepo.findById(1L);
    	AlertAndNotification alertAndNotification= createAlertAndNotification.saveAlertAndNotificationForFinalAMEResult(
    			request,forcePersonnelOptional, smsResponse.getMessage(),
    			applicationStateDescription, examType, 
    			 "SAVING FINAL REPORT BY ESIGN ", "NA","NA");
    	alertAndNotificationRepository.save(alertAndNotification);
	  	logger.info("setting alert And Notification.......!");
	  	
	  	
        //logger.info("Final report status updated...........!" + filePathForSave);

		SMSTemplateDto smsTemplateDtoReview = new SMSTemplateDto();
		Optional<AmeReviewCandidatesList> ameReviewCandidatasList = ameReviewCandidatesListRepository
				.findByAmeIdAndStatusCode(ameId, CommonConstant.REVIEW_PENDING);
		if (ameReviewCandidatasList.isPresent()) {

			AmeReviewCandidatesList ameReviewCandidatesData = new AmeReviewCandidatesList();
			ameReviewCandidatesData = ameReviewCandidatasList.get();
			ForcePersonnelDto candidateForcePersonnel = forcePersonnelService
					.getForcePersonnelDetailsByForcePersonnelId(
							ameReviewCandidatesData.getCandidateForcePersonalId())
					.get();
			smsTemplateDtoReview.setIrlaNo(candidateForcePersonnel.getForceId());
			smsTemplateDtoReview.setMobileNumber(String.valueOf(candidateForcePersonnel.getMobileNumber()));
			smsTemplateDtoReview.setCountryIsdCode("91");
			smsTemplateDtoReview.setCategoryAwarded(MedicalCategory);

			smsTemplateDtoReview
					.setAmeCompletedOnDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewCreatedOn()));
			smsTemplateDtoReview
					.setAmeReviewDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewEndDate()));

			SmsResponse smsResponseReview=smsTemplateService.reviewMedicalBoardSMS(smsTemplateDto,
					SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_REVIEW_MEDICAL_BOARD);
		  
			
	     	Optional<ApplicationStateDescription> applicationStateDescriptionReview=applicationStateDescriptionRepository.findById(55);
	     	Optional<RefMedicalExamType> examTypeReview=refMedicalExamTypeRepo.findById(1L);
	     	AlertAndNotification alertAndNotificationReview= createAlertAndNotification.saveAlertAndNotificationForFinalAMEResult(
	    			request,forcePersonnelOptional, smsResponseReview.getMessage(),
	    			applicationStateDescriptionReview, examTypeReview, 
	    			 "SAVING FINAL REPORT BY ESIGN REVIEW  ", "NA","NA");
	    	alertAndNotificationRepository.save(alertAndNotificationReview);
			logger.info("AmeApplication Status Flow updated......Riview message......!");
      }

}
    
    
}
