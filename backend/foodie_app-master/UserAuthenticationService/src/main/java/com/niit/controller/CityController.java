/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.controller;

import com.niit.domain.City;
import com.niit.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityRepo cityRepo;
    @GetMapping("/get")
    public ResponseEntity<?> getCities()
    {
        List<String> cities = new ArrayList<>();
        List<City> cities1 = cityRepo.findAll();
        for (City city:
             cities1) {
            cities.add(city.getCity());
        }
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
