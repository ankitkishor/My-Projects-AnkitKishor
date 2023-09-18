/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode
public class Cuisine {
    private  String id;
    private String name;
    private String description;
    private double price;
    private String type;
    private Long image;
    private int quantity;
}
