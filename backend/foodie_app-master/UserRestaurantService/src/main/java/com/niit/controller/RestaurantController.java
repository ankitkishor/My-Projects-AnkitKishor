/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.controller;

import com.niit.domain.Cuisine;
import com.niit.domain.Feedback;
import com.niit.domain.Restaurant;
import com.niit.proxy.ImageProxy;
import com.niit.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    ImageProxy imageProxy;


//    @PostMapping(value = {"/test"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public  ResponseEntity<?> addImg(@RequestPart("image") MultipartFile file){
//        System.out.println(imageProxy.addImage(file));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant){
        return new ResponseEntity<>(restaurantService.addRestaurant(restaurant), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getRestaurants(){
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/byCity/{city}")
    public ResponseEntity<?> getRestaurantByCity(@PathVariable String city){
        return new ResponseEntity<>(restaurantService.getByCity(city), HttpStatus.OK);
    }
    @PostMapping("/cuisine/{id}/{restaurant}")
    public ResponseEntity<?> addCuisine(@PathVariable String id,
                                        @PathVariable int restaurant,
                                        @RequestPart("cuisine") Cuisine cuisine,
                                        @RequestPart("image") MultipartFile file){
        cuisine.setImage(imageProxy.addImage(file));
        return new ResponseEntity<>(restaurantService.addCuisine(cuisine,id,restaurant),HttpStatus.OK);
    }


    @GetMapping("/{name}")
    public  ResponseEntity<?> getRestaurantByCuisine(@PathVariable String name){
        return new ResponseEntity<>(restaurantService.findRestaurantsByCuisineName(name),HttpStatus.OK);
    }

    @GetMapping("cuisine/{id}")
    public  ResponseEntity<?> getCuisine(@PathVariable Integer id){
        return new ResponseEntity<>(restaurantService.getCuisine(id),HttpStatus.OK);
    }
    @GetMapping("getCuisine/{id}/{cuisineId}")
    public  ResponseEntity<?> getCuisineById(@PathVariable Integer id,@PathVariable String cuisineId){
        return new ResponseEntity<>(restaurantService.getCuisineById(id,cuisineId),HttpStatus.OK);
    }

    @GetMapping("/find/{name}/{city}")
    public ResponseEntity<?> findRestaurant(@PathVariable String name,@PathVariable String city){
        return new ResponseEntity<>(restaurantService.findRestaurant(name,city),HttpStatus.OK);
    }
    @GetMapping("/getRestaurant/{id}")
    public  ResponseEntity<?> getRestaurant(@PathVariable Integer id){
        return new ResponseEntity<>(restaurantService.getRestaurantById(id),HttpStatus.OK);
    }


    @PostMapping("/feedback/{restaurantId}")
    public  ResponseEntity<?> addFeedback(@PathVariable int restaurantId,@RequestBody Feedback feedback){
        restaurantService.addFeedback(restaurantId,feedback);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/feedbacks/{id}")
    public  ResponseEntity<?> getFeedbacks(@PathVariable int id){
        return new ResponseEntity<>(restaurantService.getFeedbacks(id),HttpStatus.OK);
    }


    @GetMapping("/getRating/{id}/{restaurantId}")
    public ResponseEntity<?> getRatings(@PathVariable String id,@PathVariable int restaurantId){
        return new ResponseEntity<>(restaurantService.getRating(id,restaurantId),HttpStatus.OK);
    }


    @PostMapping("/addRating/{id}/{restaurantId}")
    public ResponseEntity<?> rating(@PathVariable String id, @PathVariable int restaurantId,@RequestBody int rating){
        return new ResponseEntity<>(restaurantService.rating(id, restaurantId, rating),HttpStatus.OK);
    }
//not added?
    @DeleteMapping("/deleteCuisine/{id}/{cuisineId}")
    public ResponseEntity<?> deleteCuisineById(@PathVariable int id, @PathVariable String cuisineId){
        return new ResponseEntity<>(restaurantService.deleteCuisine(id,cuisineId ),HttpStatus.OK);
    }

    @PutMapping("/updateCuisine/{id}")
    public ResponseEntity<?> updateCuisine(@PathVariable int id, @RequestBody Cuisine  cuisine){
        return new ResponseEntity<>(restaurantService.editCuisine(id,cuisine ),HttpStatus.OK);
    }
}
