package com.bosa.moviecatalogservice.models;

import java.util.ArrayList;
import java.util.List;

public class UserRating {

    private Integer userId;
    private List<Rating> ratings;

    public UserRating() {
        this.ratings = new ArrayList<>();
    }

    public UserRating(Integer userId, List<Rating> ratings) {
        this.userId = userId;
        this.ratings = ratings;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
