package com.weatherapp.myweatherapp.repository;

public class VisualcrossingRepositoryTest {

  @org.mockito.Mock
  private org.springframework.web.client.RestTemplate restTemplate;

  @org.mockito.InjectMocks
  private VisualcrossingRepository repository;

  @org.junit.jupiter.api.BeforeEach
  public void setup() {
    org.mockito.MockitoAnnotations.openMocks(this);
    // Set property values manually for testing
    repository.url = "http://localhost/api/";
    repository.key = "testKey";
    repository.unitGroup = "metric";
    repository.contentType = "json";
  }

  @org.junit.jupiter.api.Test
  public void testGetByCityWithoutParams() {
    // Create dummy CityInfo result
    com.weatherapp.myweatherapp.model.CityInfo expectedCityInfo = new com.weatherapp.myweatherapp.model.CityInfo();
    String city = "NewYork";
    String expectedUri = "http://localhost/api/timeline/NewYork?unitGroup=metric&key=testKey&contentType=json";

    // Mock the call
    org.mockito.Mockito.when(restTemplate.getForObject(expectedUri, com.weatherapp.myweatherapp.model.CityInfo.class))
        .thenReturn(expectedCityInfo);

    com.weatherapp.myweatherapp.model.CityInfo result = repository.getByCity(city);

    org.mockito.Mockito.verify(restTemplate, org.mockito.Mockito.times(1))
        .getForObject(expectedUri, com.weatherapp.myweatherapp.model.CityInfo.class);
    org.junit.jupiter.api.Assertions.assertEquals(expectedCityInfo, result);
  }

  @org.junit.jupiter.api.Test
  public void testGetByCityWithParams() {
    // Create dummy CityInfo result
    com.weatherapp.myweatherapp.model.CityInfo expectedCityInfo = new com.weatherapp.myweatherapp.model.CityInfo();
    String city = "London";
    java.util.List<String> params = java.util.Arrays.asList("temp", "humidity");

    // Expected URI construction as per the repository logic:
    // Base URI: http://localhost/api/timeline/London?unitGroup=metric
    // Adding elements: &elements=temp%2Chumidity%2C&include=current
    // Adding key and contentType: &key=testKey&contentType=json
    String expectedUri = "http://localhost/api/timeline/London?unitGroup=metric" +
        "&elements=temp%2Chumidity%2C&include=current" +
        "&key=testKey&contentType=json";

    // Mock the call
    org.mockito.Mockito.when(restTemplate.getForObject(expectedUri, com.weatherapp.myweatherapp.model.CityInfo.class))
        .thenReturn(expectedCityInfo);

    com.weatherapp.myweatherapp.model.CityInfo result = repository.getByCity(city, params);

    org.mockito.Mockito.verify(restTemplate, org.mockito.Mockito.times(1))
        .getForObject(expectedUri, com.weatherapp.myweatherapp.model.CityInfo.class);
    org.junit.jupiter.api.Assertions.assertEquals(expectedCityInfo, result);
  }
}