package com.example.SpringSecurity.utils;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtils {

	private static final long EXPIRATION_TIME = 60 * 60 * 1000;
	private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("SECRETKEYSECRSECRETKEYSECRETKEYSECRETKEYETSSECRETKEYSECRETKEYSECRETKEYECRETKEYSECRETKEYSECRETKEYKEYSECRETKEY"));

	public static String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);

		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
				.signWith(SECRET_KEY,SignatureAlgorithm.HS256)
				.compact();
	}

	public static boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
	                .setSigningKey(SECRET_KEY)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
			return true;
		} catch (Exception e) {
			return false;
		} 
	}
	
	public static String getUsername(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
	                .setSigningKey(SECRET_KEY)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
			return claims.getOrDefault("username","").toString();
		} catch (Exception e) {
			return "";
		} 
	}
}
