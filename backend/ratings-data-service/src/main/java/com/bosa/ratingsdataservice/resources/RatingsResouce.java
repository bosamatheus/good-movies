package com.bosa.ratingsdataservice.resources;

import com.bosa.ratingsdataservice.models.Rating;
import com.bosa.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResouce {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") Integer movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") Integer userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating(1, 4),
                new Rating(2, 5)
        );
        return new UserRating(userId, ratings);
    }
}
