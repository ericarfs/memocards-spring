package com.ericarfs.memocards.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.Flashcard;

@Repository
public interface FlashcardRepository extends MongoRepository<Flashcard, String>{
	@Query("{$and: [ {'expression' : ?0 }, {'meaning': ?1 }, {'example': ?2 }, {'author.username': ?3} ]}")
	Flashcard findByAll(String expression, String meaning, String example, String authorUsername);
	
	Flashcard findByExpression(String expression);
	
	@Query("{ 'author.username': ?0 }")
	List<Flashcard> findByAuthor(String username);
	
	
}
