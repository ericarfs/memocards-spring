package com.ericarfs.memocards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.Flashcard;

@Repository
public interface FlashcardRepository extends MongoRepository<Flashcard, String>{

}
