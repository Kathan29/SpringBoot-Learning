package com.kathan.JournalApp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kathan.JournalApp.entities.JournalEntry;
import com.kathan.JournalApp.service.JournalService;

@RestController
@RequestMapping("/journal")
public class JournalControllerDB {
	private JournalService service;
	public JournalControllerDB(JournalService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry j) {
		try {
			if(j.getDate()==null) {
				j.setDate(LocalDate.now());
			}
			service.saveEntry(j);
			return new ResponseEntity<>(j,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAll(){
		//return service.getAll();
		List<JournalEntry> allEntry = service.getAll();
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
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable String myId) {
		service.deleteById(myId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
