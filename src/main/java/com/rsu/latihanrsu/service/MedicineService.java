package com.rsu.latihanrsu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rsu.latihanrsu.dto.request.MedicineRequest;
import com.rsu.latihanrsu.dto.response.MedicineResponse;

public interface MedicineService {
  List<MedicineResponse> getAllMedicines();
  MedicineResponse getMedicineById(String id);
  // MedicineResponse createMedicine(MedicineRequest request);
  MedicineResponse createMedicine(MedicineRequest request, List<MultipartFile> multipartFiles);
  MedicineResponse updateMedicine(String id, MedicineRequest request);
  void deleteMedicine(String id);
}
