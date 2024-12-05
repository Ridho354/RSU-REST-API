package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsu.latihanrsu.entity.ControlNumber;

@Repository
public interface ControlNumberRepository extends JpaRepository<ControlNumber, Integer> {
    // List<ControlNumber> findByPatientId_PatientId(String patientId);
    // List<ControlNumber> findByPolyclinic_id_Id(String polyclinicId); 
    // List<ControlNumber> findByPatient_id_Patient_id(String patientId);
}
