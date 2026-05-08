package com.kathan.JournalApp.externalApi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
	
	private Current current;
	private Location location;
	
	@Getter @Setter
	public class Current{
		@JsonProperty("temp_c")
	    private double temperature;
		
		@JsonProperty("feelslike_c")
	    private double feelsLike;
	}

	@Getter @Setter
	public class Location{
		
		@JsonProperty("name")
	    private String city;
		
	    private String region;
	    private String country;
	    private String localtime;
	}
}



