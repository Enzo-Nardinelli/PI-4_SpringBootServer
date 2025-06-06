package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String>{
    Optional<UserModel> findByEmail(String email);
}