package com.kathan.JournalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	
	private final UserRepo repo;
	private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Users saveUserEntry(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		 user.setRoles(Arrays.asList("USER"));
		return repo.save(user);
	}
	
	public void saveEntry(Users user) {
		repo.save(user);
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
	
	public void deleteByUsername(String username) {
		repo.deleteByUsername(username);
	}

	public Users saveAdminEntry(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		return repo.save(user);
	}
	
	public void getUsernamePassword() {
		Users user = repo.getUsernamePassword("kathan@gmail.com");
		log.info(user.getUsername());
		log.info(user.getPassword());
		//In query we wrote such that we recieve username password only, hence id should be null here.
		log.info(user.getId());
	}

}
