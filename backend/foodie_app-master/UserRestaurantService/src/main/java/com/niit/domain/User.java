/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    @Id
    private String email;
    private String password;
    private String phoneNo;
    private String name;
    private String dob;
    private boolean vendor;
    private Long image;
    private List<Integer> vendorOwnedRestaurant;
    private List<Cuisine> cart;
    private List<Order> orders;
    private List<Address> addresses;
    private List<Integer> liked;
}
