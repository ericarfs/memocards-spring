package com.ericarfs.memocards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.dto.UserDTO;
import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<UserDTO> findByUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = service.findByUsername(username).get();
		UserDTO userDto = new UserDTO(user);

		return ResponseEntity.ok().body(userDto);
	}

	@DeleteMapping
	public ResponseEntity<Void> delete() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = service.findByUsername(username).get();
		service.delete(user.getId());

		return ResponseEntity.noContent().build();
	}
}
