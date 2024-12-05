package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsu.latihanrsu.entity.MedicineTransaction;

public interface MedicineTransactionRepository extends JpaRepository<MedicineTransaction, String> {
    boolean existsById(String id);
}