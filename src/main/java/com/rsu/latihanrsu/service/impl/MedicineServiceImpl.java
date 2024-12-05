package com.rsu.latihanrsu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.dto.request.MedicineRequest;
import com.rsu.latihanrsu.dto.response.MedicineResponse;
import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.repository.MedicineRepository;
import com.rsu.latihanrsu.service.MedicineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
  
  private final MedicineRepository MedicineRepository;
  
  @PreAuthorize("hasRole('STAFF','SUPER_ADMIN')")
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
  public MedicineResponse createMedicine(MedicineRequest request) {
    Medicine Medicine = new Medicine();
    Medicine.setMedicine_name(request.getName());
    Medicine.setMedicine_price(request.getPrice());
    Medicine.setMedicine_info(request.getInfo());
    return toMedicineResponse(MedicineRepository.saveAndFlush(Medicine));
  }
  
  @Override
  public MedicineResponse updateMedicine(String id, MedicineRequest request) {
    Medicine Medicine = MedicineRepository.findById(id)
      .orElseThrow();
    Medicine.setMedicine_name(request.getName());
    Medicine.setMedicine_price(request.getPrice());
    Medicine.setMedicine_info(request.getInfo());
    return toMedicineResponse(MedicineRepository.save(Medicine));
  }
  
  @Override
  public void deleteMedicine(String id) {
    MedicineRepository.deleteById(id);
  }
  
  private MedicineResponse toMedicineResponse(Medicine Medicine) {
    MedicineResponse response = new MedicineResponse();
    response.setId(Medicine.getMedicine_id());
    response.setName(Medicine.getMedicine_name());
    response.setPrice(Medicine.getMedicine_price());
    response.setInfo(Medicine.getMedicine_info());
    return response;
  }
}