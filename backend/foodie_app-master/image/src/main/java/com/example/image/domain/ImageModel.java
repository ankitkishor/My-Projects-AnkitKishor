/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.example.image.domain;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length = 500000000)
    private byte[] picByte;
    public ImageModel(){}

    public ImageModel(String name,String type,byte[] picByte){
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }
}
