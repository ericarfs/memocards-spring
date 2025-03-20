package com.ericarfs.memocards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.service.FlashcardService;

import jakarta.validation.Valid;

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
	
	@GetMapping("/{id}")
	public ResponseEntity<Flashcard> listByID(@PathVariable String id){
		Flashcard card =  service.findById(id);
		return ResponseEntity.ok().body(card);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Flashcard obj){
		service.insert(obj);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Flashcard> update(@PathVariable String id, @Valid @RequestBody Flashcard obj){
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
