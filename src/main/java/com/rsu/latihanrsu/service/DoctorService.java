package com.rsu.latihanrsu.service;

import java.util.List;

import com.rsu.latihanrsu.dto.request.DoctorRequest;
import com.rsu.latihanrsu.dto.response.DoctorResponse;

public interface DoctorService {
    DoctorResponse addDoctor(DoctorRequest request);
    DoctorResponse getDoctorById(String id);
    List<DoctorResponse> getAllDoctors();
    DoctorResponse updateDoctor(String id, DoctorRequest request);
    void deleteDoctor(String id);
}