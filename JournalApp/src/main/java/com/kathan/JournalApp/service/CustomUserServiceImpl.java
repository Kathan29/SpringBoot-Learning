package com.kathan.JournalApp.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService{
	private final UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found with username : "+username);
		}
		
		UserDetails userDetails = User.builder().username(user.getUsername())
								.password(user.getPassword())
								.roles(user.getRoles().toArray(new String[0]))
								.build();
		
		return userDetails;
	}
	
	
}
