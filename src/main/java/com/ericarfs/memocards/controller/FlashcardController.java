package com.ericarfs.memocards.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ericarfs.memocards.dto.AuthorDTO;
import com.ericarfs.memocards.dto.CreateFlashcardDTO;
import com.ericarfs.memocards.dto.FlashcardDTO;
import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.service.FlashcardService;
import com.ericarfs.memocards.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
	@Autowired
	private FlashcardService cardService;
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<FlashcardDTO>> listAll() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		List<Flashcard> list = cardService.findByAuthor(username);
		List<FlashcardDTO> cards = list
				.stream()
				.map(FlashcardDTO::new)
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(cards);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FlashcardDTO> listByID(@PathVariable String id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		Flashcard card = cardService.findByIdAndAuthor(id, username);
		return ResponseEntity.ok().body(new FlashcardDTO(card));
	}

	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CreateFlashcardDTO objDto) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = userService.findByUsername(username).get();
		AuthorDTO author = new AuthorDTO(user.getId(), username);

		Flashcard card = objDto.mapToFlashcard(author);
		cardService.insert(card);
		userService.addFlashcard(user, card);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<FlashcardDTO> update(@PathVariable String id, @Valid @RequestBody Flashcard obj) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		obj = cardService.update(id, username, obj);
		return ResponseEntity.ok().body(new FlashcardDTO(obj));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		cardService.deleteByUser(id, username);
		return ResponseEntity.noContent().build();
	}
}
