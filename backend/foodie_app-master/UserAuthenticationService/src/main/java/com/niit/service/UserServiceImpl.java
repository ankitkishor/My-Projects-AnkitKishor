/*
 * Author:Ajay Sharma
 * Date : 10-02-2023
 *Created With : Intellij IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;
import com.niit.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    SecurityGeneratorTokenImpl securityGeneratorToken;

    @Override
    public User addUser(User user){
        return userRepo.save(user);
    }

    @Override
    public Map<String,String> checkAuthentication(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            if (userRepo.findByEmail(user.getEmail()).get().getPassword().equals(user.getPassword())) {
                return securityGeneratorToken.generateToken(user);
            }
        }
        return null;
    }


//    @Override
//    public User findByUserIdAndPassword(String userId, String Password) throws UserNotFoundException {
//        User user = customerRepo.findByUserIdAndPassword(userId,Password);
//        return user;
////  if(user.getUserId().equals(userId)&&user.getPassword().equals(Password)){
////
////      return user;
////    }
////      throw new UserNotFoundException();
////    }
//}
}
