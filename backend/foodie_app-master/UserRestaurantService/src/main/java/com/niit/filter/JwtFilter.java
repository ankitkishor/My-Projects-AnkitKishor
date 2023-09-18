/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String authHeader = request.getHeader("authorization");
        System.out.println(request);
        if(authHeader==null || !authHeader.startsWith("Bearer") ){
            throw new ServletException("Token missing");
        }
        else{
            String token = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("foodie").parseClaimsJws(token).getBody();
            System.out.println("claims : " + claims);
            filterChain.doFilter(request,response);
        }
    }
}

