package com.ericarfs.memocards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericarfs.memocards.dto.AuthorDTO;
import com.ericarfs.memocards.dto.CreateFlashcardDTO;
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
	
	public List<Flashcard> findByAuthor(String username) {
		return repository.findByAuthor(username);
	}
	
	public Flashcard findById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Flashcard with id " + id + " not found."));
	}
	
	public Flashcard findByIdAndAuthor(String id, String username) {
		Flashcard obj = findById(id);
		
		if (obj == null || !obj.getAuthor().username().equals(username))
			throw new ResourceNotFoundException("Flashcard with id " + id + " not found.");
		
		return obj;
	}

	public void insert(Flashcard obj) {
		if (repository.findByAll(obj.getExpression(), obj.getMeaning(), obj.getExample(), obj.getAuthor().username()) != null)
			throw new DatabaseException("Duplicated Flashcard.");

		repository.insert(obj);
	}
	
	public Flashcard update(String id, String username, Flashcard newObj) {
		Flashcard obj = findByIdAndAuthor(id, username);
		
		Flashcard findObj = repository.findByAll(newObj.getExpression(), newObj.getMeaning(), newObj.getExample(), username);
		
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
	
	public void deleteByUser(String id, String username) {
		findByIdAndAuthor(id, username);
		
		repository.deleteById(id);

	}
	
	public void delete(String id) {
		findById(id);
		repository.deleteById(id);
	}
	
	public Flashcard mapToFlashcard(CreateFlashcardDTO objDto, AuthorDTO author) {
		return new Flashcard(null, objDto.expression(), objDto.meaning(), objDto.example(), author);
	}

}
