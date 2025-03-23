package com.ericarfs.memocards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateFlashcardDTO(
		@NotNull @NotBlank @NotEmpty String expression, 
		@NotNull @NotBlank @NotEmpty String meaning, 
		String example) {
}
