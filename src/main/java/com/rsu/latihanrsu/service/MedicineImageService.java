package com.rsu.latihanrsu.service;

import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.entity.MedicineImage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MedicineImageService {
    List<MedicineImage> createBulkImages(List<MultipartFile> multipartFiles, Medicine medicine);
}
