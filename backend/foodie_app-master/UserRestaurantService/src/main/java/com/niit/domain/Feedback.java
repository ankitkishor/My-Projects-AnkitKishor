/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.domain;

import lombok.Data;

@Data
public class Feedback {
        private String date;
        private String time;
        private String feedback;
        private String user;
        private Long image;
    }

