package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WeatherService weatherService;

  @Test
  public void testForecastByCityWithoutParams() throws Exception {
    String city = "TestCity";
    CityInfo dummyCityInfo = new CityInfo();
    // When no explicit parameters passed, default is empty list
    Mockito.when(weatherService.forecastByCity(Mockito.eq(city), Mockito.anyList()))
        .thenReturn(dummyCityInfo);

    mockMvc.perform(get("/forecast/{city}", city))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testForecastByCityWithParams() throws Exception {
    String city = "TestCity";
    CityInfo dummyCityInfo = new CityInfo();
    // Provide a list of parameters via request param "param"
    Mockito.when(weatherService.forecastByCity(Mockito.eq(city), Mockito.anyList()))
        .thenReturn(dummyCityInfo);

    mockMvc.perform(get("/forecast/{city}", city)
            .param("param", "temp", "humidity"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testCompareDaylight() throws Exception {
    // /longestday?city=City1&city=City2
    Map<String, Object> dummyResponse = new HashMap<>();
    dummyResponse.put("longestDayCity", "City1");
    dummyResponse.put("City1", "12:00");
    dummyResponse.put("City2", "10:00");

    Mockito.when(weatherService.compareDaylight(Mockito.anyList()))
        .thenReturn(dummyResponse);

    mockMvc.perform(get("/longestday")
            .param("city", "City1", "City2"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testRainCheck() throws Exception {
    // /raincheck?city=City1&city=City2
    Map<String, Object> dummyResponse = new HashMap<>();
    dummyResponse.put("rainingCities", Arrays.asList("City1"));

    Mockito.when(weatherService.rainCheck(Mockito.anyList()))
        .thenReturn(dummyResponse);

    mockMvc.perform(get("/raincheck")
            .param("city", "City1", "City2"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}