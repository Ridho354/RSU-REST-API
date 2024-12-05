package com.rsu.latihanrsu.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsu.latihanrsu.entity.Outpatient;

@Repository
public interface OutpatientRepository extends JpaRepository<Outpatient, String> {
    List<Outpatient> findByDateControl(LocalDate date);
    // List<Outpatient> findByPatient_id(String patientId);
}
