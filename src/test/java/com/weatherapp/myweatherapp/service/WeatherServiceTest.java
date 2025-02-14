package com.weatherapp.myweatherapp.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.model.CityInfo.CurrentConditions;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// class WeatherServiceTest {

//   // TODO: 12/05/2023 write unit tests

// }
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

  @Mock
  VisualcrossingRepository weatherRepo;

  WeatherService weatherService;

  @BeforeEach
  void setUp() {
    weatherService = new WeatherService();
    weatherService.weatherRepo = weatherRepo;
  }

  @Test
  void testForecastByCity() {
    String city = "TestCity";
    CityInfo mockedCityInfo = mock(CityInfo.class);
    // Create a mock for CurrentConditions (assuming this method exists in CityInfo)
    var mockedConditions = mock(CurrentConditions.class);
    // Stub the required methods.
    when(weatherRepo.getByCity(city, new ArrayList<String>())).thenReturn(mockedCityInfo);

    CityInfo result = weatherService.forecastByCity(city, new ArrayList<String>());
    assertNotNull(result);
    assertEquals(mockedCityInfo, result);
  }

  @Test
  void testCompareDaylight() {
    String cityA = "CityA";
    String cityB = "CityB";
    String cityC = "CityC";

    // CityA: 06:00 to 18:00 -> 12 hours => "12:00:00"
    CityInfo cityInfoA = mock(CityInfo.class);
    var condA = mock(CurrentConditions.class);
    when(condA.getSunrise()).thenReturn("06:00:00");
    when(condA.getSunset()).thenReturn("18:00:00");
    when(cityInfoA.getCurrentConditions()).thenReturn(condA);

    // CityB: 05:00 to 20:00 -> 15 hours => "15:00:00"
    CityInfo cityInfoB = mock(CityInfo.class);
    var condB = mock(CurrentConditions.class);
    when(condB.getSunrise()).thenReturn("05:00:00");
    when(condB.getSunset()).thenReturn("20:00:00");
    when(cityInfoB.getCurrentConditions()).thenReturn(condB);

    // CityC: 07:00 to 17:30 -> 10.5 hours => "10:30:00"
    CityInfo cityInfoC = mock(CityInfo.class);
    var condC = mock(CurrentConditions.class);
    when(condC.getSunrise()).thenReturn("07:00:00");
    when(condC.getSunset()).thenReturn("17:30:00");
    when(cityInfoC.getCurrentConditions()).thenReturn(condC);

    when(weatherRepo.getByCity(cityA)).thenReturn(cityInfoA);
    when(weatherRepo.getByCity(cityB)).thenReturn(cityInfoB);
    when(weatherRepo.getByCity(cityC)).thenReturn(cityInfoC);

    List<String> cities = List.of(cityA, cityB, cityC);
    Map<String, Object> result = weatherService.compareDaylight(cities);

    assertNotNull(result);
    assertEquals(cityB, result.get("longestDay"));

    List<Map<String, Object>> cityInfoList = (List<Map<String, Object>>) result.get("cities");
    assertEquals(3, cityInfoList.size());

    for (Map<String, Object> cityMap : cityInfoList) {
      String city = (String) cityMap.get("city");
      String daylightTime = (String) cityMap.get("daylightTime");
      if (city.equals(cityA)) {
        assertEquals("12:00:00", daylightTime);
      } else if (city.equals(cityB)) {
        assertEquals("15:00:00", daylightTime);
      } else if (city.equals(cityC)) {
        assertEquals("10:30:00", daylightTime);
      }
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  void testRainCheck() {
    String cityA = "CityA";
    String cityB = "CityB";
    CityInfo cityInfoA = mock(CityInfo.class);
    var condA = mock(CurrentConditions.class);
    when(condA.getConditions()).thenReturn("Light Rain");
    when(cityInfoA.getCurrentConditions()).thenReturn(condA);
    when(condA.getConditions()).thenReturn("Light Rain");

    CityInfo cityInfoB = mock(CityInfo.class);
    var condB = mock(CurrentConditions.class);
    when(condB.getConditions()).thenReturn("Sunny");
    when(cityInfoB.getCurrentConditions()).thenReturn(condB);

    when(weatherRepo.getByCity(cityA)).thenReturn(cityInfoA);
    when(weatherRepo.getByCity(cityB)).thenReturn(cityInfoB);

    List<String> cities = List.of(cityA, cityB);
    Map<String, Object> result = weatherService.rainCheck(cities);

    assertNotNull(result);

    List<String> rainingCities = (List<String>) result.get("isRaining");
    assertTrue(rainingCities.contains(cityA));
    assertFalse(rainingCities.contains(cityB));

    List<Map<String, Object>> cityInfoList = (List<Map<String, Object>>) result.get("cities");
    assertEquals(2, cityInfoList.size());
    for (Map<String, Object> cityMap : cityInfoList) {
      String city = (String) cityMap.get("city");
      Boolean isRain = (Boolean) cityMap.get("isRain");
      if (city.equals(cityA)) {
        assertTrue(isRain);
      } else if (city.equals(cityB)) {
        assertFalse(isRain);
      }
    }
  }
}