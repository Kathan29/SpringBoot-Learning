package com.kathan.JournalApp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@PutMapping
	public ResponseEntity<Users> updateById(@RequestBody Users newEntry) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		Users user = service.findByUsername(username);
		
		user.setUsername(newEntry.getUsername()==null ? user.getUsername() : newEntry.getUsername());
		user.setPassword(newEntry.getPassword()==null ? user.getPassword() : newEntry.getPassword());
		
		service.saveUserEntry(user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteByName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		service.deleteByUsername(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
}
