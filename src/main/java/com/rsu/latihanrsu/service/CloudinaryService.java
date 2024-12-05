package com.rsu.latihanrsu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map upload(MultipartFile file, String folderName);
    void delete(String publicId);
}
