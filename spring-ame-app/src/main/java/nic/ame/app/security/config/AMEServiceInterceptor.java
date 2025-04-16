package nic.ame.app.security.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nic.ame.master.util.GetClientIP;
@Component
public class AMEServiceInterceptor implements HandlerInterceptor {



@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	GetClientIP getClientIp = new GetClientIP();
    
    String Ip = getClientIp.getIpAddressFromHeader(request);
    //System.out.println(Ip+"----------inside interceptor------------------------");	
	
    return true;
         }
}
