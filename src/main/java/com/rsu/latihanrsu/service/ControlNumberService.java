package com.rsu.latihanrsu.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.entity.ControlNumber;
import com.rsu.latihanrsu.entity.Doctor;
import com.rsu.latihanrsu.entity.Patient;
import com.rsu.latihanrsu.entity.Polyclinic;
import com.rsu.latihanrsu.repository.ControlNumberRepository;
import com.rsu.latihanrsu.repository.DoctorRepository;
import com.rsu.latihanrsu.repository.PatientRepository;
import com.rsu.latihanrsu.repository.PolyclinicRepository;

@Service
public class ControlNumberService {

    @Autowired
    private ControlNumberRepository controlNumberRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PolyclinicRepository polyclinicRepository;

    public ControlNumber addControlNumber(ControlNumber controlNumber) throws Exception {
        // Verifikasi apakah patient_id ada
        Optional<Patient> patient = patientRepository.findById(controlNumber.getPatient_id().getId());
        if (!patient.isPresent()) {
            throw new Exception("Patient ID not found.");
        }

        // Verifikasi apakah doctor_id ada
        Optional<Doctor> doctor = doctorRepository.findById(controlNumber.getDoctor_id().getDoctor_id());
        if (!doctor.isPresent()) {
            throw new Exception("Doctor ID not found.");
        }

        // Verifikasi apakah polyclinic_id ada
        Optional<Polyclinic> polyclinic = polyclinicRepository.findById(controlNumber.getPolyclinic_id().getPolyclinic_id());
        if (!polyclinic.isPresent()) {
            throw new Exception("Polyclinic ID not found.");
        }

        // Jika semua validasi lulus, simpan ControlNumber
        return controlNumberRepository.saveAndFlush(controlNumber);
    }

    public List<ControlNumber> getAllControlNumbers() {
        return controlNumberRepository.findAll();
    }

    public Optional<ControlNumber> getControlNumberById(Integer id) {
        return controlNumberRepository.findById(id);
    }

    public ControlNumber updateControlNumber(Integer id, ControlNumber controlNumberDetails) throws Exception {
        // Verifikasi apakah patient_id, doctor_id, polyclinic_id valid
        addControlNumber(controlNumberDetails);

        ControlNumber controlNumber = controlNumberRepository.findById(id).orElseThrow(() -> new Exception("Control Number not found"));
        controlNumber.setPatient_id(controlNumberDetails.getPatient_id());
        controlNumber.setDoctor_id(controlNumberDetails.getDoctor_id());
        controlNumber.setPolyclinic_id(controlNumberDetails.getPolyclinic_id());
        controlNumber.setNextControlDate(controlNumberDetails.getNextControlDate());
        controlNumber.setDiagnosisDiseases(controlNumberDetails.getDiagnosisDiseases());

        return controlNumberRepository.save(controlNumber);
    }

    public void deleteControlNumber(Integer id) throws Exception {
        ControlNumber controlNumber = controlNumberRepository.findById(id)
                .orElseThrow(() -> new Exception("Control Number not found"));
        controlNumberRepository.delete(controlNumber);
    }

    // public List<ControlNumber> getControlNumbersByPatientId(String patientId) {
    //     return controlNumberRepository.findByPatientId_PatientId(patientId);
    // }

    // public List<ControlNumber> getControlNumbersByPolyclinicId(String polyclinicId) {
    //     return controlNumberRepository.findByPolyclinic_id_Id(polyclinicId);
    // }
}


