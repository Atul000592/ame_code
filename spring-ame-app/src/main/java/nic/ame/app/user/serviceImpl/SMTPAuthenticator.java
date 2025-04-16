package nic.ame.app.user.serviceImpl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	public PasswordAuthentication getPasswordAuthentication() {
		String username = EmailConfigurationConstants.EMAIL;
		String password = EmailConfigurationConstants.PASSWORD;
		return new PasswordAuthentication(username, password);
	}

}
