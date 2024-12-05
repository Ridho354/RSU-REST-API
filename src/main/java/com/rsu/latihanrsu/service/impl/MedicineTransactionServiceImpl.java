package com.rsu.latihanrsu.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.constant.MedicinePayment;
import com.rsu.latihanrsu.dto.request.MedicineTransactionRequest;
import com.rsu.latihanrsu.dto.request.UpdateOrderStatusRequest;
import com.rsu.latihanrsu.dto.response.MedicineTransactionResponse;
import com.rsu.latihanrsu.entity.MedicineTransaction;
import com.rsu.latihanrsu.entity.MedicineTransactionDetail;
import com.rsu.latihanrsu.entity.Patient;
import com.rsu.latihanrsu.repository.MedicineTransactionDetailRepository;
import com.rsu.latihanrsu.repository.MedicineTransactionRepository;
import com.rsu.latihanrsu.repository.PatientRepository;
import com.rsu.latihanrsu.service.MedicineTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicineTransactionServiceImpl implements MedicineTransactionService {
    private final MedicineTransactionRepository MedicineTransactionRepository;
    private final PatientRepository patientRepository;
    LocalDate currentDate = LocalDate.now();
    private final MedicineTransactionDetailRepository MedicineTransactionDetailRepository;

    @Override
    public List<MedicineTransactionResponse> getAllMedicineTransactions() {
        List<MedicineTransaction> MedicineTransactions = MedicineTransactionRepository.findAll();
        return MedicineTransactions.stream()
                .map(this::toMedicineTransactionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineTransactionResponse getMedicineTransactionById(String id) {
        MedicineTransaction MedicineTransaction = MedicineTransactionRepository.findById(id).orElseThrow();
        return toMedicineTransactionResponse(MedicineTransaction);
    }

    @Override
    public MedicineTransactionResponse createMedicineTransaction(MedicineTransactionRequest request) {
        // Patient patient = patientRepository.findById(request.getIdPatient()).orElseThrow(() -> new RuntimeException("Patient tidak ditemukan"));
        MedicineTransaction medicineTransaction = MedicineTransaction.builder()
                // .id_patient(patient)
                .total_price(request.getTotalPrice())
                .transaction_date(currentDate.toString())
                .MedicinePaymentStatus(MedicinePayment.DRAFT)
                .build();

        MedicineTransactionRepository.saveAndFlush(medicineTransaction);
        return toMedicineTransactionResponse(medicineTransaction);
    }

    // @Override
    // public MedicineTransactionResponse updateMedicineTransaction(String id, MedicineTransactionRequest request) {
    //     MedicineTransaction MedicineTransaction = MedicineTransactionRepository.findById(id).orElseThrow();
    //     Patient patient = patientRepository.findById(request.getIdPatient()).orElseThrow(() -> new RuntimeException("Patient tidak ditemukan"));
    //     MedicineTransaction.setTotal_price(request.getTotalPrice());
    //     MedicineTransaction.setMedicinePaymentStatus(MedicinePayment.valueOf(request.getMedicinePaymentStatus()));
    //     return toMedicineTransactionResponse(MedicineTransactionRepository.saveAndFlush(MedicineTransaction));
    // }

    @Override
    public MedicineTransactionResponse updateMedicineTransaction(String id, MedicineTransactionRequest request) {
        MedicineTransaction MedicineTransaction = MedicineTransactionRepository.findById(id).orElseThrow();
        Patient patient = patientRepository.findById(request.getIdPatient()).orElseThrow(() -> new RuntimeException("Patient tidak ditemukan"));
        
        
        List<MedicineTransactionDetail> details = MedicineTransactionDetailRepository.findByMedicineTransaction(MedicineTransaction);
        Long totalPrice = details.stream()
                .mapToLong(detail -> detail.getMedicine_id().getMedicine_price() * detail.getQuantity())
                .sum();
        if (totalPrice != null) {
            MedicineTransaction.setTotal_price(request.getTotalPrice());
        } else{
            MedicineTransaction.setTotal_price(totalPrice);
        }
        
        MedicineTransaction.setMedicinePaymentStatus(MedicinePayment.valueOf(request.getMedicinePaymentStatus()));
        return toMedicineTransactionResponse(MedicineTransactionRepository.saveAndFlush(MedicineTransaction));
    }

    @Override
    public void deleteMedicineTransaction(String id) {
        MedicineTransactionRepository.deleteById(id);
    }

    private MedicineTransactionResponse toMedicineTransactionResponse(MedicineTransaction MedicineTransaction) {
        return MedicineTransactionResponse.builder()
                .idMedicineTransaction(MedicineTransaction.getId_Medicine_transaction())
                // .idPatient(MedicineTransaction.getId_patient().getId())
                .transactionDate(MedicineTransaction.getTransaction_date())
                .totalPrice(MedicineTransaction.getTotal_price())
                .MedicinePaymentStatus(MedicineTransaction.getMedicinePaymentStatus().toString())
                .build();
    }

    @Override
    public List<MedicineTransactionResponse> getMedicineTransactionsByPatientId(Integer id) {
            return null;
        // implementation for this method
    }
    
    public MedicineTransaction getOneMedicineTransactionById(String id) {
        return MedicineTransactionRepository.findById(id).orElseThrow();
    }

    @Override
    public MedicineTransactionResponse updateOrderStatus(String orderId, UpdateOrderStatusRequest request) {
        // Mendapatkan order dan validasi akses
        MedicineTransaction order = getOneMedicineTransactionById(orderId);

        // Validasi transisi status
        validateStatusTransition(order.getMedicinePaymentStatus(), request.getOrderStatus());

        order.setMedicinePaymentStatus(request.getOrderStatus());
        return toMedicineTransactionResponse(MedicineTransactionRepository.saveAndFlush(order));
    }

    private void validateStatusTransition(MedicinePayment currentStatus, MedicinePayment newStatus) {
        boolean isValid = false;

        switch(currentStatus) {
            case DRAFT:
                isValid = newStatus == MedicinePayment.PENDING || newStatus == MedicinePayment.CANCELLED;
                break;
            case PENDING:
                isValid = newStatus == MedicinePayment.PAID || newStatus == MedicinePayment.CANCELLED;
                break;
            case PAID:
                isValid = newStatus == MedicinePayment.COMPLETED || newStatus == MedicinePayment.CANCELLED;
                break;
            case COMPLETED:
            case CANCELLED:
                isValid = false;
                break;
        }

        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(Constant.ERROR_INVALID_STATUS_TRANSITION, currentStatus, newStatus));
        }
    }

    @Override
    public void updateOrderId(String id, String newOrderId) {
        MedicineTransaction order = getOneMedicineTransactionById(id);
        order.setId_Medicine_transaction(newOrderId);
        MedicineTransactionRepository.saveAndFlush(order);
    }
}