package com.weatherapp.myweatherapp.controller;

// ----------------- Imports -----------------
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class to handle weather endpoints.
 * 
 *  @endpoint /forecast/{city}
 *  @endpoint /longestday?city=city1&city=city2
 *  @endpoint /raincheck?city=city1&city=city2
 */
@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  // ----------------- Endpoints -----------------
  /**
   * Get the weather forecast for a given city
   * Usage: /forecast/{city}
   * @param city - the city to get the forecast for
   * @return CityInfo object with the forecast data
   */
  @GetMapping("/forecast/{city}")
  public ResponseEntity<CityInfo> forecastByCity(@PathVariable("city") String city, @RequestParam(value = "param", required = false, defaultValue = "") List<String> params) {

    if (params == null) {
      // If no parameters are provided, return the default forecast (empty list of parameters)
      params = new ArrayList<>();
    }
    CityInfo ci = weatherService.forecastByCity(city, params);

    return ResponseEntity.ok(ci);
  }

  // Task 1: given two city names, compare the length of the daylight hours and return the city with the longest day

  /**
   * Compare the daylight hours between cities, and return the city with the longest day
   * Usage: /longestday?city=city1&city=city2
   * @param cities - the list of cities to compare
   * @return Map<String, Object> with the city with the longest day and the daylight time for each city
   */
  @GetMapping("/longestday")
  public ResponseEntity<Map<String, Object>> compareDaylight(@RequestParam("city") List<String> cities) {
      if (cities == null || cities.size() < 2) {
        throw new IllegalArgumentException("At least two cities must be provided");
      }
      Map<String, Object> response = weatherService.compareDaylight(cities);

      return ResponseEntity.ok(response);
  }
  // Task 2: given two city names, check which city its currently raining in

  /**
   * Check if it is currently raining in the given cities
   * Usage: /raincheck?city=city1&city=city2
   * @param cities - the list of cities to check
   * @return Map<String, Object> with the cities that are currently raining
   */
  @GetMapping("/raincheck")
  public ResponseEntity<Map<String, Object>> rainCheck(@RequestParam("city") List<String> cities) {
    if (cities == null || cities.size() < 2) {
      throw new IllegalArgumentException("At least two cities must be provided");
    }
    Map<String, Object> response = weatherService.rainCheck(cities);
    return ResponseEntity.ok(response);
  }
}
