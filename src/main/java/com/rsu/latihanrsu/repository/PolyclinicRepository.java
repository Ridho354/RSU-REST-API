// src/main/java/com/rsu/latihanrsu/repository/PolyclinicRepository.java

package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsu.latihanrsu.entity.Polyclinic;

@Repository
public interface PolyclinicRepository extends JpaRepository<Polyclinic, String> {
    
}
