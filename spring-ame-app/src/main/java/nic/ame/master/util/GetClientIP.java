package nic.ame.master.util;

import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class GetClientIP {
	
	
	private static final List<String> POSSIBLE_IP_HEADERS = List.of(
		      "X-Forwarded-For",
		      "HTTP_FORWARDED",
		      "HTTP_FORWARDED_FOR",
		      "HTTP_X_FORWARDED",
		      "HTTP_X_FORWARDED_FOR",
		      "HTTP_CLIENT_IP",
		      "HTTP_VIA",
		      "HTTP_X_CLUSTER_CLIENT_IP",
		      "Proxy-Client-IP",
		      "WL-Proxy-Client-IP",
		      "REMOTE_ADDR"
		  );

	
	 public String getIpAddressFromHeader(HttpServletRequest request) {
		    for (String ipHeader : POSSIBLE_IP_HEADERS) {
		      String headerValue = Collections.list(request.getHeaders(ipHeader)).stream()
		          .filter(StringUtils::hasLength)
		          .findFirst()
		          .orElse(null);
		      	
		      if (headerValue != null) {
		        return headerValue;
		      }
		    }
		    if(request.getHeader("x-forwarded-for") == null) { 
		          return request.getRemoteAddr(); 
		          }
		    return null;
		  }
}
