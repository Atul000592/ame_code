package nic.ame.app.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nic.ame.app.security.service.UserInfoService;

/*
	@Component
	public class JwtAuthenticationFilter extends OncePerRequestFilter {

		
		@Autowired
		private JwtTokenService jwtTokenService;
		
		@Autowired
		private UserInfoService userInfoService;
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			final String authHeader=request.getHeader("Authorization");
			final String jwt;
			final String userName;
			
			if(authHeader==null||!authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
				
			}
			
			jwt=authHeader.substring(7);
			userName=jwtTokenService.extractUserName(jwt);
			
			if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails=userInfoService.loadUserByUsername(userName);
				if(jwtTokenService.isTokenValid(userName, userDetails))
				{
					UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			       
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			
			}
		}

}
*/