/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image",url = "localhost:8084")
public interface ImageProxy {
    @PostMapping(value = {"/image/upload"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long addImage(@RequestPart("image") MultipartFile file);

    @PutMapping(value = {"/image/update/image/{id}"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateImage(@RequestPart("image") MultipartFile multipartFile, @PathVariable Long id);
}
