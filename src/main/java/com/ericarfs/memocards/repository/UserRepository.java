package com.ericarfs.memocards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	@Query("{ 'id': ?0 }")
    @Update("{ '$set' : { 'deletedAt' : new Date() } }")
    void deleteById(String id);
	
	@Query("{ 'deletedAt': null }")
	List<User> findAll();
	
	Optional<User> findByUsername(String username);
}
