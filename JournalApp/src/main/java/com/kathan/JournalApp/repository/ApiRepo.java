package com.kathan.JournalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kathan.JournalApp.entities.ApiEntry;

public interface ApiRepo extends MongoRepository<ApiEntry, String>{
	
}
