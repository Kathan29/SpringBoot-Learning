package com.kathan.JournalApp.externalApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherService {
	
	private final RestTemplate rest;
	
	@Value("${weather_api_key}")
	private String apiKey;
	private static final String api = "https://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";
	
	public WeatherResponse getWeather(String city) {
		String finalApi = api.replace("CITY", city).replace("API_KEY",apiKey);
		ResponseEntity<WeatherResponse> response = rest.exchange(finalApi, HttpMethod.GET,null,WeatherResponse.class);
		return response.getBody();
	}
	
}
