package com.rsu.latihanrsu.controller;  


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rsu.latihanrsu.service.StorageService;
import com.rsu.latihanrsu.util.ResponseUtil;

import java.util.Map;

import com.rsu.latihanrsu.constant.Constant;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.IMAGES_TEST_API)
public class CloudinaryImageController {
    private final StorageService storageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(value = "folder", defaultValue = "rsu") String folder) {
        Map result = storageService.uploadFile(multipartFile, folder);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully uploaded image", result);
    }

    // ini adalah solusi ketika path variable kemungkinan ada special characters seperti slash
    // alternative gunakan query param sebagai workaround
    @DeleteMapping("/**")
    public ResponseEntity<?> deleteImage(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();

        String publicId = requestURL.split(Constant.IMAGES_TEST_API)[1];

        storageService.deleteFile(publicId.substring(1));
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfuly deleted image", null);
    }
}
