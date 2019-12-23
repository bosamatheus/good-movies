package com.bosa.moviecatalogservice.resources;

import com.bosa.moviecatalogservice.models.*;
import com.bosa.moviecatalogservice.services.MovieInfo;
import com.bosa.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private WebClient webClient; // Recommended by Spring's documentation

    /*
    @Autowired
    private RestTemplate restTemplate; // RestTemplate going to be deprecated soon
    */

    @Autowired
    private UserRatingInfo userRatingInfo;

    @Autowired
    private MovieInfo movieInfo;

    @RequestMapping("/{userId}")
    public Catalog getCatalog(@PathVariable("userId") Integer userId) {

        UserRating userRating = userRatingInfo.getUserRating(userId);
        // Alternative way:
        // UserRating userRating = restTemplate.getForObject("http://localhost:8084/ratingsdata/users/" + userId, UserRating.class);

        // For each movie, call movie info service and get details
        Catalog catalog = new Catalog();
        userRating.getRatings().forEach(rating -> catalog.getItems().add(movieInfo.getCatalogItem(rating)));

        return catalog;
    }
}
