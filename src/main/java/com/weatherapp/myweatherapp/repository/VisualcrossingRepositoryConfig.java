package com.weatherapp.myweatherapp.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class VisualcrossingRepositoryConfig {
    @Bean
    public RestTemplate restTemplate() {
      ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      MappingJackson2HttpMessageConverter messageConverter =
        new MappingJackson2HttpMessageConverter(objectMapper);
  
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(0, messageConverter);
      return restTemplate;
    }
}
