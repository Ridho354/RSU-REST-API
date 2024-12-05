package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rsu.latihanrsu.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient>{
        // @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Patient p WHERE p.id = :id AND p.userAccount = :userAccount")
        // boolean existsByPatient_idAndUserAccount(@Param("id") Integer id, @Param("userAccount") UserAccount userAccount);
        boolean existsByIdAndUserAccount_id(Integer id, String userAccount_id);
}
