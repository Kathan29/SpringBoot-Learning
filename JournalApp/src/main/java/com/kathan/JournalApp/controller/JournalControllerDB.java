package com.kathan.JournalApp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.JournalEntry;
import com.kathan.JournalApp.entities.Users;
import com.kathan.JournalApp.service.JournalService;
import com.kathan.JournalApp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalControllerDB {
	private final JournalService service;
	private final UserService userService;

	
	@PostMapping("{username}")
	@Transactional
	public ResponseEntity<JournalEntry> createJournalEntryForUser(@RequestBody JournalEntry j,@PathVariable String username) {
		try {
			if(j.getDate()==null) {
				j.setDate(LocalDate.now());
			}
			service.saveEntry(j); //Saving entry in Journal Entry database
			
			Users user = userService.findByUsername(username);
			if(user==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			user.getJournal_entries().add(j); //Saving entry with user
			userService.saveEntry(user); //Now saving user into database
			
			return new ResponseEntity<>(j,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@GetMapping("{username}")
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username){
		Users user = userService.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		List<JournalEntry> allEntry = user.getJournal_entries();
		
		if(allEntry==null || allEntry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allEntry,HttpStatus.OK);
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getById(@PathVariable String myId) {
		//return service.getById(myId).orElse(null);
		Optional<JournalEntry> journalEntry = service.getById(myId);
		return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> updateById(@PathVariable String myId,@RequestBody JournalEntry newEntry) {

		Optional<JournalEntry> oldEntry = service.getById(myId);
		if(oldEntry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		JournalEntry old = oldEntry.get();
		old.setTitle(newEntry.getTitle()==null ? old.getTitle() : newEntry.getTitle());
		old.setContent(newEntry.getContent()==null ? old.getContent() : newEntry.getContent());
		
		service.saveEntry(old);
		return new ResponseEntity<>(old,HttpStatus.OK);
	}
	
	@DeleteMapping("/{myId}/{username}")
	@Transactional
	public ResponseEntity<?> deleteById(@PathVariable String myId,@PathVariable String username) {
		Users user = userService.findByUsername(username);
		if(user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		user.getJournal_entries().removeIf(entry -> entry.getId().equals(myId));
		userService.saveEntry(user);
		service.deleteById(myId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
