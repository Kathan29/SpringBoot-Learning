package com.kathan.JournalApp.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	@NonNull
	private String username;
	
	@NonNull
	private String password;
	
	@DBRef
	private List<JournalEntry> journal_entries = new ArrayList<>();
	
	private List<String> roles = new ArrayList<>();
	
	private String email;
	private String sentimentAnalysis;
	
}
