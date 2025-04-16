package nic.ame.master.util;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;

import nic.ame.constant.EmailConfigConstant;




@Component("emailUtil")
public class EmailUtil {
	


	private static Logger logger = LogManager.getLogger(EmailUtil.class);
	private static Session session = null;

	public static boolean sendEmail(List<String> recipientAddress, String subject, String content) {

		return sendEmail(recipientAddress, null, subject, content, null, null, null);
	}

	//Email with attachment to multiple id in to and cc
	public static boolean sendEmail(List<String> recipientAddress, List<String> ccAddresses, String subject, String content, String attachmentFileName, InputStream attachmentFileStream, String attachmentContentType) {

		try {

			if(session == null) {

				logger.debug("Initializing email configuration");

				Properties properties = new Properties();

				properties.put(EmailConfigConstant.MAIL_SMTP_HOST_KEY, EmailConfigConstant.MAIL_SMTP_HOST_VALUE);
				properties.put(EmailConfigConstant.MAIL_SMTP_PORT_KEY, EmailConfigConstant.MAIL_SMTP_PORT_VALUE);

				//Commenting the following properties as they are not required for relay.nic.in as it is not using SSL
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE_KEY, EmailConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE_VALUE);
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_PORT_KEY, EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_PORT_VALUE);
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_KEY, EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE);
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_KEY, EmailConfigurationConstants.MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE);
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_AUTH_KEY, EmailConfigurationConstants.MAIL_SMTP_AUTH_VALUE);
				//properties.put(EmailConfigurationConstants.MAIL_SMTP_DEBUG_KEY, EmailConfigurationConstants.MAIL_SMTP_DEBUG_VALUE);

				/*session = Session.getInstance(properties, new javax.mail.Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication(EmailConfigurationConstants.EMAIL, EmailConfigurationConstants.PASSWORD);
					}
				});*/

				session = Session.getInstance(properties);
			}

			Message message = new MimeMessage(session);

			message.setSubject(subject);
			message.setFrom(new InternetAddress(EmailConfigConstant.EMAIL));
			for(String toAddress : recipientAddress) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			}
			// cc address if available
			if(ccAddresses != null) {
				for(String ccAddress : ccAddresses) {

					message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddress));
				}
			}

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			//Create textual part of the email
			BodyPart text = new MimeBodyPart();
			text.setText(content);
			text.setHeader("Content-Type", "text/html");

			multipart.addBodyPart(text);

			//Create the attachment part if present
			if(attachmentFileName != null) {
				BodyPart attachment = new MimeBodyPart();

				attachment.setDataHandler(new DataHandler(new ByteArrayDataSource(attachmentFileStream, attachmentContentType)));
				attachment.setFileName(attachmentFileName);
				attachment.setDisposition(Part.ATTACHMENT);

				multipart.addBodyPart(attachment);
				logger.info("file attached");
			}

			//Set the complete message parts
			message.setContent(multipart);
			
			logger.debug("Sending email");
			
			//Testing
			logger.info("Email disabled for testing");

			

			return true;
		}
		catch (Exception e) {

			//TODO: Handle the error case so that email messages are not lost.
			e.printStackTrace();
		}

		return false;
	}

	
}