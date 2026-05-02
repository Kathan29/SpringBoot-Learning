package com.kathan.JournalApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kathan.JournalApp.entities.JournalEntry;
import com.kathan.JournalApp.repository.JournalRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JournalService {
	
	private final JournalRepo repo;
	
	public JournalEntry saveEntry(JournalEntry j) {
		return repo.save(j);
	}


	public List<JournalEntry> getAll() {
		return repo.findAll();
	}


	public Optional<JournalEntry> getById(String myId) {
		return repo.findById(myId);
	}


	public void deleteById(String myId) {
		repo.deleteById(myId);
	}
	


}
