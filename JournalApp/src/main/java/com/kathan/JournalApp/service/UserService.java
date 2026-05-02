package com.kathan.JournalApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepo repo;
	
	public Users saveEntry(Users user) {
		return repo.save(user);
	}


	public List<Users> getAllUsers() {
		return repo.findAll();
	}


	public Optional<Users> getById(String myId) {
		return repo.findById(myId);
	}


	public void deleteById(String myId) {
		repo.deleteById(myId);
	}
	
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}

}
