package com.ericarfs.memocards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.Flashcard;

@Repository
public interface FlashcardRepository extends MongoRepository<Flashcard, String>{
	@Query("{$and: [ {'expression' : ?0 }, {'meaning': ?1 }, {'example': ?2 } ]}")
	Flashcard findByAll(String expression, String meaning, String example);
	
	Flashcard findByExpression(String expression);
}
