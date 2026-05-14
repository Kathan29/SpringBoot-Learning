package com.kathan.JournalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kathan.JournalApp.entities.Users;

public interface UserRepo extends MongoRepository<Users, String>{
	public Users findByUsername(String username);
	public void  deleteByUsername(String username);
	
	@Query(value= "{'email':?0}",fields = "{'username': 1,'password':1,'id':0}")
	public Users getUsernamePassword(String email);
}
