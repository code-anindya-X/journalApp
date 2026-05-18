package com.ciscotraining.journalApp.service;

import com.ciscotraining.journalApp.api.response.WeatherResponse;
import com.ciscotraining.journalApp.cache.AppCache;
import com.ciscotraining.journalApp.controller.WeatherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.lang.invoke.CallSite;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    public String apiKey;

//    public static final String API= "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;




    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        else{
            String finalAPI= appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city).replace("<apiKey>", apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }

    }

    public Map<String, WeatherResponse> getWeatherForCities(List<String> cities) {
        ExecutorService executorService = Executors.newFixedThreadPool(2); //cities.size()
        Map<String, CompletableFuture<WeatherResponse>> responseMap = new HashMap<>(); //HashMap

        try{
            for (String city : cities){
                CompletableFuture<WeatherResponse> future =
                        CompletableFuture.supplyAsync(()->getWeather(city), executorService)
                                .exceptionally(e->{
                                    e.printStackTrace();
                                    return null;
                                });
                responseMap.put(city, future);
            }

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(responseMap.values().toArray(new CompletableFuture[0]));
            allFutures.join();

            Map<String, WeatherResponse> result= new HashMap<>();

            for(String city : responseMap.keySet()){
                result.put(city, responseMap.get(city).join());
            }
            return result;
        }finally {
            executorService.shutdown();
        }


    }
}







//Callable<WeatherResponse> task = new Callable<WeatherResponse>() {
//    @Override
//    public WeatherResponse call() throws Exception {
//        return getWeather(city);
//    }
//};
