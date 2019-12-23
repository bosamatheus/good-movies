package com.bosa.movieinfoservice.resources;

import com.bosa.movieinfoservice.models.Movie;

import com.bosa.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/movie")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private WebClient webClient;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") Integer movieId) {

        MovieSummary movieSummary = webClient
                .get()
                .uri("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey)
                .retrieve()
                .bodyToMono(MovieSummary.class)
                .block();

        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
