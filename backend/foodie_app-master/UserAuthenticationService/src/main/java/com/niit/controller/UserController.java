
package com.niit.controller;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;
import com.niit.service.UserService;
import com.niit.service.SecurityGeneratorTokenImpl;
import com.niit.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v2")
//@CrossOrigin("http://localhost:4200/")
public class UserController {
    UserServiceImpl userService;
    SecurityGeneratorTokenImpl securityGeneratorToken;
   @Autowired

    public UserController(UserServiceImpl userService, SecurityGeneratorTokenImpl securityGeneratorToken) {
        this.userService = userService;
        this.securityGeneratorToken = securityGeneratorToken;
    }


    @PostMapping("/user")
    public ResponseEntity<?> registerCustomer(@RequestBody User user){
        System.out.println(user);
        User user1 = userService.addUser(user);
        if(user1!=null){
            return new ResponseEntity<User>(user1, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<String>("Error Occurred",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public  ResponseEntity<?> loginCheck(@RequestBody User user)  {
        Map<String,String> map = userService.checkAuthentication(user);
        System.out.println(map);
       if(map != null){
        return new ResponseEntity<>(map,HttpStatus.OK);}
       else {
           return new ResponseEntity<>(HttpStatus.CONFLICT);}
       }
    }

