package nic.ame.app.security.config;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterMethod extends OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(FilterMethod.class);
	@Autowired
	ServletContext context;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		

		if (request.getMethod().equals("OPTIONS")) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else {
			response.setHeader("X-XSS-Protection", "1; mode=block");
			// response.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=strict");
			addSameSiteCookieAttribute(response);
			addSameSiteCookieAttribute(response);

			setSecurityHeaders(response);
			filterChain.doFilter(request, response);
		}

	}

	/*
	 * private void addSameSiteCookieAttribute(HttpServletResponse response) {
	 * Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
	 * boolean firstHeader = true; for (String header : headers) { // there can be
	 * multiple Set-Cookie attributes if (firstHeader) {
	 * response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header,
	 * "SameSite=Strict", "Secure=true")); firstHeader = false; continue; }
	 * response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header,
	 * "SameSite=Strict", "Secure=true")); } }
	 */
	private void setSecurityHeaders(HttpServletResponse response) {
        // XSS Protection Header
        response.setHeader("X-XSS-Protection", "1; mode=block");

        // Sample cookie with HttpOnly and SameSite attributes
        Cookie sampleCookie = new Cookie("key", "value");
        sampleCookie.setHttpOnly(true);
        sampleCookie.setSecure(true); // HTTPS requirement for SameSite=None
        sampleCookie.setPath("/");
        response.addCookie(sampleCookie);
    }
	
	private void addSameSiteCookieAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        
        for (String header : headers) {
            String updatedHeader = header;
            
            // Append SameSite and Secure attributes if they are not already present
            if (!header.toLowerCase().contains("samesite")) {
                updatedHeader += "; SameSite=Strict";
            }
            if (!header.toLowerCase().contains("secure")) {
                updatedHeader += "; Secure";
            }

            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE, updatedHeader);
                firstHeader = false;
            } else {
                response.addHeader(HttpHeaders.SET_COOKIE, updatedHeader);
            }
        }
    } 
}
