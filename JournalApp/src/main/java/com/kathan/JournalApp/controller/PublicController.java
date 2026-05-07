package com.kathan.JournalApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
	private final UserService service;
	
	@PostMapping("/create-user")
	public ResponseEntity<Users> create(@RequestBody Users user) {
		try {
			 service.saveUserEntry(user);
			 return new ResponseEntity<>(user,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
