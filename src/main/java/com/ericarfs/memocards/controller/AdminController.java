package com.ericarfs.memocards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.service.FlashcardService;
import com.ericarfs.memocards.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private FlashcardService cardService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> findAllUsers(){
		List<User> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> findUserById(@PathVariable String id){
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/flashcards")
	public ResponseEntity<List<Flashcard>> listAllFlashcards(){
		List<Flashcard> list =  cardService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/flashcards/{id}")
	public ResponseEntity<Flashcard> findFlashcardById(@PathVariable String id){
		Flashcard card = cardService.findById(id);
		return ResponseEntity.ok().body(card);
	}
	
	@DeleteMapping("/flashcards/{id}")
	public ResponseEntity<Void> deleteFlashcard(@PathVariable String id){
		cardService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
