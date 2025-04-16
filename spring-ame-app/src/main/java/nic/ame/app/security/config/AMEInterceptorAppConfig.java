package nic.ame.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AMEInterceptorAppConfig implements WebMvcConfigurer{
	
	@Autowired
	public AMEServiceInterceptor ameServiceInterceptor;
		// TODO Auto-generated constructor stub
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ameServiceInterceptor); 
	}

}
