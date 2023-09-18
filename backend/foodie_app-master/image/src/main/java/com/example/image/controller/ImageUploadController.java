/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.example.image.controller;

import java.io.IOException;
import java.util.Optional;

import com.example.image.Repository.ImageRepository;
import com.example.image.domain.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/image")
public class ImageUploadController {
    @Autowired
    private ImageRepository imageRepository;


    @PostMapping(value = {"/upload"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long addImage(@RequestPart("image") MultipartFile file){
        try {
            ImageModel imageModel = new ImageModel(file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            ImageModel img = imageRepository.save(imageModel);
           // System.out.println(img.getId());
            return img.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/getimage/{name}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImg(@PathVariable Long name){
        return new ResponseEntity<>(imageRepository.findById(name).get().getPicByte(), HttpStatus.OK);
    }

//    @PutMapping("/update/image/{id}")
    @PutMapping( value = {"/update/image/{id}"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateImage(@RequestPart("image") MultipartFile multipartFile,@PathVariable Long id){
        ImageModel imageModel = imageRepository.findById(id).get();
        try {
            ImageModel imageModel1 = new ImageModel(multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes());
            imageModel1.setId(imageModel.getId());
            imageModel = imageModel1;
            imageRepository.save(imageModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}