package com.kathan.JournalApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.service.JWTService;
import com.kathan.JournalApp.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
@Slf4j
public class PublicController {
	private final UserService service;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user){
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			
			String jwt = jwtService.generateToken(user.getUsername());
			
			return new ResponseEntity<>(jwt,HttpStatus.OK);
		}catch(Exception e) {
			log.error("Wrong username - password , user not found.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
	}
	
}
