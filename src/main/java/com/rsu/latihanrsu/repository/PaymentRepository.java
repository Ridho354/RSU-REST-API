package com.rsu.latihanrsu.repository;

import com.rsu.latihanrsu.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// #Midtrans#
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    // Payment findByMedicineTransactionId(String orderId);
    @Query("SELECT p FROM Payment p WHERE p.medicineTransaction.id_Medicine_transaction = :id")
    Payment findByMedicineTransactionId(@Param("id") String id);
}
