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
import com.ericarfs.memocards.exceptions.DatabaseException;
import com.ericarfs.memocards.repository.FlashcardRepository;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceInsertTest {
	@InjectMocks
	private FlashcardService service;
	
	@Mock
	private FlashcardRepository repository;
	
	private List<Flashcard> flashcards = new ArrayList<>();
	
	private AuthorDTO author;
	
	@BeforeEach
	public void setUp() {
		author =  new AuthorDTO("68e04d0d365ac80f157d98b4", "Maria");
		Flashcard c1 = new Flashcard("67e04d0d365ac80f157d98b4", "Expression1", "Meaning1", "Example1", author);

		flashcards.add(c1);
		
		lenient().when(repository.findById("67e04d0d365ac80f157d98b4"))
					.thenReturn(Optional.of(flashcards.get(0)));
		
	}
	
	@Test
	void mustInsertAFlashcard() {
		when(repository.findByAll("Expression", "Meaning", "Example", "Maria")).thenReturn(null);
				
		Flashcard card = new Flashcard(null, "Expression", "Meaning", "Example", author);
		
		service.insert(card);
		
		verify(repository).findByAll("Expression", "Meaning", "Example", "Maria");
		verify(repository).insert(card);
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenCreateDuplicatedFlashcard() {
		when(repository.findByAll("Expression1", "Meaning1", "Example1", "Maria")).thenReturn(flashcards.get(0));
				
		Flashcard card = new Flashcard(null, "Expression1", "Meaning1", "Example1", author);
		
		final DatabaseException e = assertThrows(DatabaseException.class, () -> {
			service.insert(card);
		});
		
		verify(repository).findByAll("Expression1", "Meaning1", "Example1", "Maria");
		assertEquals(e.getMessage(), "Duplicated Flashcard.");
		verifyNoMoreInteractions(repository);
	}

}
