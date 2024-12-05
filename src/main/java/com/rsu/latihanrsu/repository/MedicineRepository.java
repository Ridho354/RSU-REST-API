package com.rsu.latihanrsu.repository;

import com.rsu.latihanrsu.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {
    boolean existsById(String id);
}