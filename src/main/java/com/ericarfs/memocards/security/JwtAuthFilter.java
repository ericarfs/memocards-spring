package com.ericarfs.memocards.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	private final UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService){
    	this.userDetailsService = userDetailsService;
    }

    @Override
 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
 			throws ServletException, IOException {	
    	 
    	String authHeader = request.getHeader("Authorization");
    	if (authHeader == null || !authHeader.startsWith("Bearer")){
    		filterChain.doFilter(request, response);
    		return;
    	}
		
		String token = authHeader.substring(7);
		Claims claims = JwtUtil.extractClaims(token);
		String username = claims.getSubject();
		String role = claims.get("role", String.class);
		
		System.out.println(role);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userdetails = userDetailsService.loadUserByUsername(username);

			if (JwtUtil.validateToken(token)){
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userdetails, 
						null, 
						Collections.singletonList(new SimpleGrantedAuthority(role)));
			
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			filterChain.doFilter(request, response);
		}

    }

}