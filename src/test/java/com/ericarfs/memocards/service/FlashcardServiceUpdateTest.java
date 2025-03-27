package com.ericarfs.memocards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
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
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.FlashcardRepository;

@ExtendWith(MockitoExtension.class)
public class FlashcardServiceUpdateTest {
	@InjectMocks
	private FlashcardService service;
	
	@Mock
	private FlashcardRepository repository;
	
	private List<Flashcard> flashcards = new ArrayList<>();
	
	private Flashcard card;
	
	@BeforeEach
	public void setUp() {
		Flashcard c1 = new Flashcard("67e04d0d365ac80f157d98b4", "Expression1", "Meaning1", "Example1", new AuthorDTO("68e04d0d365ac80f157d98b4", "Maria"));
		Flashcard c2 = new Flashcard("67e04d0d365ac80f157d98b6", "Expression1", "Meaning2", "Example1", new AuthorDTO("68e04d0d365ac80f157d98b4", "Maria"));

		card = new Flashcard();
		card.setExpression("Expression");
		card.setMeaning("Meaning");
		card.setExample("Example");
		
		flashcards.add(c1);
		flashcards.add(c2);
		
		lenient().when(repository.findById("67e04d0d365ac80f157d98b4"))
		.thenReturn(Optional.of(flashcards.get(0)));

		lenient().when(repository.findById("67e04d0d365ac80f157d98b5"))
				.thenThrow(new ResourceNotFoundException("Flashcard with id 67e04d0d365ac80f157d98b5 not found."));
	}
	
	@Test
	void mustUpdateAFlashcard() {
		when(repository.findByAll("Expression", "Meaning", "Example", "Maria")).thenReturn(null);

		doAnswer(invocation -> {
			Flashcard obj = flashcards.get(0);
			obj.setExpression(card.getExpression());
			obj.setMeaning(card.getMeaning());
			obj.setExample(card.getExample());

			return obj;
		}).when(repository).save(flashcards.get(0));
		
		Flashcard obj = service.update("67e04d0d365ac80f157d98b4", "Maria", card);
		
		assertEquals(obj, flashcards.get(0));
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verify(repository).findByAll("Expression", "Meaning", "Example", "Maria");
		verify(repository).save(flashcards.get(0));
		verifyNoMoreInteractions(repository);
	}
	
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFound() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.update("67e04d0d365ac80f157d98b5", "Maria", card);
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b5");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b5 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenFlashcardNotFoundByIdAndAuthor() {
		final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
			service.update("67e04d0d365ac80f157d98b4", "Joao",card);
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		assertEquals(e.getMessage(), "Flashcard with id 67e04d0d365ac80f157d98b4 not found.");
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void mustThrowExceptionWhenCreateDuplicatedFlashcard() {
		when(repository.findByAll("Expression1", "Meaning2", "Example1", "Maria")).thenReturn(flashcards.get(1));

		Flashcard dupCard = new Flashcard();
		dupCard.setExpression("Expression1");
		dupCard.setMeaning("Meaning2");
		dupCard.setExample("Example1");
		
		final DatabaseException e = assertThrows(DatabaseException.class, () -> {
			service.update("67e04d0d365ac80f157d98b4", "Maria", dupCard);
		});
		
		verify(repository).findById("67e04d0d365ac80f157d98b4");
		verify(repository).findByAll("Expression1", "Meaning2", "Example1", "Maria");
		assertEquals(e.getMessage(), "Duplicated Flashcard.");
		verifyNoMoreInteractions(repository);
	}
}
