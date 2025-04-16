package nic.ame.master.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class GetIpAddressClient {

	
	/*
	 * private static final List<String> POSSIBLE_IP_HEADERS = List.of(
	 * "X-Forwarded-For", "HTTP_FORWARDED", "HTTP_FORWARDED_FOR",
	 * "HTTP_X_FORWARDED", "HTTP_X_FORWARDED_FOR", "HTTP_CLIENT_IP", "HTTP_VIA",
	 * "HTTP_X_CLUSTER_CLIENT_IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
	 * "REMOTE_ADDR" ); public static String
	 * getIpAddressFromHeaderClient(HttpServletRequest request) { for (String
	 * ipHeader : POSSIBLE_IP_HEADERS) { String headerValue =
	 * Collections.list(request.getHeaders(ipHeader)).stream()
	 * .filter(StringUtils::hasLength) .findFirst() .orElse(null);
	 * 
	 * if (headerValue != null) { return headerValue; } }
	 * if(request.getHeader("x-forwarded-for") == null) { return
	 * request.getRemoteAddr(); } return null; }
	 */
	
	 private static final List<String> POSSIBLE_IP_HEADERS = Arrays.asList(
	            "x-forwarded-for",
	            "http_forwarded",
	            "http_forwarded_for",
	            "http_x_forwarded",
	            "http_x_forwarded_for",
	            "http_client_ip",
	            "http_via",
	            "http_x_cluster_client_ip",
	            "proxy_client_ip",
	            "wl_proxy_client_ip",
	            "remote_addr"
	    );

	    public static String getIpAddressFromHeaderClient(HttpServletRequest request) {
	        for (String ipHeader : POSSIBLE_IP_HEADERS) {
	            String headerValue = request.getHeader(ipHeader);
	            if (headerValue != null && headerValue.length() > 0 && !"unknown".equalsIgnoreCase(headerValue)) {
	                // The value of X-Forwarded-For could contain multiple IP addresses separated by a comma.
	                // The first one is the original client IP.
	                if ("x-forwarded-for".equalsIgnoreCase(ipHeader)) {
	                    int index = headerValue.indexOf(',');
	                    return (index != -1) ? headerValue.substring(0, index) : headerValue;
	                }
	                return headerValue;
	            }
	        }
	        return request.getRemoteAddr(); // If no header found, return the remote address
}
	    }
