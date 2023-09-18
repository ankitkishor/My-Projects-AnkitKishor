/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.service;

import com.niit.domain.Cuisine;
import com.niit.domain.Feedback;
import com.niit.domain.Rated;
import com.niit.domain.Restaurant;
import com.niit.repository.RestaurantIdRepo;
import com.niit.repository.RestaurantRepo;
import com.niit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    RestaurantIdRepo restaurantIdRepo;
    @Autowired
    UserRepo userRepo;
    @Override
    public int addRestaurant(Restaurant restaurant) {
        return (restaurantRepo.save(restaurant).getId());
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> getByCity(String city) {
        return restaurantRepo.findByCity(city);
    }

    @Override
    public Cuisine addCuisine(Cuisine cuisine,String id,int restaurant) {
        cuisine.setId(Integer.toString(restaurant)+cuisine.getName());
        List<Cuisine> cuisines1 = new ArrayList<>();
        if(restaurantRepo.findById(restaurant).get().getCuisines()==null){
            Restaurant restaurant1 = restaurantRepo.findById(restaurant).get();
            restaurant1.setCuisines(new ArrayList<>());
            restaurantRepo.save(restaurant1);
            cuisines1 = restaurantRepo.findById(restaurant).get().getCuisines();
        }
        if(!cuisines1.contains(cuisine)){
       if(userRepo.findById(id).get().getVendorOwnedRestaurant().contains(restaurant)){
           Restaurant restaurant1 = restaurantRepo.findById(restaurant).get();

           List<Cuisine> cuisines = restaurant1.getCuisines();
           cuisines.add(cuisine);
           restaurant1.setCuisines(cuisines);
           restaurantRepo.save(restaurant1);
           return cuisine;
       }}
       return null;
    }

    @Override
    public Cuisine getCuisineById(int id,String cuisineId){
        List<Cuisine> cuisines = restaurantRepo.findById(id).get().getCuisines();
        for (Cuisine cuisine:cuisines) {
            if(cuisine.getId().equals(cuisineId)){
                return cuisine;
            }
        }
        return  null;
    }
    @Override
    public List<Cuisine> deleteCuisine(int restaurantId,String cuisine){
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        List<Cuisine> cuisines =restaurant.getCuisines();
//        Cuisine cuisine1 = new Cuisine();
    for (Cuisine cuisine1:cuisines) {
        if(cuisine1.getId()!=null){
     if(cuisine1.getId().equals(cuisine)){
         cuisines.remove(cuisine1);
         break;
     }}
    }
        restaurant.setCuisines(cuisines);
        restaurantRepo.save(restaurant);
        return cuisines;
    }

    public List<Restaurant> findRestaurantsByCuisineName(String cuisineName) {
        return restaurantRepo.findByCuisineName(cuisineName);
    }

    public List<Cuisine> getCuisine(Integer id) {
        Restaurant restaurant = restaurantRepo.findById(id).get();
        return restaurant.getCuisines();
    }


    @Override
    public Cuisine editCuisine(int id,Cuisine cuisine){
        Restaurant restaurant = restaurantRepo.findById(id).get();
        List<Cuisine> cuisines = restaurant.getCuisines();
        for (Cuisine cuisine1:cuisines) {
            if(cuisine.getId().equals(cuisine.getId())){
                if(cuisine.getName() != null){
                    cuisine1.setName(cuisine.getName());
                }
                if(cuisine.getDescription()!=null){
                    cuisine1.setDescription(cuisine.getDescription());
                }
                if(cuisine.getPrice() != 0){
                    cuisine1.setPrice(cuisine.getPrice());
                }
                if(cuisine.getType() != null){
                    cuisine1.setType(cuisine.getType());
                }
                break;
            }
        }
        restaurant.setCuisines(cuisines);
        restaurantRepo.save(restaurant);
        return cuisine;
    }


//    public List<Restaurant> findRestaurant(String name,String city){
//        List<Restaurant> restaurants = restaurantRepo.findByRestaurantNameOrCuisineNameContainingIgnoreCase(name);
//        List<Restaurant> result = new ArrayList<>();
//        for (Restaurant restaurant : restaurants) {
//            if (restaurant.getCuisines() == null) {
//                // If the cuisines field is null, add the restaurant to the result list
//                result.add(restaurant);
//            } else {
//                boolean cuisineMatch = false;
//                for (Cuisine cuisine : restaurant.getCuisines()) {
//                    if (cuisine.getName().toLowerCase().contains(name.toLowerCase())) {
//                        cuisineMatch = true;
//                        break;
//                    }
//                }
//                if (restaurant.getName().toLowerCase().contains(name.toLowerCase()) || cuisineMatch) {
//                    // If the restaurant name or one of its cuisines matches the query, add it to the result list
//                    result.add(restaurant);
//                }
//            }
//        }
//        return result;


    @Override
        public List<Restaurant> findRestaurant(String name,String city){
        List<Restaurant> restaurants = restaurantRepo.findByCity(city);
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getCuisines() == null) {
                if (restaurant.getName().toLowerCase().contains(name.toLowerCase())) {
                    result.add(restaurant);
                }
            } else {
                boolean cuisineMatch = false;
                for (Cuisine cuisine : restaurant.getCuisines()) {
                    if (cuisine.getName().toLowerCase().contains(name.toLowerCase())) {
                        cuisineMatch = true;
                        break;
                    }
                }
                if (restaurant.getName().toLowerCase().contains(name.toLowerCase()) || cuisineMatch) {
                    result.add(restaurant);
                }
            }
        }
        return result;
    }


    @Override
    public int rating(String id, int restaurantId, int rating){
        if(restaurantRepo.findById(restaurantId).get().getRatings() == null){
            Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
            restaurant.setRatings(new ArrayList<>());
            restaurantRepo.save(restaurant);
        }
        Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
        List<Rated> ratings = restaurant.getRatings();
        boolean flag = false;
        for (Rated rated : ratings) {
            if(rated.getEmail().equals(id)){
                rated.setRating(rating);
                flag = true;
                break;
            }}
            if(!flag){
                Rated rated = new Rated();
                rated.setRating(rating);
                rated.setEmail(id);
                ratings.add(rated);
            }
            restaurant.setRatings(ratings);
            List<Rated> ratings1 = restaurant.getRatings();
            int sum = 0;
        for (Rated rated:ratings1) {
            sum = sum + rated.getRating();
        }
            double r = sum/restaurant.getRatings().size();
            int rating1 = (int)Math.round(r);
            restaurant.setRating(rating1);
        restaurantRepo.save(restaurant);
        return rating1;
    }

    @Override
    public int getRating(String id, int restaurantId){
        List<Rated> ratings = restaurantRepo.findById(restaurantId).get().getRatings();
        for (Rated rated:ratings) {
            if(rated.getEmail().equals(id)){
                return rated.getRating();
            }
        }
        return 0;
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return (restaurantRepo.findById(id).get());
    }

    @Override
    public String addFeedback(int restaurantId,Feedback feedback) {
        if(feedback!=null) {
            feedback.setImage(userRepo.findById(feedback.getUser()).get().getImage());
            feedback.setDate(LocalDate.now().toString());
            feedback.setTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString());
            Restaurant restaurant = restaurantRepo.findById(restaurantId).get();
            if(restaurant.getFeedback()==null){
                List<Feedback> feedbacks=new ArrayList<>();
                restaurant.setFeedback(feedbacks);
            }
            List<Feedback> feedbacks = restaurant.getFeedback();
            feedbacks.add(feedback);
            restaurant.setFeedback(feedbacks);
            restaurantRepo.save(restaurant);
            return "Thanks for your feedback";
        }
        return null;
    }

    @Override
    public List<Feedback> getFeedbacks(int restaurantId) {
        Restaurant restaurant=restaurantRepo.findById(restaurantId).get();
        List<Feedback> feedbacks = restaurant.getFeedback();
        return feedbacks;
    }


}
