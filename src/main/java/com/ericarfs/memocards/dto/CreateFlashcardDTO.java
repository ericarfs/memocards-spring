package com.ericarfs.memocards.dto;

import com.ericarfs.memocards.entity.Flashcard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFlashcardDTO(
		@NotNull @NotBlank @NotEmpty @Size(max = 200, message = "Expression should be up to 200 characters!") String expression,
		@NotNull @NotBlank @NotEmpty @Size(max = 200, message = "Expression meaning should be up to 200 characters!") String meaning,
		@Size(max = 200, message = "Expression example should be up to 200 characters!") String example) {

	public Flashcard mapToFlashcard(AuthorDTO author) {
		return new Flashcard(null, expression, meaning, example, author);
	}
}
