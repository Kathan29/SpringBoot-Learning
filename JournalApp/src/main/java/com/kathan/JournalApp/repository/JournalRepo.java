package com.kathan.JournalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kathan.JournalApp.entities.JournalEntry;

public interface JournalRepo extends MongoRepository<JournalEntry, String>{

}
