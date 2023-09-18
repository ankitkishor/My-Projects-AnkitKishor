package com.niit.repository;

import com.niit.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantRepo extends MongoRepository<Restaurant,Integer> {
    List<Restaurant> findByCity(String city);
        @Query("{ 'cuisines.name' : ?0 }")
        List<Restaurant> findByCuisineName(String cuisineName);
    List<Restaurant> findByNameContainingIgnoreCaseOrCuisinesNameContainingIgnoreCase(String name);
    @Query("{ $or: [ { name: { $regex: ?0, $options: 'i' } }, { 'cuisines': { $elemMatch: { 'name': { $regex: ?0, $options: 'i' } } } } ] }")
    List<Restaurant> findByRestaurantNameOrCuisineNameContainingIgnoreCase(String name);

}
