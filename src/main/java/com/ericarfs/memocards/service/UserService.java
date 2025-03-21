package com.ericarfs.memocards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericarfs.memocards.entity.User;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
	}

	public void insert(User obj) {
		repository.insert(obj);
	}
	
	public void delete(String id) {
		if (repository.existsById(id))
			repository.deleteById(id);
		else
			throw new ResourceNotFoundException("User with id " + id + " not found.");

	}

}
