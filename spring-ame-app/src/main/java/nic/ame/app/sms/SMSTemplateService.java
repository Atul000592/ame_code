package nic.ame.app.sms;

public interface SMSTemplateService {

	
	public  SmsResponse individualMappedToBoardSMS(SMSTemplateDto smsTemplateDto,String templateId);
	public  SmsResponse preAmeSMS(SMSTemplateDto smsTemplateDto,String templateId);
	public  SmsResponse ameSMS(SMSTemplateDto smsTemplateDto,String templateId);
	public  SmsResponse reviewMedicalBoardSMS(SMSTemplateDto smsTemplateDto,String templateId);
	public SmsResponse rescheduleAppointmentSMS(SMSTemplateDto smsTemplateDto,String templateId);

}
