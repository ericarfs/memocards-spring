package com.ericarfs.memocards.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.ericarfs.memocards.entity.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	
	private List<FlashcardDTO> flashcards;
	
	public UserDTO() {}
	
	public UserDTO(User obj) {
		id = obj.getId();
		username = obj.getUsername();
		flashcards = obj.getFlashcards()
						.stream()
						.map(FlashcardDTO::new)
						.collect(Collectors.toList());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<FlashcardDTO> getFlashcards() {
		return flashcards;
	}

	public void setFlashcards(List<FlashcardDTO> flashcards) {
		this.flashcards = flashcards;
	}
}
