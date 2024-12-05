package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.rsu.latihanrsu.entity.MedicineImage;

public interface MedicineImageRepository extends JpaRepository<MedicineImage, String> {
    @Query("SELECT mi FROM MedicineImage mi WHERE mi.medicine.medicine_id = :medicine_id")
    List<MedicineImage> findByMedicine_id(@Param("medicine_id") String medicine_id);
    // List<MedicineImage> findByMedicine_medicine_id(String medicine_id);
}
