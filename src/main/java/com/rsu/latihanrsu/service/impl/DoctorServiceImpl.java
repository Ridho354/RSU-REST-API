package com.rsu.latihanrsu.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.dto.request.DoctorRequest;
import com.rsu.latihanrsu.dto.response.DoctorResponse;
import com.rsu.latihanrsu.dto.response.PolyclinicResponse;
import com.rsu.latihanrsu.entity.Doctor;
import com.rsu.latihanrsu.repository.DoctorRepository;
import com.rsu.latihanrsu.repository.PolyclinicRepository;
import com.rsu.latihanrsu.service.DoctorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PolyclinicRepository polyclinicRepository;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Override
    public DoctorResponse addDoctor(DoctorRequest request) {
        Doctor doctor = Doctor.builder()
                .doctor_name(request.getDoctorName())
                .number_str(request.getNumberStr())
                .specialist(request.getSpecialist())
                .polyclinic_name(polyclinicRepository.findById(request.getPolyclinicId()).get())
                .build();
        doctorRepository.saveAndFlush(doctor);
        return toDoctorResponse(doctor);
    }

    @Override
    public DoctorResponse getDoctorById(String id) {
        Doctor doctor = doctorRepository.findById(id).get();
        return toDoctorResponse(doctor);
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::toDoctorResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponse updateDoctor(String id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setDoctor_name(request.getDoctorName());
        doctor.setNumber_str(request.getNumberStr());
        doctor.setSpecialist(request.getSpecialist());
        doctor.setPolyclinic_name(polyclinicRepository.findById(request.getPolyclinicId()).get());
        doctorRepository.saveAndFlush(doctor);
        return toDoctorResponse(doctor);
    }

    @Override
    public void deleteDoctor(String id) {
        doctorRepository.deleteById(id);
    }

    private DoctorResponse toDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .doctorId(doctor.getDoctor_id())
                .doctorName(doctor.getDoctor_name())
                .numberStr(doctor.getNumber_str())
                .specialist(doctor.getSpecialist())
                .polyclinic(PolyclinicResponse.builder()
                        .polyclinicId(doctor.getPolyclinic_name().getPolyclinic_id())
                        .polyclinicName(doctor.getPolyclinic_name().getPolyclinic_name())
                        .build())
                .build();
    }
}