package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsu.latihanrsu.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
}