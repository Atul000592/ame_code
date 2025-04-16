package nic.ame.app.security.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SameSiteCookieConfig {

	/*
	 * @Bean public ServletContextInitializer initializer() { return
	 * servletContext->{ jakarta.servlet.SessionCookieConfig
	 * sessionCookieConfig=servletContext.getSessionCookieConfig();
	 * sessionCookieConfig.setHttpOnly(true); sessionCookieConfig.setSecure(true);
	 * sessionCookieConfig.setName("JSESSIONID");
	 * 
	 * };}
	 */ 
}
