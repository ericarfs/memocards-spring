package com.ericarfs.memocards.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.security.JwtUtil;
import com.ericarfs.memocards.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserService service;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody User obj) {
		service.register(obj.getUsername(), obj.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/token")
	public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
		Optional<User> user = service.findByUsername(request.get("username"));

		if (user.isPresent() && service.validatePassword(user.get().getPassword(), request.get("password"))) {
			String accessToken = JwtUtil.generateAccessToken(user.get().getUsername(), user.get().getRole().name());
			String refreshToken = JwtUtil.generateRefreshToken(user.get());
			return ResponseEntity.ok().body(Map.of(
					"access", accessToken,
					"refresh", refreshToken));
		}

		return ResponseEntity.status(401).body("Wrong credentials.");
	}

	@PostMapping("/token/refresh")
	public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
		String refreshToken = request.get("refreshToken");

		try {
			String username = JwtUtil.extractClaims(refreshToken).getSubject();

			String newAccessToken = JwtUtil.generateAccessToken(username,
					service.findByUsername(username).get().getRole().name());

			return ResponseEntity.ok().body(Map.of(
					"access", newAccessToken,
					"refresh", refreshToken));
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Invalid refresh token.");
		}
	}

}
