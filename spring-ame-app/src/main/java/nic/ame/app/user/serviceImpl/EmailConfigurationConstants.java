package nic.ame.app.user.serviceImpl;

public interface EmailConfigurationConstants {

	public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
	public static final String MAIL_SMTP_HOST_VALUE = "relay.nic.in";

	public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
	public static final String MAIL_SMTP_PORT_VALUE = "25";

	// new validation and encryption in mail TLSv1.2 12-01-2023(brijesh kumar)
	public static final String MAIL_SMTP_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_STARTTLS_ENABLE_VALUE = "true";

	// new "mail.transport.protocol"--"smtp"
	public static final String MAIL_TRANSPORT_PROTOCAL_KEY = "mail.transport.protocol";
	public static final String MAIL_TRANSPORT_PROTOCAL_VALUE = "smtp";

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

	// new validation and encryption in mail TLSv1.2 12-01-2023(brijesh kumar)
	public static final String MAIL_SMTP_SSL_PROTOCALS_KEY = "mail.smtp.ssl.protocols";
	public static final String MAIL_SMTP_SSL_PROTOCALS_VAlUE = "TLSv1.2";

	/*
	 * public static final String MAIL_SMTP_SOCKS_HOST_KEY = "mail.smtp.socks.host";
	 * public static final String MAIL_SMTP_SOCKS_HOST_VALUE = "192.168.3.222";
	 * 
	 * public static final String MAIL_SMTP_SOCKS_PORT_KEY = "mail.smtp.socks.port";
	 * public static final String MAIL_SMTP_SOCKS_PORT_VALUE = "3169";
	 */

	/*
	 * public static final String MAIL_SMTP_USER_KEY = "mail.smtp.user"; public
	 * static final String MAIL_SMTP_USER_VALUE = "nalini@nic.in";
	 */

	public static final String EMAIL = "support-ame@mha.gov.in";
	public static final String PASSWORD = "Ame@2024";

	/*
	 * public static final String MAIL_SMTP_PASSWORD_KEY = "mail.smtp.password";
	 * public static final String MAIL_SMTP_PASSWORD_VALUE = "*****";
	 */

	// public static final String PASSWORD = "T6@e5$8M";

	// public static final String PASSWORD = "Nic@#$123";

	// public static final String PASSWORD = "pk1969Kl$";

	public static final String MESSAGE_CONTENT_TYPE_HTML = "text/html";
}
