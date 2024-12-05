package com.rsu.latihanrsu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rsu.latihanrsu.entity.MedicineTransaction;
import com.rsu.latihanrsu.entity.MedicineTransactionDetail;

@Repository
public interface MedicineTransactionDetailRepository extends JpaRepository<MedicineTransactionDetail, String> {

    @Query("SELECT d FROM MedicineTransactionDetail d WHERE d.id_Medicine_transaction = :MedicineTransaction")
    public List<MedicineTransactionDetail> findByMedicineTransaction(@Param("MedicineTransaction") MedicineTransaction MedicineTransaction);
}
