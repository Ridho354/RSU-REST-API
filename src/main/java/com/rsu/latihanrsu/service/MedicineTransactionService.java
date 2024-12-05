package com.rsu.latihanrsu.service;

import java.util.List;

import com.rsu.latihanrsu.dto.request.MedicineTransactionRequest;
import com.rsu.latihanrsu.dto.request.UpdateOrderStatusRequest;
import com.rsu.latihanrsu.dto.response.MedicineTransactionResponse;
import com.rsu.latihanrsu.entity.MedicineTransaction;

public interface MedicineTransactionService {
    List<MedicineTransactionResponse> getAllMedicineTransactions();
    MedicineTransactionResponse getMedicineTransactionById(String id);
    MedicineTransactionResponse createMedicineTransaction(MedicineTransactionRequest request);
    MedicineTransactionResponse updateMedicineTransaction(String id, MedicineTransactionRequest request);
    void deleteMedicineTransaction(String id);
    List<MedicineTransactionResponse> getMedicineTransactionsByPatientId(Integer id);
    MedicineTransaction getOneMedicineTransactionById(String id);
    MedicineTransactionResponse updateOrderStatus(String orderId, UpdateOrderStatusRequest request);
    void updateOrderId(String id, String newOrderId);
}
