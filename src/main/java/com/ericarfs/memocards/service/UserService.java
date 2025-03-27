package com.ericarfs.memocards.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.entity.enums.Role;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
	}
	
	public void delete(String id) {
		User user = findById(id);
		
		String username = user.getUsername();
		String newUsername = username + passwordEncoder.encode(username).substring(0, 15);

		repository.deleteById(id, newUsername);
	}

	public void register(String username, String password) {
		String encryptedPassword = passwordEncoder.encode(password);
		User user = new User(null, username, encryptedPassword, Role.BASIC);
		repository.insert(user);
	}
	
	public Boolean validatePassword(String userPassword, String password) {
		return passwordEncoder.matches(password, userPassword);
	}
	
	public Optional<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public void addFlashcard(User user, Flashcard card) {
		user.getFlashcards().add(card);
		repository.save(user);
	}
}
