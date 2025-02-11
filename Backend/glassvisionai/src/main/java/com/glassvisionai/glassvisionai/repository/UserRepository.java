package com.glassvisionai.glassvisionai.repository;


import com.glassvisionai.glassvisionai.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String> { // Use String for the ID type
    User findByUsername(String username);
}
