package com.kathan.JournalApp.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
	@Id
	private String id;
	private String title;
	private String content;
	private LocalDate date;

}
