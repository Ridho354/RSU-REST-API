package com.rsu.latihanrsu.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.dto.request.PagingRequest;
import com.rsu.latihanrsu.dto.request.PatientRequest;
import com.rsu.latihanrsu.dto.response.PatientResponse;
import com.rsu.latihanrsu.entity.Patient;
import com.rsu.latihanrsu.repository.PatientRepository;
import com.rsu.latihanrsu.service.PatientService;
import com.rsu.latihanrsu.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private  final UserService userService;

    @Override
    public PatientResponse addPatient(PatientRequest patientRequest) {
        Patient patient = Patient.builder()
                .patient_name(patientRequest.getPatient_name())
                .patient_status(patientRequest.getPatient_status())
                .number_bpjs(patientRequest.getNumber_bpjs())
                .patient_address(patientRequest.getPatient_address())
                .birth_date(patientRequest.getBirth_date())
                .gender(patientRequest.getGender())
                .phone_number(patientRequest.getPhone_number())
                .disases_diagnosis(patientRequest.getDisases_diagnosis())
                .userAccount(patientRequest.getUserAccount())
                .build();
        patientRepository.saveAndFlush(patient);
        return toPatientResponse(patient);
    }

    // @PreAuthorize("hasAnyRole('PATIENT','STAFF', 'SUPER_ADMIN')")
    @Override
    public Page<PatientResponse> getAllPatient(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize());
        Page<Patient> allPatient = patientRepository.findAll(pageable);
        return allPatient.map(patient -> toPatientResponse(patient));
    }

    @Override
    public PatientResponse getPatientById(Integer patientId) {
        Patient patient = patientRepository.findById(patientId).get();
        return toPatientResponse(patient);
    }

    @Override
    public PatientResponse updatePatient(Integer patientId, PatientRequest patientRequest) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            if(!patient.getUserAccount().getId().equals(this.userService.getByContext().getId())) {
                throw new RuntimeException("You don't have permission to update this patient");
            }
            System.out.println("ok");
            patient.setPatient_name(patientRequest.getPatient_name());
            patient.setPatient_status(patientRequest.getPatient_status());
            patient.setNumber_bpjs(patientRequest.getNumber_bpjs());
            patient.setPatient_address(patientRequest.getPatient_address());
            patient.setBirth_date(patientRequest.getBirth_date());
            patient.setGender(patientRequest.getGender());
            patient.setPhone_number(patientRequest.getPhone_number());
            patient.setDisases_diagnosis(patientRequest.getDisases_diagnosis());
            patientRepository.saveAndFlush(patient);
            return toPatientResponse(patient);
        } else {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
    }

    @Override
    public void deletePatient(Integer patientId) {
        patientRepository.deleteById(patientId);
    }

    public PatientResponse toPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setPatient_id(patient.getId());
        patientResponse.setPatient_name(patient.getPatient_name());
        patientResponse.setPatient_status(patient.getPatient_status());
        patientResponse.setNumber_bpjs(patient.getNumber_bpjs());
        patientResponse.setPatient_address(patient.getPatient_address());
        patientResponse.setBirth_date(patient.getBirth_date());
        patientResponse.setGender(patient.getGender());
        patientResponse.setPhone_number(patient.getPhone_number());
        patientResponse.setDisases_diagnosis(patient.getDisases_diagnosis());
        patientResponse.setUserAccountId(patient.getUserAccount().getId());
        return patientResponse;
    }
}
