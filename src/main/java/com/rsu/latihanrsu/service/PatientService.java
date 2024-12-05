package com.rsu.latihanrsu.service;

import org.springframework.data.domain.Page;

import com.rsu.latihanrsu.dto.request.PagingRequest;
import com.rsu.latihanrsu.dto.request.PatientRequest;
import com.rsu.latihanrsu.dto.response.PatientResponse;

public interface PatientService {  
    PatientResponse addPatient(PatientRequest patientRequest);
    Page<PatientResponse> getAllPatient(PagingRequest pagingRequest);
    PatientResponse getPatientById(Integer patientId);
    PatientResponse updatePatient(Integer patientId, PatientRequest patientRequest);
    void deletePatient(Integer patientId);
}
