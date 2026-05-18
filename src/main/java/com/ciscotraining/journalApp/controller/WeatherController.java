package com.ciscotraining.journalApp.controller;

import com.ciscotraining.journalApp.api.response.WeatherResponse;
import com.ciscotraining.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping
    public ResponseEntity<?> getWeatherForCities(@RequestBody List<String> cities) {
        if (cities == null || cities.isEmpty()) {
            return new ResponseEntity<>("Cities list can not be empty", HttpStatus.BAD_REQUEST);
        }

        Map<String, WeatherResponse> weatherData= weatherService.getWeatherForCities(cities);
        return new ResponseEntity<>(weatherData, HttpStatus.OK);

    }
}
