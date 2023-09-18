/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.domain;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String date;
    private String time;
    private List<Cuisine> cuisines;
    private double totalBillAmount;
}
