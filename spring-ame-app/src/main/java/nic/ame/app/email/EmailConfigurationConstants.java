package nic.ame.app.email;


public interface EmailConfigurationConstants {

	public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
	public static final String MAIL_SMTP_HOST_VALUE = "smtpsgwhyd.nic.in";

	public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
	public static final String MAIL_SMTP_PORT_VALUE = "465";

	public static final String MAIL_SMTP_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_STARTTLS_ENABLE_VALUE = "true";


	public static final String MAIL_TRANSPORT_PROTOCAL_KEY = "mail.transport.protocol";
	public static final String MAIL_TRANSPORT_PROTOCAL_VALUE = "smtp";


	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS_KEY = "mail.smtp.socketFactory.class";
	public static final String MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE = "javax.net.ssl.SSLSocketFactory";

	public static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK_KEY = "mail.smtp.socketFactory.fallback";
	public static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE = "true";

	public static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
	public static final String MAIL_SMTP_AUTH_VALUE = "true";

	public static final String MAIL_SMTP_DEBUG_KEY = "mail.smtp.debug";
	public static final String MAIL_SMTP_DEBUG_VALUE = "true";

	public static final String MAIL_SMTP_SSL_PROTOCALS_KEY = "mail.smtp.ssl.protocols";
	public static final String MAIL_SMTP_SSL_PROTOCALS_VAlUE = "TLSv1.2";


	public static final String EMAIL = "support-ame@mha.gov.in";
	public static final String PASSWORD = "Ame@2024";



	public static final String MESSAGE_CONTENT_TYPE_HTML = "text/html";
}
