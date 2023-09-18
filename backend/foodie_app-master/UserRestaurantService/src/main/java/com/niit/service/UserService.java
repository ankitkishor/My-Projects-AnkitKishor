package com.niit.service;

import com.niit.domain.*;
import com.niit.exception.UserAlreadyExist;
import com.niit.exception.UserNotFound;

import java.util.List;

public interface UserService {
    User register(User user) throws UserAlreadyExist;
    boolean delete(String id) throws UserNotFound;
    User update(User user);
    User getUser(String id);
    void vendor(User user);
    Restaurant addRestaurant(Restaurant restaurant,String id);
    List<Cuisine> getCart(String id);
    boolean deleteCuisineFromCart(String id,String cuisineId);

    double addToCart(String id,Cuisine cuisine);
    public List<Order> getOrders(String id);
    public Order placeOrder(String id, Order order);

//    public double getTotal();

    public int setQuantity(int quantity,Cuisine cuisine,String id);
    List<Address> addAddress(Address address,String id);
    void liked(String id,int restaurantId);
    boolean getIfLiked(String id,int restaurantId);
     void removeLiked(String id,int restaurantId);
     void removeAddress(String id,String address);
    public List<Integer> getLikedRestaurant(String id);
     List<Address> editAddress(String id,Address address);
     List<Address> getAddress(String id);
     void deleteRestaurant(String id,int restaurantId);
    Restaurant editRestaurant(Restaurant restaurant);
    public List<Integer> getVendorOwnedRestaurant(String id);
    public int cartItemNumber(String id);
}
