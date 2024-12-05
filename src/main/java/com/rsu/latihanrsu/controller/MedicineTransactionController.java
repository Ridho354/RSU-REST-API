package com.rsu.latihanrsu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.dto.MedicineTransactionDetailDTO;
import com.rsu.latihanrsu.dto.request.MedicineTransactionRequest;
import com.rsu.latihanrsu.dto.response.MedicineTransactionResponse;
import com.rsu.latihanrsu.entity.MedicineTransaction;
import com.rsu.latihanrsu.service.MedicineTransactionDetailService;
import com.rsu.latihanrsu.service.MedicineTransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Medicine Transaction Controller", description="CRUD transaksi Obat")
@RestController
@RequestMapping(Constant.MEDICINE_TRANSACTION_API)
public class MedicineTransactionController {
    private final MedicineTransactionService MedicineTransactionService;

    @Autowired
    private MedicineTransactionDetailService MedicineTransactionDetailService;
    public MedicineTransactionController(MedicineTransactionService MedicineTransactionService) {
        this.MedicineTransactionService = MedicineTransactionService;
    }

    @PreAuthorize("hasAnyRole('STAFF', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<MedicineTransactionResponse>> getAllMedicineTransactions() {
        List<MedicineTransactionResponse> MedicineTransactions = MedicineTransactionService.getAllMedicineTransactions();
        return ResponseEntity.ok(MedicineTransactions);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<MedicineTransactionResponse> getMedicineTransactionById(@PathVariable String id) {
    //     MedicineTransactionResponse MedicineTransaction = MedicineTransactionService.getMedicineTransactionById(id);
    //     return ResponseEntity.ok(MedicineTransaction);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineTransactionResponse> getMedicineTransactionById(@PathVariable String id) {
        MedicineTransactionResponse MedicineTransaction = MedicineTransactionService.getMedicineTransactionById(id);
        if (MedicineTransaction != null) {
            MedicineTransaction MedicineTransactionObj = new MedicineTransaction();
            MedicineTransactionObj.setId_Medicine_transaction(id);
            List<MedicineTransactionDetailDTO> details = MedicineTransactionDetailService.getMedicineTransactionDetailsByMedicineTransaction(MedicineTransactionObj)
                    .stream()
                    .map(detail -> new MedicineTransactionDetailDTO(detail.getId_Medicine_transaction_detail(), detail.getMedicine_id().getMedicine_name(), detail.getMedicine_id().getMedicine_price(), detail.getQuantity()))
                    .collect(Collectors.toList());
            MedicineTransaction.setDetails(details);
        }
        return ResponseEntity.ok(MedicineTransaction);
    }

    @PostMapping
    public ResponseEntity<MedicineTransactionResponse> createMedicineTransaction(@RequestBody MedicineTransactionRequest request) {
        MedicineTransactionResponse MedicineTransaction = MedicineTransactionService.createMedicineTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MedicineTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineTransactionResponse> updateMedicineTransaction(@PathVariable String id, @RequestBody MedicineTransactionRequest request) {
        MedicineTransactionResponse MedicineTransaction = MedicineTransactionService.updateMedicineTransaction(id, request);
        return ResponseEntity.ok(MedicineTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineTransaction(@PathVariable String id) {
        MedicineTransactionService.deleteMedicineTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<List<MedicineTransactionResponse>> getMedicineTransactionsByPatientId(@PathVariable Integer id) {
        List<MedicineTransactionResponse> MedicineTransactions = MedicineTransactionService.getMedicineTransactionsByPatientId(id);
        return ResponseEntity.status(HttpStatus.OK).body(MedicineTransactions);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<List<MedicineTransactionDetailDTO>> getMedicineTransactionDetailsByMedicineTransaction(@PathVariable String id) {
        MedicineTransaction MedicineTransaction = new MedicineTransaction();
        MedicineTransaction.setId_Medicine_transaction(id);
        // List<MedicineTransactionDetailDTO> details = MedicineTransactionDetailService.getMedicineTransactionDetailsByMedicineTransaction(MedicineTransaction);
        List<MedicineTransactionDetailDTO> details = MedicineTransactionDetailService.getMedicineTransactionDetailsByMedicineTransaction(MedicineTransaction)
                .stream()
                .map(detail -> new MedicineTransactionDetailDTO(detail.getId_Medicine_transaction_detail(), detail.getMedicine_id().getMedicine_name(), detail.getMedicine_id().getMedicine_price(), detail.getQuantity()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(details);
    }
}