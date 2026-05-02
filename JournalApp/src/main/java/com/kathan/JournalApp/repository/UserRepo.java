package com.kathan.JournalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kathan.JournalApp.entities.Users;

public interface UserRepo extends MongoRepository<Users, String>{
	public Users findByUsername(String username);
}
