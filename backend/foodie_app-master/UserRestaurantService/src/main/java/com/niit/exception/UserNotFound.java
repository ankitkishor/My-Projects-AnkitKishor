/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT,reason = "User Not Found")
public class UserNotFound extends Exception{
}
