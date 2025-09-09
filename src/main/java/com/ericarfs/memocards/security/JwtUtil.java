package com.ericarfs.memocards.security;

import java.security.Key;
import java.util.Date;

import com.ericarfs.memocards.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 86400000;

	public static String generateAccessToken(String username, String role) {
		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public static String generateRefreshToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public static Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key).build()
				.parseClaimsJws(token).getBody();
	}

	public static boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
}
