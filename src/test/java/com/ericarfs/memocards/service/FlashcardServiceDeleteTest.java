package com.ericarfs.memocards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
public class FlashcardServiceDeleteTest {
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
		
		lenient().doAnswer(invocation -> {
			flashcards.remove(0);
			assertEquals(0, flashcards.size());
			return null;
		}).when(repository).deleteById("67e04d0d365ac80f157d98b4");
		
	}
	
	@Test
	void mustDeleteAFlashcardByUser() {
		service.deleteByUser("67e04d0d365ac80f157d98b4", "Maria");
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verify(repository).deleteById("67e04d0d365ac80f157d98b4");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFound() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteByUser("67e04d0d365ac80f157d98b5", "Maria");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFoundByIdAndAuthor() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteByUser("67e04d0d365ac80f157d98b4", "Joao");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b4 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustDeleteAFlashcardById() {
		service.delete("67e04d0d365ac80f157d98b4");
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verify(repository).deleteById("67e04d0d365ac80f157d98b4");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFoundById() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.delete("67e04d0d365ac80f157d98b5");
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	

}
