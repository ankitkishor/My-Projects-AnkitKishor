/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.service;


import com.niit.domain.*;
import com.niit.exception.UserAlreadyExist;
import com.niit.exception.UserNotFound;
import com.niit.proxy.UserProxy;
import com.niit.repository.RestaurantIdRepo;
import com.niit.repository.RestaurantRepo;
import com.niit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserProxy userProxy;
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    RestaurantIdRepo restaurantIdRepo;

    @Override
    public User register(User user) throws UserAlreadyExist {
        if (!userRepo.findById(user.getEmail()).isPresent()) {
            UserLog userLog = new UserLog();
            userLog.setEmail(user.getEmail());
            userLog.setPassword(user.getPassword());
            user.setVendor(false);
            userProxy.addUser(userLog);
            return userRepo.save(user);
        } else {
            throw new UserAlreadyExist();
        }
    }

    @Override
    public boolean delete(String id) throws UserNotFound {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
            return true;
        } else {
            throw new UserNotFound();
        }
    }

    @Override
    public User update(User user) {
        User user1 = userRepo.findById(user.getEmail()).get();
        if (userRepo.findById(user.getEmail()).isPresent()) {
            if (user.getPassword() != null) {
                user1.setPassword(user.getPassword());
            }
            if (user.getPhoneNo() != null) {
                user1.setPhoneNo(user.getPhoneNo());
            }
            if (user.getName() != null) {
                user1.setName(user.getName());
            }
            if (user.getDob() != null) {
                user1.setDob(user.getDob());
            }
        }
        return userRepo.save(user1);
    }

    @Override
    public User getUser(String id) {
        User user = userRepo.findById(id).get();
        user.setPassword("");   
        user.setAddresses(null);
        user.setCart(null);
        user.setVendorOwnedRestaurant(null);
        return user;
    }

    @Override
    public void vendor(User user) {
       if(userRepo.findById(user.getEmail()).isPresent()){
          User user1 =  userRepo.findById(user.getEmail()).get();
           if(user1.getPassword().equals(user.getPassword())){
           if (!user1.isVendor()){
               user1.setVendor(true);
               userRepo.save(user1);
           }
           }
       }
    }

    @Override
    public Restaurant addRestaurant(Restaurant restaurant,String id) {
        RestaurantId restaurantId = restaurantIdRepo.findById(1).get();
        int id1 = restaurantIdRepo.findById(1).get().getGeneratedVal();
        restaurant.setId(id1);
        restaurant.setRating(0);
        restaurantId.setGeneratedVal(id1 + 1);
        restaurantIdRepo.save(restaurantId);

        User user = userRepo.findById(id).get();
        if (user.getVendorOwnedRestaurant() == null){
            List<Integer> vendorRestaurants = new ArrayList<>();
            user.setVendorOwnedRestaurant(vendorRestaurants);
            userRepo.save(user);
        }
        List<Integer> vendorRestaurants = user.getVendorOwnedRestaurant();
        vendorRestaurants.add(restaurantService.addRestaurant(restaurant));
        user.setVendorOwnedRestaurant(vendorRestaurants);
        userRepo.save(user);
//        restaurantRepo.save(restaurant);
       return restaurant;
    }
    @Override
    public void deleteRestaurant(String id,int restaurantId){
        User user = userRepo.findById(id).get();
        if(user.isVendor()){
            if(user.getVendorOwnedRestaurant().contains(restaurantId)){
                restaurantRepo.deleteById(restaurantId);
                List<Integer> restaurants= user.getVendorOwnedRestaurant();
                restaurants.remove(Integer.valueOf(restaurantId));
                user.setVendorOwnedRestaurant(restaurants);
                userRepo.save(user);
            }
        }
    }
    @Override
    public double addToCart(String id, Cuisine cuisine) {
        User user = userRepo.findById(id).get();
        double billAmount=0;
        if(user.getCart()==null){
            List<Cuisine> cuisines=new ArrayList<>();
            user.setCart(cuisines);
            userRepo.save(user);
        }
        List<Cuisine> cart = user.getCart();
        boolean found = false;
        for (Cuisine cuisine1 : cart) {
            if (cuisine1.getId().equals(cuisine.getId())) {
                cuisine1.setQuantity(cuisine1.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            cuisine.setQuantity(1);
            cart.add(cuisine);
        }
        user.setCart(cart);
        userRepo.save(user);
        for(Cuisine item:cart){
                billAmount=billAmount+(item.getPrice()* item.getQuantity());
            }
        return billAmount;
    }

    @Override
    public List<Cuisine> getCart(String id) {
        User user = userRepo.findById(id).get();
        return user.getCart();
    }
    @Override
    public boolean deleteCuisineFromCart(String id, String cuisineId) {
        User user = userRepo.findById(id).get();
        List<Cuisine> cuisines=user.getCart();
        boolean isDeleted = cuisines.removeIf(cuisine -> (cuisine.getId().equals(cuisineId)));
        user.setCart(cuisines);
        userRepo.save(user);
        if(isDeleted){
        return true;
    }

        return false;
    }
    @Override
    public Order placeOrder(String id, Order order) {
        order.setDate(LocalDate.now().toString());
        order.setTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString());
        User user=userRepo.findById(id).get();
        double billAmount=0;
        if(user.getOrders()==null){
            List<Order> orders=new ArrayList<>();
            user.setOrders(orders);
        }
        List<Cuisine> cuisines=order.getCuisines();
        for(Cuisine item:cuisines){
            billAmount=billAmount+(item.getPrice()* item.getQuantity());
        };
        order.setTotalBillAmount(billAmount);
        List<Order> orders=user.getOrders();
        orders.add(order);
        List<Cuisine> cuisines1 = new ArrayList<>();
        user.setCart(cuisines1);
        user.setOrders(orders);
        userRepo.save(user);
        return order;
    }

    @Override
    public List<Order> getOrders(String id) {
        User user=userRepo.findById(id).get();
        return user.getOrders();
    }

    @Override
    public int setQuantity(int quantity,Cuisine cuisine,String id){
        User user=userRepo.findById(id).get();
        List<Cuisine> cuisines=user.getCart();
        for (Cuisine item : cuisines) {
            if(item.getId().equals(cuisine.getId())){
                item.setQuantity(quantity);
            }
        }
        System.out.println(cuisines);
        user.setCart(cuisines);
        userRepo.save(user);
        return quantity;
    }

    @Override
    public List<Address> addAddress(Address address, String id) {
        User user = userRepo.findById(id).get();
        if(user.getAddresses()==null)
        {
            user.setAddresses(new ArrayList<>());
            userRepo.save(user);
        }
        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
        Random random = new Random();
        int random1 =  random.nextInt(100);
        address.setId(date+time+Integer.toString(random1));
        List<Address> addresses = user.getAddresses();
        addresses.add(address);
        user.setAddresses(addresses);
        userRepo.save(user);
        return addresses;
    }

    @Override
    public List<Address> getAddress(String id){
        return userRepo.findById(id).get().getAddresses();
    }

    @Override
    public List<Address> editAddress(String id,Address address){
        List<Address> addresses = userRepo.findById(id).get().getAddresses();
        for (Address address1:addresses){
            if(address.getId().equals(address1.getId())){
                address1 = address;
                break;
            }
        }
        User user = userRepo.findById(id).get();
        user.setAddresses(addresses);
        userRepo.save(user);
        return addresses;
    }


    @Override
    public void removeAddress(String id,String address){
        User user = userRepo.findById(id).get();
        List<Address> addresses = user.getAddresses();
        for (Address address1:addresses) {
            if(address1.getId().equals(address));
            {
                addresses.remove(address1);
                break;
            }
        }
        user.setAddresses(addresses);
        userRepo.save(user);
    }

    @Override
    public void liked(String id,int restaurantId){
        User user = userRepo.findById(id).get();
        if(user.getLiked()==null){
            user.setLiked(new ArrayList<>());
            userRepo.save(user);
        }
        List<Integer> liked = user.getLiked();
        if(!liked.contains(restaurantId)){
            liked.add(restaurantId);
            user.setLiked(liked);
            userRepo.save(user);
        }
    }
    @Override
    public boolean getIfLiked(String id,int restaurantId){
        User user = userRepo.findById(id).get();
        if(user.getLiked().contains(restaurantId)){
            return true;
        }
        return false;
    }
    @Override
    public void removeLiked(String id,int restaurantId){
        User user = userRepo.findById(id).get();
        if(user.getLiked().contains(restaurantId)){
            List<Integer> liked = user.getLiked();
            liked.remove(Integer.valueOf(restaurantId));
            user.setLiked(liked);
            userRepo.save(user);
        }
    }
    @Override
    public List<Integer> getLikedRestaurant(String id){
        return userRepo.findById(id).get().getLiked();
    }

    @Override
    public Restaurant editRestaurant(Restaurant restaurant){
        Restaurant restaurant1 = restaurantRepo.findById(restaurant.getId()).get();
        if(restaurant.getName()!=null){restaurant1.setName(restaurant.getName());}
        if(restaurant.getDescription()!=null){restaurant1.setDescription(restaurant.getDescription());}
        if(restaurant.getCity()!=null){restaurant1.setCity(restaurant.getCity());}
        restaurantRepo.save(restaurant1);
        return restaurant;
    }


    @Override
    public List<Integer> getVendorOwnedRestaurant(String id){
        return userRepo.findById(id).get().getVendorOwnedRestaurant();
    }

    @Override
    public int cartItemNumber(String id){
        return userRepo.findById(id).get().getCart().size();
    }
}

