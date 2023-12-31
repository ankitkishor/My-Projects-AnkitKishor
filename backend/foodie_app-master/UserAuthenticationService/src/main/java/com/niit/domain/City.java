/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue
    private int id;
    private String city;
}
