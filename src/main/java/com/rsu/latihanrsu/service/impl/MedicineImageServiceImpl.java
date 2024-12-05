package com.rsu.latihanrsu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.entity.MedicineImage;
import com.rsu.latihanrsu.repository.MedicineImageRepository;
import com.rsu.latihanrsu.service.MedicineImageService;
import com.rsu.latihanrsu.service.StorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicineImageServiceImpl implements MedicineImageService {
    private final MedicineImageRepository menuImageRepository;
    private final StorageService storageService;

    @Override
    public List<MedicineImage> createBulkImages(List<MultipartFile> multipartFiles, Medicine medicine) {
        List<MedicineImage> menuImages = new ArrayList<>();
        multipartFiles.forEach(multipartFileItem -> {
            Map cloudinaryResp = storageService.uploadFile(multipartFileItem, "menu");
            MedicineImage medicineImage = MedicineImage.builder()
                    .publicId((String) cloudinaryResp.get("public_id"))
                    .secureUrl((String) cloudinaryResp.get("secure_url"))
                    .medicine(medicine)
                    .build();
            menuImages.add(medicineImage);
        });

        menuImageRepository.saveAllAndFlush(menuImages);

        return menuImages;
    }
}
