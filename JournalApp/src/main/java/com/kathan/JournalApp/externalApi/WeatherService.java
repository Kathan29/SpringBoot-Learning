package com.kathan.JournalApp.externalApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.kathan.JournalApp.cache.AppCache;
import com.kathan.JournalApp.service.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
	
	private final RestTemplate rest;
	private final WebClient webClient;
	private final AppCache appCache;
	private final RedisService redisService;
	
	@Value("${weather_api_key}")
	private String apiKey;
	
	
	public WeatherResponse getWeatherUsingRestTemplate(String city) {
		
		WeatherResponse weatherResponse = redisService.get("weather_of_"+city, WeatherResponse.class);
		//Check if already there in redis or not 
		if(weatherResponse!=null) {
			return weatherResponse;
		}else {
			String api = appCache.getApiCache().get("weather_api");
			String finalApi = api.replace("<CITY>", city).replace("<API_KEY>",apiKey);
			log.info("Weather api URL : "+finalApi);
			ResponseEntity<WeatherResponse> response = rest.exchange(finalApi, HttpMethod.GET,null,WeatherResponse.class);
			weatherResponse = response.getBody();
			//If we get response from api, then we store in redis
			if(weatherResponse!=null) {
				redisService.set("weather_of_"+city, weatherResponse, 300l);
			}
			return weatherResponse;
		}
	}
	
	@Cacheable(cacheNames = "weather",
			key = "#city.toLowerCase()",
			unless = "#result == null")
	public WeatherResponse getWeatherUsingRedisCachable(String city) {
		String api = appCache.getApiCache().get("weather_api");
		String finalApi = api.replace("<CITY>", city).replace("<API_KEY>",apiKey);
		log.info("Calling Weather API for {}", city);
		ResponseEntity<WeatherResponse> response = rest.exchange(finalApi, HttpMethod.GET,null,WeatherResponse.class);
		return response.getBody();
	}
	
	/*
	// Just an example for Post request using rest template , main thing is HttpEntity and HttpHeaders

	public void postUsingRestTemplate(String city) {
		String finalApi = api.replace("CITY", city).replace("API_KEY",apiKey);
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setBasicAuth("username","password"); //eg httpHeader.setBasicAuth("ram","ram");
		Users user = Users.builder().username("abcd").password("abcd").build();
		HttpEntity<Users> httpEntity = new HttpEntity(user,httpHeader);
		ResponseEntity<WeatherResponse> response = rest.exchange(finalApi, HttpMethod.POST,httpEntity,WeatherResponse.class);
	}
	*/
	
	public WeatherResponse getWeatherUsingWebService(String city) {
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/v1/current.json")
						.queryParam("key", apiKey)
						.queryParam("q", city)
						.queryParam("aqi", "no")
						.build())
				.retrieve()
				.bodyToMono(WeatherResponse.class)
				.block();
	}
	
	/*
	//Post Request Using Web Service 

	public WeatherResponse postUsingWebService(String city) {
		Users user = Users.builder().username("abcd").password("abcd").build();
		
		 return webClient.post()
				.uri("/v1/admin/create")
				.headers(h -> h.setBasicAuth("myUsername","myPassword"))
					//If you want to do through JWT then .headers(h->h.setBearerAuth(token))
				.bodyValue(user)
				.retrieve()
				.bodyToMono(WeatherResponse.class)
				.block();
	}
	*/
}
