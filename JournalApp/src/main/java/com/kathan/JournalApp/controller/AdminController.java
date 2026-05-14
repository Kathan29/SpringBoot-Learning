package com.kathan.JournalApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final UserService service;
	
	@GetMapping("/get-users")
	public ResponseEntity<List<Users>> getAllUsers(){
		
		List<Users> allEntry = service.getAllUsers();
		if(allEntry==null || allEntry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allEntry,HttpStatus.OK);
	}
	
	@GetMapping("/{myId}")
	public ResponseEntity<Users> getById(@PathVariable String myId) {
		Optional<Users> user = service.getById(myId);
		return user.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/create-admin")
	public ResponseEntity<Users> create(@RequestBody Users user) {
		try {
			 service.saveAdminEntry(user);
			 return new ResponseEntity<>(user,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
