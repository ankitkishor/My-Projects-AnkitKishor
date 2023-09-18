/*
 * Author:Ajay Sharma
 * Date : 10-02-2023
 *Created With : Intellij IDEA Community Edition
 */

package com.niit.service;

import com.niit.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityGeneratorTokenImpl implements SecurityGeneratorToken {
    @Override
    public Map<String, String> generateToken(User user){
        //Initialize Map
        Map<String,String> tokenMap=new HashMap<>();
        //setter method to give empty string
        user.setPassword("");
        //Initialize Map2
        Map<String, Object> userData=new HashMap<>();
        //put the value in userData Map
        userData.put("Customer UserId", user.getEmail());
        //jwt String
        String jwtTokenString= Jwts.builder()
                .setClaims(userData)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,"foodie")
                .compact();

        tokenMap.put("token",jwtTokenString);
        tokenMap.put("Message","Login SuccessFull");

        return tokenMap;
    }
}
