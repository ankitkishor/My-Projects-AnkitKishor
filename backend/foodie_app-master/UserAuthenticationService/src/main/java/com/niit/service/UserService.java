package com.niit.service;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;

import java.util.Map;

public interface UserService {
    public User addUser(User user);
//    public User findByUserIdAndPassword(String userId, String Password) throws UserNotFoundException;
    public Map<String,String> checkAuthentication(User user);
}
