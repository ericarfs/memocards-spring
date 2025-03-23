package com.ericarfs.memocards.dto;

import java.io.Serializable;

import com.ericarfs.memocards.entity.Flashcard;

public class FlashcardDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String expression;
	private String meaning;
	private String example;
	
	public FlashcardDTO() {}
	
	public FlashcardDTO(Flashcard obj) {
		id = obj.getId();
		expression = obj.getExpression();
		meaning = obj.getMeaning();
		example = obj.getExample();
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
}
