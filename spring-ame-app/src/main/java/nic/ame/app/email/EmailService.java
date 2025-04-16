package nic.ame.app.email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;



@Service
public class EmailService {
    private static final Logger logger = LogManager.getLogger(EmailService.class);
	private static Session session = null;

	// --------------------------Old method implementation date
	// 20-02-2023---------------------------------------------//
	public static boolean sendEmailWithSinglerecipent(String recipent,String subject, String content) {
		return sendEmail(recipent, null, subject, content, null, null, null);
	}
	
	

	// Email with attachment to multiple id in to and cc
	public static boolean sendEmailMultipleRecipent(String recipent,String subject, String content, String attachmentFileName,
			InputStream attachmentFileStream, String attachmentContentType) {
		return sendEmail(recipent, null, subject, content, attachmentFileName, attachmentFileStream,
				attachmentContentType);
	}

	// Email with attachment to single recipient and optional cc addresses
	public static boolean sendEmailSingleRecipient(String recipent,String subject, String content, List<String> ccAddresses, String attachmentFileName,
			InputStream attachmentFileStream, String attachmentContentType) {
		return sendEmail(recipent, ccAddresses, subject, content, attachmentFileName, attachmentFileStream,
				attachmentContentType);
	}

	// Main send email method with all parameters
	private static boolean sendEmail(String recipientAddress, List<String> ccAddresses, String subject, String content,
			String attachmentFileName, InputStream attachmentFileStream, String attachmentContentType) {
		try {
			if (session == null) {
				logger.debug("Initializing email configuration");

				Properties properties = new Properties();
				properties.put(EmailConfigurationConstants.MAIL_TRANSPORT_PROTOCAL_KEY,
						EmailConfigurationConstants.MAIL_TRANSPORT_PROTOCAL_VALUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_HOST_KEY,
						EmailConfigurationConstants.MAIL_SMTP_HOST_VALUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_PORT_KEY,
						EmailConfigurationConstants.MAIL_SMTP_PORT_VALUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_AUTH_KEY,
						EmailConfigurationConstants.MAIL_SMTP_AUTH_VALUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE_KEY,
						EmailConfigurationConstants.MAIL_SMTP_STARTTLS_ENABLE_VALUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_SSL_PROTOCALS_KEY,
						EmailConfigurationConstants.MAIL_SMTP_SSL_PROTOCALS_VAlUE);
				
				properties.put(EmailConfigurationConstants.MAIL_SMTP_DEBUG_KEY,
						EmailConfigurationConstants.MAIL_SMTP_DEBUG_VALUE);

				Authenticator auth = new SMTPAuthenticator();
				session = Session.getInstance(properties, auth);
			}

			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(EmailConfigurationConstants.EMAIL, "Annual Medical Examination"));

			// Add recipient
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));

			// Add CC recipients if available
			if (ccAddresses != null) {
				for (String ccAddress : ccAddresses) {
					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddress));
				}
			}

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Create textual part of the email
			BodyPart text = new MimeBodyPart();
			text.setContent(content, EmailConfigurationConstants.MESSAGE_CONTENT_TYPE_HTML);

			multipart.addBodyPart(text);

			// Create the attachment part if present
			if (attachmentFileName != null) {
				BodyPart attachment = new MimeBodyPart();
				attachment.setDataHandler(
						new DataHandler(new ByteArrayDataSource(attachmentFileStream, attachmentContentType)));
				attachment.setFileName(attachmentFileName);
				attachment.setDisposition(Part.ATTACHMENT);
				multipart.addBodyPart(attachment);
				logger.info("File attached: " + attachmentFileName);
			}

			// Set the complete message parts
			msg.setContent(multipart);

			logger.debug("Sending email...");

			// Send the email
			Transport.send(msg);
			logger.debug("Email sent successfully");

			return true;
		} catch (Exception e) {
			logger.error("Error sending email: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	// Authenticator class for SMTP authentication
	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = EmailConfigurationConstants.EMAIL;
			String password = EmailConfigurationConstants.PASSWORD;
			return new PasswordAuthentication(username, password);
		}
	}
}