package com.kathan.JournalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kathan.JournalApp.entities.ApiEntry;
import com.kathan.JournalApp.repository.ApiRepo;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Data
public class AppCache {

	private final ApiRepo apiRepo;
	
	private Map<String,String> apiCache;
	@PostConstruct
	public void init(){
		List<ApiEntry> entry = apiRepo.findAll();
		apiCache = new HashMap<>();
		for(ApiEntry data : entry) {
			apiCache.put(data.getKey(),data.getValue());
		}	
	}
}
