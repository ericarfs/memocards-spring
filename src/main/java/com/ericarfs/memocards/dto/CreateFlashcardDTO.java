package com.ericarfs.memocards.dto;

import com.ericarfs.memocards.entity.Flashcard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateFlashcardDTO(
		@NotNull @NotBlank @NotEmpty String expression, 
		@NotNull @NotBlank @NotEmpty String meaning, 
		String example) {
	
	
	public Flashcard mapToFlashcard(AuthorDTO author) {
		return new Flashcard(null, expression, meaning, example, author);
	}
}
