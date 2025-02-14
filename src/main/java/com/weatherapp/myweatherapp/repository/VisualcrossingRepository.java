package com.weatherapp.myweatherapp.repository;

// ----------------- Imports -----------------
import com.weatherapp.myweatherapp.model.CityInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
/**
 * Repository class to handle API calls to the Visual Crossing Weather API
 *
 */
@Repository
public class VisualcrossingRepository {
  // Base URL for the Visual Crossing Weather API
  @Value("${weather.visualcrossing.url}")
  String url;
  // API key for the Visual Crossing Weather API
  @Value("${weather.visualcrossing.key}")
  String key;
  // Unit group for the Visual Crossing Weather API (us, metric, uk)
  @Value("${weather.visualcrossing.unitGroup}")
  String unitGroup;
  // Content type for the Visual Crossing Weather API (json, csv, xlxs)
  @Value("${weather.visualcrossing.contentType}")
  String contentType;

  // Use the custom restTemplate bean defined in VisualcrossingRepositoryConfig
  private final RestTemplate restTemplate;

  public VisualcrossingRepository(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  /**
   * Get the weather data for a city using the Visual Crossing Weather API
   * @param city - the city to get the weather data for
   * @return CityInfo object containing the weather data
   */

   public CityInfo getByCity(String city) {
    String uri = constructUri(city, null);
    // RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, CityInfo.class);
   }

  /**
   * Get the weather data for a city using the Visual Crossing Weather API
   * @param city - the city to get the weather data for
   * @param params - A list of parameters to include in the API call
   * @return CityInfo object containing the weather data
   */
  public CityInfo getByCity(String city, List<String> params) {
    String uri = constructUri(city, params);
    System.out.println(String.format("Calling API with URI: %s", uri));
    // RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, CityInfo.class);
  }

  /**
   * Construct a URI for the Visual Crossing Weather API
   * @param city - the city to get the weather data for
   * @param params - A list of parameters to include in the API call
   * @return String with the URI for the API call
   */
  private String constructUri(String city, List<String> params) {
    String uri = String.format("%s%s%s?unitGroup=%s", url, "timeline/", city, unitGroup);

    if (params != null && params.size() > 0) {
      uri += "&elements=";
      for (String param : params) {
        uri += param + "%2C";
      }
      uri += "&include=current";
    }
    uri += String.format("&key=%s&contentType=%s", key, contentType); // add key and contentType to the uri

    return uri;
  }
}
