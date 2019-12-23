package com.bosa.moviecatalogservice.services;

import com.bosa.moviecatalogservice.models.CatalogItem;
import com.bosa.moviecatalogservice.models.Movie;
import com.bosa.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MovieInfo {

    @Autowired
    private WebClient webClient; // Recommended by Spring's documentation

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
                    threadPoolKey = "movieInfoPool",
                    threadPoolProperties = {
                        @HystrixProperty(name = "coreSize", value = "20"),
                        @HystrixProperty(name = "maxQueueSize", value = "10")
                    }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = webClient.get()
                .uri("http://movie-info-service/movie/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }
}
