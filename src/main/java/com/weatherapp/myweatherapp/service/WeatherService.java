package com.weatherapp.myweatherapp.service;

// ----------------- Imports -----------------
import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to handle weather data
 */
@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  /**
   * Get the weather forecast by city
   * @param city - the city to get the forecast for
   * @return CityInfo object with the forecast
   */
  public CityInfo forecastByCity(String city, List<String> params) {
    return weatherRepo.getByCity(city, params);
  }

  /**
   * Compare the daylight hours between cities, and return the city with the longest day
   * @param cities - the list of cities to compare
   * @return Map<String, Object> with the city with the longest day and the daylight time for each city
   */
  public Map<String, Object> compareDaylight(List<String> cities) {
    String longestDayCity = null;
      long maxDaylightSeconds = Long.MIN_VALUE;
      List<Map<String, Object>> cityInfoList = new ArrayList<>();

      for (String city : cities) {
          CityInfo cityInfo = weatherRepo.getByCity(city);

          LocalTime sunrise = LocalTime.parse(cityInfo.getCurrentConditions().getSunrise());
          LocalTime sunset = LocalTime.parse(cityInfo.getCurrentConditions().getSunset());
          long daylightSeconds = ChronoUnit.SECONDS.between(sunrise, sunset);

          if (daylightSeconds > maxDaylightSeconds) {
              maxDaylightSeconds = daylightSeconds;
              longestDayCity = city;
          }

          long hours = daylightSeconds / 3600;
          long minutes = (daylightSeconds % 3600) / 60;
          long seconds = daylightSeconds % 60;
          String daylightTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

          Map<String, Object> cityInfoMap = new HashMap<>();
          cityInfoMap.put("city", city);
          cityInfoMap.put("daylightTime", daylightTime);
          cityInfoList.add(cityInfoMap);
      }

      Map<String, Object> response = new HashMap<>();
      response.put("longestDay", longestDayCity);
      response.put("cities", cityInfoList);

      return response;
  }

  /**
   * Check which city it is currently raining in (if any) based on the current conditions
   * @param cities - the list of cities to check
   * @return Map<String, Object> with the city, current conditions, and if it is raining
   */
  public Map<String, Object> rainCheck(List<String> cities) {
    Map<String, Object> response = new HashMap<>();
    List<Map<String, Object>> cityInfoList = new ArrayList<>();
    List<String> rainingCities = new ArrayList<>();
    for (String city : cities) {
      CityInfo cityInfo = weatherRepo.getByCity(city);
      String cityConditions = cityInfo.getCurrentConditions().getConditions();
      Boolean raining = cityConditions.contains("Rain");
      if (raining) {
        rainingCities.add(city);
      }

      Map<String, Object> cityInfoMap = new HashMap<>();
      cityInfoMap.put("city", city);
      cityInfoMap.put("conditions", cityConditions);
      cityInfoMap.put("isRain", raining);
      cityInfoList.add(cityInfoMap);
    }

    response.put("isRaining", rainingCities);
    response.put("cities", cityInfoList);


    return response;
  }
}
