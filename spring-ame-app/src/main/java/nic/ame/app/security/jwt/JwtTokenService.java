package nic.ame.app.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtTokenService {

	
	
	
	public String generateToken(String userName) {
		
		Map<String,Object> claims= new HashMap<>();
	    return createToken(claims,userName);
	    
	}
	
	
	private String createToken(Map<String, Object> claims, String userName) {
		// TODO Auto-generated method stub
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*30*60)).signWith(getSighKey(),SignatureAlgorithm.HS256).compact();
 

				
	}


	private Key getSighKey() {
		// TODO Auto-generated method stub
		byte [] key=Decoders.BASE64.decode("alphabetagammalemda");
		
		return Keys.hmacShaKeyFor(key);
	}
	
	
	
	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
		
	}

	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSighKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T extractClaims(String token,Function<Claims,T> claimsReolver)  {
		final Claims claims=extractAllClaims(token);
		return claimsReolver.apply(claims);
		
	}
	
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName=extractUserName(token);
		return(userName.equals(userDetails.getUsername())&& isTokenExpired(token));
	}


	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());	}


	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token,Claims::getExpiration);
	}
}
