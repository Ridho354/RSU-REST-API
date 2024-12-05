package com.rsu.latihanrsu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rsu.latihanrsu.dto.request.MedicineRequest;
import com.rsu.latihanrsu.dto.response.MedicineResponse;
import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.entity.MedicineImage;
import com.rsu.latihanrsu.repository.MedicineRepository;
import com.rsu.latihanrsu.service.MedicineImageService;
import com.rsu.latihanrsu.service.MedicineService;
import com.rsu.latihanrsu.service.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
  
  private final MedicineRepository MedicineRepository;
  private final StorageService storageService;
  private final MedicineImageService medicineImageService;
  
  @Override
  public List<MedicineResponse> getAllMedicines() {
    return MedicineRepository.findAll().stream()
      .map(this::toMedicineResponse)
      .collect(Collectors.toList());
  }
  
  @Override
  public MedicineResponse getMedicineById(String id) {
    return MedicineRepository.findById(id)
      .map(this::toMedicineResponse)
      .orElseThrow();
  }
  
  @Override
  public MedicineResponse createMedicine(MedicineRequest request, List<MultipartFile> multipartFiles) {
    Medicine medicine = new Medicine();
    medicine.setMedicine_name(request.getName());
    medicine.setMedicine_price(request.getPrice());
    medicine.setMedicine_info(request.getInfo());
    Medicine createdMedicine = MedicineRepository.saveAndFlush(medicine);

    if (multipartFiles != null && !multipartFiles.isEmpty()) {
      List<MedicineImage> menuImageList = medicineImageService.createBulkImages(multipartFiles, createdMedicine); // disini membuat MenuImage (save ke database)
      medicine.setImages(menuImageList); // ini untuk keperluan response
    }
    return toMedicineResponse(createdMedicine);
  }
  
  @Override
  public MedicineResponse updateMedicine(String id, MedicineRequest request) {
    Medicine medicine = MedicineRepository.findById(id)
      .orElseThrow();
    medicine.setMedicine_name(request.getName());
    medicine.setMedicine_price(request.getPrice());
    medicine.setMedicine_info(request.getInfo());
    Medicine createdMedicine = MedicineRepository.saveAndFlush(medicine);

    // if (multipartFiles != null && !multipartFiles.isEmpty()) {
    //   List<MedicineImage> menuImageList = medicineImageService.createBulkImages(multipartFiles, createdMedicine); // disini membuat MenuImage (save ke database)
    //   medicine.setImages(menuImageList); // ini untuk keperluan response
    // }
    return toMedicineResponse(createdMedicine);
  }
  
  @Override
  public void deleteMedicine(String id) {
    Medicine medicine = MedicineRepository.findById(id)
      .orElseThrow();
    if (medicine.getImages() != null && !medicine.getImages().isEmpty()) {
        medicine.getImages().forEach(menuImage -> storageService.deleteFile(menuImage.getPublicId()));
    }
    MedicineRepository.deleteById(id);
  }
  
  private MedicineResponse toMedicineResponse(Medicine medicine) {
    MedicineResponse response = new MedicineResponse();
    response.setId(medicine.getMedicine_id());
    response.setName(medicine.getMedicine_name());
    response.setPrice(medicine.getMedicine_price());
    response.setInfo(medicine.getMedicine_info());
    response.setImages(medicine.getImages());
    return response;
  }
}