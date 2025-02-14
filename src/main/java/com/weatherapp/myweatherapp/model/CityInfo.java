package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
// ----------------- Imports -----------------
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;

/**
 * Model class to store weather data from the Visual Crossing Weather API
 * This class is used to map the JSON response from the API to a Java object
 */
public class CityInfo {

  @JsonProperty("address")
  private String address;

  @JsonProperty("description")
  private String description;
  @JsonProperty("currentConditions")
  @JsonMerge
  private CurrentConditions currentConditions = new CurrentConditions();

  @JsonProperty("days")
  @JsonMerge
  private List<Days> days = new ArrayList<>();
  // private List<Days> days;

  // Getter and Setter for address
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }

  // Getter and Setter for description
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  // Getter and Setter for currentConditions
  public CurrentConditions getCurrentConditions() {
    return currentConditions;
  }

  // Getter and Setter for days
  public List<Days> getDays() {
    return days;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CurrentConditions {
    public CurrentConditions() { }
    @JsonProperty("temp")
    private String currentTemperature;

    @JsonProperty("sunrise")
    private String sunrise;

    @JsonProperty("sunset")
    private String sunset;

    @JsonProperty("feelslike")
    private String feelslike;

    @JsonProperty("humidity")
    private String humidity;

    @JsonProperty("conditions")
    private String conditions;

    // Getter for currentTemperature
    public String getCurrentTemperature() {
      return currentTemperature;
    }
    // Getter for sunrise
    public String getSunrise() {
      return sunrise;
    }

    // Getter for sunset
    public String getSunset() {
      return sunset;
    }
    // Getter for feelslike
    public String getFeelslike() {
      return feelslike;
    }

    // Getter and Setter for humidity
    public String getHumidity() {
      return humidity;
    }

    // Getter for conditions
    public String getConditions() {
      return conditions;
    }
  }
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Days {
    public Days() { }
    @JsonProperty("datetime")
    private String date;

    @JsonProperty("temp")
    private String currentTemperature;

    @JsonProperty("tempmax")
    private String maxTemperature;

    @JsonProperty("tempmin")
    private String minTemperature;

    @JsonProperty("conditions")
    private String conditions;

    @JsonProperty("description")
    private String description;

    // Getter for date
    public String getDate() {
      return date;
    }

    // Getter for currentTemperature
    public String getCurrentTemperature() {
      return currentTemperature;
    }

    // Getter for maxTemperature
    public String getMaxTemperature() {
      return maxTemperature;
    }

    // Getter for minTemperature
    public String getMinTemperature() {
      return minTemperature;
    }

    // Getter for conditions
    public String getConditions() {
      return conditions;
    }

    // Getter for description
    public String getDescription() {
      return description;
    }
  }
}