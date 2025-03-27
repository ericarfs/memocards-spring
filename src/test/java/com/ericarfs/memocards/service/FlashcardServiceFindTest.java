package com.ericarfs.memocards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ericarfs.memocards.dto.AuthorDTO;
import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.FlashcardRepository;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceFindTest {
	@InjectMocks
	private FlashcardService service;
	
	@Mock
	private FlashcardRepository repository;
	
	private List<Flashcard> flashcards = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		Flashcard c1 = new Flashcard("67e04d0d365ac80f157d98b4", "Expression1", "Meaning1", "Example1", new AuthorDTO("68e04d0d365ac80f157d98b4", "Maria"));

		flashcards.add(c1);
		
		lenient().when(repository.findById("67e04d0d365ac80f157d98b4"))
		.thenReturn(Optional.of(flashcards.get(0)));

		lenient().when(repository.findById("67e04d0d365ac80f157d98b5"))
				.thenThrow(new ResourceNotFoundException("Flashcard with id 67e04d0d365ac80f157d98b5 not found."));
	}
	
	@Test
	void mustReturnAListWithOneFlashcard() {
		when(repository.findAll()).thenReturn(flashcards);
		List<Flashcard> users = service.findAll();
		
		assertEquals(1, users.size());
		
		verify(repository).findAll();
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustFindAFlashcardById() {
		Flashcard c = service.findById("67e04d0d365ac80f157d98b4");
		
		assertEquals(c, flashcards.get(0));
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFoundById() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById("67e04d0d365ac80f157d98b5");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustFindFlashcardsByAuthor() {
		when(repository.findByAuthor("Maria")).thenReturn(flashcards);
		
		List<Flashcard> cards = service.findByAuthor("Maria");
		
		assertEquals(cards, flashcards);
		
		verify(repository).findByAuthor("Maria");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustFindFlashcardByIdAndAuthor() {
		Flashcard card = service.findByIdAndAuthor("67e04d0d365ac80f157d98b4","Maria");
		
		assertEquals(card, flashcards.get(0));
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFound() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.findByIdAndAuthor("67e04d0d365ac80f157d98b5", "Maria");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFoundByIdAndAuthor() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.findByIdAndAuthor("67e04d0d365ac80f157d98b4", "Joao");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b4 not found.");
		verifyNoMoreInteractions(repository);
	}
}
