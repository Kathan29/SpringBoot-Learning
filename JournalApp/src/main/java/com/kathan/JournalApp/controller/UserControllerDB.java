package com.kathan.JournalApp.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserControllerDB {
	
	private final UserService service;
	
	@PostMapping
	public ResponseEntity<Users> create(@RequestBody Users user) {
		try {
			service.saveEntry(user);
			return new ResponseEntity<>(user,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Users>> getAllUsers(){
		
		List<Users> allEntry = service.getAllUsers();
		if(allEntry==null || allEntry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allEntry,HttpStatus.OK);
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<Users> getById(@PathVariable String myId) {
		Optional<Users> user = service.getById(myId);
		return user.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<Users> updateById(@RequestBody Users newEntry,@PathVariable String username) {
		Users user = service.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		user.setUsername(newEntry.getUsername()==null ? user.getUsername() : newEntry.getUsername());
		user.setPassword(newEntry.getPassword()==null ? user.getPassword() : newEntry.getPassword());
		
		service.saveEntry(user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable String myId) {
		service.deleteById(myId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
