package com.ericarfs.memocards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping()
	public ResponseEntity<List<User>> findAll(){
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable String id){
		User user = service.findById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
