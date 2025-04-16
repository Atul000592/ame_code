package nic.ame.app.user.serviceImpl;



import javax.mail.MessagingException;

public interface EmailService {

	void sendEmail(String to, String subject, String body) ;
}