package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Feedback;
import com.niit.domain.Restaurant;

import java.util.List;

public interface RestaurantService {
    int addRestaurant(Restaurant restaurant);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getByCity(String city);
    Cuisine addCuisine(Cuisine cuisine,String id,int restaurant);
    List<Restaurant> findRestaurantsByCuisineName(String cuisineName);
    List<Cuisine> getCuisine(Integer id);
    List<Restaurant> findRestaurant(String name,String city);
     int rating(String id, int restaurantId, int rating);
     Restaurant getRestaurantById(int id);
     List<Feedback> getFeedbacks(int restaurantId);
     String addFeedback(int restaurantId,Feedback feedback);
     int getRating(String id, int restaurantId);
    List<Cuisine> deleteCuisine(int restaurantId,String cuisine);
    Cuisine editCuisine(int id,Cuisine cuisine);
    public Cuisine getCuisineById(int id,String cuisineId);
}
