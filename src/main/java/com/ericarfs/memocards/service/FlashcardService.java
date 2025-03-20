package com.ericarfs.memocards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericarfs.memocards.entity.Flashcard;
import com.ericarfs.memocards.repository.FlashcardRepository;

@Service
public class FlashcardService {

	@Autowired
	private FlashcardRepository repository;
	
	public List<Flashcard> findAll(){
		return repository.findAll();
	}

	public void insert(Flashcard obj) {
		if(!obj.getExpression().isBlank() && !obj.getMeaning().isBlank())
			repository.insert(obj);		
	}
	
	

}
