package com.bosa.moviecatalogservice.services;

import com.bosa.moviecatalogservice.models.Rating;
import com.bosa.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private WebClient webClient;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        }
    )
    public UserRating getUserRating(@PathVariable("userId") Integer userId) {
        return webClient.get()
                .uri("http://ratings-data-service/ratingsdata/users/" + userId)
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();
    }

    private UserRating getFallbackUserRating(@PathVariable("userId") Integer userId) {
        return new UserRating(userId, Arrays.asList(new Rating(0, 0)));
    }
}
