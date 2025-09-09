package com.ericarfs.memocards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.ericarfs.memocards.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	@Query("{ $and: [ {'id': ?0}, {'deletedAt':null} ]}")
	@Update("{ '$set' : { 'deletedAt' : new Date(), 'username': ?1} }")
	void deleteById(String id, String newUsername);

	List<User> findAll();

	@Query("{ $and: [{ 'username': { $regex: '^?0$', $options: 'i' } }, { 'deletedAt': null }] }")
	Optional<User> findByUsername(String username);
}
