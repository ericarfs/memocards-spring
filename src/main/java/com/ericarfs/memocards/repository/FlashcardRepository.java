package com.ericarfs.memocards.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.Flashcard;

@Repository
public interface FlashcardRepository extends MongoRepository<Flashcard, String> {
	@Query("{ $and: [ " +
			"{ 'expression': { $regex: '^?0$', $options: 'i' } }, " +
			"{ 'meaning': { $regex: '^?1$', $options: 'i' } }, " +
			"{ 'example': { $regex: '^?2$', $options: 'i' } }, " +
			"{ 'author.username': { $regex: '^?3$', $options: 'i' } } " +
			"] }")
	Flashcard findByAll(String expression, String meaning, String example, String authorUsername);

	@Query("{ 'expression': { $regex: '^?0$', $options: 'i' } }")
	Flashcard findByExpression(String expression);

	@Query("{ 'author.username': { $regex: '^?0$', $options: 'i' } }")
	List<Flashcard> findByAuthor(String username);

}
