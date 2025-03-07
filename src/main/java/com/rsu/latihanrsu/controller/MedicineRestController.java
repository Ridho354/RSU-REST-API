package com.rsu.latihanrsu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.dto.request.MedicineRequest;
import com.rsu.latihanrsu.dto.response.MedicineResponse;
import com.rsu.latihanrsu.service.MedicineService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="Medicine Controller", description="CRUD Obat")
@RestController
@RequestMapping(Constant.MEDICINE_API)
@RequiredArgsConstructor
public class MedicineRestController {
  
  private final MedicineService MedicineService;
  
  @PreAuthorize("hasAnyRole('STAFF', 'SUPER_ADMIN')")
  @GetMapping
  public ResponseEntity<List<MedicineResponse>> getAllMedicines() {
    List<MedicineResponse> responses = MedicineService.getAllMedicines();
    return new ResponseEntity<>(responses, HttpStatus.OK);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable String id) {
    MedicineResponse response = MedicineService.getMedicineById(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @PostMapping
  public ResponseEntity<MedicineResponse> createMedicine(@RequestBody MedicineRequest request) {
    MedicineResponse response = MedicineService.createMedicine(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<MedicineResponse> updateMedicine(@PathVariable String id, @RequestBody MedicineRequest request) {
    MedicineResponse response = MedicineService.updateMedicine(id, request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMedicine(@PathVariable String id) {
    MedicineService.deleteMedicine(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}