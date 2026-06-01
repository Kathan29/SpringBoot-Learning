package com.kathan.JournalApp.externalApi;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Current current;
	private Location location;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Current implements Serializable{
		private static final long serialVersionUID = 1L;
		@JsonProperty("temp_c")
	    private double temperature;
		
		@JsonProperty("feelslike_c")
	    private double feelsLike;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Location implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("name")
	    private String city;
		
	    private String region;
	    private String country;
	    private String localtime;
	}
}



