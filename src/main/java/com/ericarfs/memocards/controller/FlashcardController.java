package com.ericarfs.memocards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.service.FlashcardService;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
	@Autowired
	private FlashcardService service;
	
	@GetMapping
	public ResponseEntity<List<Flashcard>> listAll(){
		List<Flashcard> list =  service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Flashcard obj){
		service.insert(obj);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
