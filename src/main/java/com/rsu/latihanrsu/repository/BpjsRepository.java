package com.rsu.latihanrsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rsu.latihanrsu.entity.Bpjs;

public interface BpjsRepository extends JpaRepository<Bpjs, String>{
    @Query("SELECT b FROM Bpjs b WHERE b.number_bpjs = :numberBpjs")
    Bpjs findByNumberBpjs(@Param("numberBpjs") String numberBpjs);

}
