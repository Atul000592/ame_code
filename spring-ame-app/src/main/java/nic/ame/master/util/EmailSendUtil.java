package nic.ame.master.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nic.ame.constant.EmailConfigConstant;



public class EmailSendUtil {
	
	
	
	
	public static void sendmail(String message,String subject,String to ) throws MessagingException {
		
	
		Properties properties=System.getProperties();
		
		properties.put(EmailConfigConstant.MAIL_SMTP_HOST_KEY_l, EmailConfigConstant.MAIL_SMTP_HOST_VALUE_l);
		
		properties.put(EmailConfigConstant.MAIL_SMTP_PORT_KEY_l, EmailConfigConstant.MAIL_SMTP_PORT_VALUE_l);
		
		properties.put(EmailConfigConstant.MAIL_SMTP_USER_KEY_SSL, EmailConfigConstant.MAIL_SMTP_USER_VALUE_SSL);
		
		properties.put(EmailConfigConstant.MAIL_SMTP_AUTH_KEY_L,EmailConfigConstant.MAIL_SMTP_AUTH_VALUE_L);
		
	   properties.put(EmailConfigConstant.MAIL_SMTP_SSL_PROTOCALS_KEY,EmailConfigConstant.MAIL_SMTP_SSL_PROTOCALS_VAlUE);
	   
	   properties.put(EmailConfigConstant.MAIL_SMTP_STARTTLS_ENABLE_KEY,EmailConfigConstant.MAIL_SMTP_STARTTLS_ENABLE_VALUE);
	   
	   properties.put(EmailConfigConstant.MAIL_SMTP_AUTH_KEY, EmailConfigConstant.MAIL_SMTP_AUTH_VALUE);
	   
	   properties.put(EmailConfigConstant.MAIL_SMTP_DEBUG_KEY, EmailConfigConstant.MAIL_SMTP_DEBUG_KEY);
	   
        Session session=Session.getInstance(properties,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication
						(EmailConfigConstant.MAIL_SMTP_USER_KEY_EMAIL,EmailConfigConstant.MAIL_SMTP_USER_EMAIL_VALUE);
			}
	
		
		});
	
	List<String> List1=new ArrayList<>();
	
	List1.add("brijeshkumar.cse09@gmail.com");
	List1.add("kumar.me16@outlook.com");
	String toa="brijeshkumar.cse09@gmail.com"+","+"kumar.me16@outlook.com";
	

	
	
	 session.setDebug(true);
	 
	 MimeMessage message2= new MimeMessage(session);
	
	 //from
	 message2.setFrom(new InternetAddress(EmailConfigConstant.MAIL_SMTP_USER_KEY_EMAIL));
	 
	 message2.addRecipient(Message.RecipientType.TO, new InternetAddress("atul29030@gmail.com"));
	 message2.addRecipient(Message.RecipientType.CC, new InternetAddress("brijeshkumar.cse09@gmail.com"));
 
	 message2.setSubject(subject);
	 
	 message2.setText(message);
	 
	 
	 Transport.send(message2);
	 
	 System.out.println("Mail has beeen send......");
		
	}
	

}
