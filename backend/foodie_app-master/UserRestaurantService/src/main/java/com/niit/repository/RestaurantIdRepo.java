/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.repository;

import com.niit.domain.RestaurantId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantIdRepo extends MongoRepository<RestaurantId,Integer> {
}
