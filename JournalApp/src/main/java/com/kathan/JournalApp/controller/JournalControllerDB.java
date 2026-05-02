package com.kathan.JournalApp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	public JournalEntry create(@RequestBody JournalEntry j) {
		if(j.getDate()==null) {
			j.setDate(LocalDate.now());
		}
		return service.saveEntry(j);
	}
	
	@GetMapping
	public List<JournalEntry> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/id/{myId}")
	public JournalEntry getById(@PathVariable String myId) {
		return service.getById(myId).orElse(null);
	}
	
	@PutMapping("/id/{myId}")
	public JournalEntry updateById(@PathVariable String myId,@RequestBody JournalEntry newEntry) {
		JournalEntry oldEntry = getById(myId);
		if(oldEntry!=null) {
			oldEntry.setTitle(newEntry.getTitle()==null ? oldEntry.getTitle() : newEntry.getTitle());
			oldEntry.setContent(newEntry.getContent()==null ? oldEntry.getContent() : newEntry.getContent());
		}else {
			System.out.println("No Id found");
		}
		
		service.saveEntry(oldEntry);
		return oldEntry;
	}
	
	@DeleteMapping("/id/{myId}")
	public void deleteById(@PathVariable String myId) {
		service.deleteById(myId);
	}

}
