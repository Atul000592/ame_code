package com.nic.in.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtService {

	  private static final String SECRET_KEY = "nVzAeIgqJNFw5s6FCXyYBe8IzBIVuZy3PKrT9XtIn5g=";

	    public String generateToken(String username) {
	        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }
	// Extract username
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	// Validate token
	public boolean validateToken(String token, String username) {
		return extractUsername(token).equals(username) && !isTokenExpired(token);
	}

	// Check expiry
	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
		return expiration.before(new Date());
	}
}
