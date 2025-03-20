package com.ericarfs.memocards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.exceptions.DatabaseException;
import com.ericarfs.memocards.exceptions.ResourceNotFoundException;
import com.ericarfs.memocards.repository.FlashcardRepository;

@Service
public class FlashcardService {

	@Autowired
	private FlashcardRepository repository;

	public List<Flashcard> findAll() {
		return repository.findAll();
	}

	public Flashcard findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Flashcard with id " + id + " not found."));
	}

	public void insert(Flashcard obj) {
		if (repository.findByAll(obj.getExpression(), obj.getMeaning(), obj.getExample()) != null)
			throw new DatabaseException("Duplicated Flashcard.");

		repository.insert(obj);
	}
	
	public Flashcard update(String id, Flashcard newObj) {
		Flashcard obj = repository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Flashcard with id " + id + " not found."));
		
		Flashcard findObj = repository.findByAll(newObj.getExpression(), newObj.getMeaning(), newObj.getExample());
		
		if (findObj != null) {
			if (!findObj.getId().equals(id))
				throw new DatabaseException("Duplicated Flashcard.");
			return findObj;
		}
		
		obj.setExpression(newObj.getExpression());
		obj.setMeaning(newObj.getMeaning());
		obj.setExample(newObj.getExample());
		
		return repository.save(obj);
	}
	
	public void delete(String id) {
		if (repository.existsById(id))
			repository.deleteById(id);
		else
			throw new ResourceNotFoundException("Flashcard with id " + id + " not found.");

	}

}
