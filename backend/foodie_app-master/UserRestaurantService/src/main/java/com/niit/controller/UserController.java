/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.controller;


import com.niit.domain.*;
import com.niit.exception.UserAlreadyExist;
import com.niit.exception.UserNotFound;
import com.niit.proxy.ImageProxy;
import com.niit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ImageProxy imageProxy;

    /**
     * done
     * @param user
     * @param file
     * @return
     * @throws UserAlreadyExist
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart("user") User user
            ,@RequestPart("image") MultipartFile file
                                         ) throws UserAlreadyExist {
        try{
            user.setImage(imageProxy.addImage(file));
            return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
        }
        catch(UserAlreadyExist exception){
            throw new UserAlreadyExist();
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws UserNotFound {
        try{
            return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        }
        catch(UserNotFound exception){
            throw new UserNotFound();
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try{
            return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Un successful", HttpStatus.OK);
        }
    }

    /**
     * done
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }

    /**
     * done
     * @param user
     * @return
     */
    @PostMapping("/user/vendor")
    public ResponseEntity<?> vendor(@RequestBody User user){
        userService.vendor(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * done
     * @param restaurant
     * @param file
     * @param id
     * @return
     */
    @PostMapping("/user/restaurant/{id}")
    public ResponseEntity<?> restaurant(@RequestPart("restaurant") Restaurant restaurant,
                                    @RequestPart("image") MultipartFile file
    ,@PathVariable String id){
        restaurant.setImage(imageProxy.addImage(file));
        return new ResponseEntity<>(userService.addRestaurant(restaurant,id),HttpStatus.OK);
    }

    /**
     * done
     * @param id
     * @param cuisine
     * @return
     */
    @PostMapping("/user/cart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable String id,@RequestBody Cuisine cuisine){
        return new ResponseEntity<>(userService.addToCart(id,cuisine), HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/user/cartList/{id}")
    public ResponseEntity<?> getCartList(@PathVariable String id){
        return new ResponseEntity<>(userService.getCart(id),HttpStatus.OK);
    }

    /**
     * done
     * @param id
     * @param cuisine
     * @return
     */
    @PostMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteCuisineFromCart(@PathVariable String id,@RequestBody Cuisine cuisine){
        System.out.println(id);
        return new ResponseEntity<>(userService.deleteCuisineFromCart(id,cuisine.getId()), HttpStatus.CREATED);
    }

    /**
     * done
     * @param id
     * @param order
     * @return
     */
    @PostMapping("/user/{id}/order")
    public ResponseEntity<?> placeOrder(@PathVariable String id, @RequestBody Order order){
        return new ResponseEntity<>(userService.placeOrder(id,order),HttpStatus.OK);
    }

    /**
     *done
     * @param id
     * @return
     */
    @GetMapping("/user/allOrders/{id}")
    public ResponseEntity<?> getOrdersList(@PathVariable String id){
        return new ResponseEntity<>(userService.getOrders(id),HttpStatus.OK);
    }

    /**
     * done
     * @param quantity
     * @param cuisine
     * @param id
     * @return
     */
    @PostMapping("/user/setQuantity/{id}/{quantity}")
    public ResponseEntity<?> Quantity(@PathVariable int quantity,@RequestBody Cuisine cuisine,@PathVariable String id){
        return new ResponseEntity<>(userService.setQuantity(quantity,cuisine,id),HttpStatus.OK);
    }


//    @GetMapping("/get/addedToFavourites")

    @PutMapping("/image/update/image/{id}")
    public ResponseEntity<?> updateImg(@RequestPart("image") MultipartFile multipartFile,@PathVariable Long id){
        imageProxy.updateImage(multipartFile,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/address/{id}")
    public ResponseEntity<?> addAddress(@RequestBody Address address,@PathVariable String id){
        return new ResponseEntity<>(userService.addAddress(address,id),HttpStatus.OK);
    }

    @GetMapping("/user/getAddress/{id}")
    public ResponseEntity<?> getAddress(@PathVariable String id){
        return new ResponseEntity<>(userService.getAddress(id),HttpStatus.OK);
    }
    @PutMapping("/user/editAddress/{id}")
    public ResponseEntity<?> editAddress(@RequestBody Address address,@PathVariable String id){
        return new ResponseEntity<>(userService.editAddress(id,address),HttpStatus.OK);
    }
    @DeleteMapping("/user/delete/address/{id}/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable String id,@PathVariable String addressId){
        userService.removeAddress(id,addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * done
     * @param restaurant
     * @param id
     * @return
     */
    @PostMapping("/user/favorite/{id}")
    public ResponseEntity<?> addFavorite(@RequestBody int restaurant,@PathVariable String id){
        userService.liked(id,restaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/user/getFavorites/{id}/{restaurantId}")
    public ResponseEntity<?> getIfLiked(@PathVariable String id,@PathVariable int restaurantId){
        return new ResponseEntity<>(userService.getIfLiked(id,restaurantId),HttpStatus.OK);
    }

    /**
     * done
     * @param id
     * @param restaurantId
     * @return
     */
    @DeleteMapping("/user/removeFavorite/{id}/{restaurantId}")
    public ResponseEntity<?> removeLiked(@PathVariable String id,@PathVariable int restaurantId){
        userService.removeLiked(id,restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//notadded?
    @DeleteMapping("/user/deleteRestaurant/{id}/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantByID(@PathVariable String id,@PathVariable int restaurantId){
        userService.deleteRestaurant(id,restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/updateRestaurant")
    public ResponseEntity<?> updateRestaurant(@RequestBody Restaurant restaurant){
        userService.editRestaurant(restaurant);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/user/get/vendor/restaurant/{id}")
    public ResponseEntity<?> getVendorOwnedRestaurant (@PathVariable String id){
        return new ResponseEntity<>(userService.getVendorOwnedRestaurant(id),HttpStatus.OK);
    }

    @GetMapping("/user/cartItems/{id}")
    public ResponseEntity<?> cartItemNumber(@PathVariable String id){
        return new ResponseEntity<>(userService.cartItemNumber(id),HttpStatus.OK);
    }


    @GetMapping("/user/getLiked/restaurant/{id}")
    public ResponseEntity<?> getLikedRestaurant(@PathVariable String id){
        return new ResponseEntity<>(userService.getLikedRestaurant(id),HttpStatus.OK);
    }
}
