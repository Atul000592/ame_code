package nic.ame.constant;

public interface EmailConfigConstant {

	public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
	public static final String MAIL_SMTP_HOST_VALUE = "relay.nic.in";
	

	public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
	public static final String MAIL_SMTP_PORT_VALUE = "25";

	public static final String MAIL_SMTP_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_STARTTLS_ENABLE_VALUE = "true";

	public static final String MAIL_SMTP_SOCKETFACTORY_PORT_KEY = "mail.smtp.socketFactory.port";
	public static final String MAIL_SMTP_SOCKETFACTORY_PORT_VALUE = "25";

	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS_KEY = "mail.smtp.socketFactory.class";
	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE = "javax.net.ssl.SSLSocketFactory";

	public static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK_KEY = "mail.smtp.socketFactory.fallback";
	public static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE = "true";

	public static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
	public static final String MAIL_SMTP_AUTH_VALUE = "true";

	public static final String MAIL_SMTP_DEBUG_KEY = "mail.smtp.debug";
	public static final String MAIL_SMTP_DEBUG_VALUE = "true";

	
	//new validation and encryption in mail TLSv1.2
	public static final String MAIL_SMTP_SSL_PROTOCALS_KEY="mail.smtp.ssl.protocols";
	public static final String MAIL_SMTP_SSL_PROTOCALS_VAlUE="TLSv1.2";


	public static final String EMAIL = "shaheed.capfs@gov.in";

	public static final String MESSAGE_CONTENT_TYPE_HTML = "text/html";
	
	//------------------------------local email with gmail port----------------------------------------------//
	
	public static final String MAIL_SMTP_HOST_KEY_l = "mail.smtp.host";
	public static final String MAIL_SMTP_HOST_VALUE_l = "smtp.gmail.com";
	
	public static final String MAIL_SMTP_PORT_KEY_l= "mail.smtp.port";
	public static final String MAIL_SMTP_PORT_VALUE_l = "465";
	
	
	public static final String MAIL_SMTP_USER_KEY_EMAIL = "amereport2022@gmail.com";
	public static final String MAIL_SMTP_USER_EMAIL_VALUE = "gsiogpjbnuzjgqfq";
	
	public static final String MAIL_SMTP_USER_KEY_SSL="mail.smtp.ssl.enable";
	public static final String MAIL_SMTP_USER_VALUE_SSL="true";
	
	public static final String MAIL_SMTP_AUTH_KEY_L= "mail.smtp.auth";
	public static final String MAIL_SMTP_AUTH_VALUE_L = "true";
	

	
	
	
}
