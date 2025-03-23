package com.ericarfs.memocards.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ericarfs.memocards.dto.FlashcardDTO;
import com.ericarfs.memocards.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document
(collection="tb_users")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
		
	@NotEmpty(message = "Username must not be empty")
	@NotBlank(message = "Username must not be blank")
	@NotNull(message = "Username must not be null")
	@Indexed(unique=true) 
	private String username;
	
	@NotEmpty(message = "Password must not be empty")
	@NotBlank(message = "Password must not be blank")
	@NotNull(message = "Password must not be null")
	private String password;
	
	private String role;
	
	@CreatedDate
	private Instant createdAt;
	
	private Instant deletedAt = null;
	
	@DBRef(lazy = true)
	private List<FlashcardDTO> flashcards = new ArrayList<>();
	
	public User() {
	}

	public User(String id, String username, String password, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role.name();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return Role.valueOf(role); 
	}

	public void setRole(Role role) {
		this.role = role.name();
	}
	
	public List<FlashcardDTO> getFlashcards() {
		return flashcards;
	}
	
	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Instant deletedAt) {
		this.deletedAt = deletedAt;
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
