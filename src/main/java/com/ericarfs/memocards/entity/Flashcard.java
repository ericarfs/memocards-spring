package com.ericarfs.memocards.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document(collection="tb_flashcards")
public class Flashcard implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@NotEmpty(message = "Expression must not be empty")
	@NotBlank(message = "Expression must not be blank")
	@NotNull(message = "Expression must not be null")
	private String expression;
	
	@NotEmpty(message = "Meaning must not be empty")
	@NotBlank(message = "Meaning must not be blank")
	@NotNull(message = "Meaning must not be null")
	private String meaning;
	
	private String example;
	
	private final Instant updatedAt = Instant.now();
	
	public Flashcard() {
	}

	public Flashcard(String id, String expression, String meaning, String example) {
		super();
		this.id = id;
		this.expression = expression;
		this.meaning = meaning;
		this.example = example;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flashcard other = (Flashcard) obj;
		return Objects.equals(id, other.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Instant getCreatedAt() {
		return updatedAt;
	}
	
}
