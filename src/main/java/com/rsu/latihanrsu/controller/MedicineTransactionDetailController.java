package com.rsu.latihanrsu.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.entity.MedicineTransactionDetail;
import com.rsu.latihanrsu.service.MedicineTransactionDetailService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Medicine Transaction Detail Controller", description="CRUD Detail Transaksi obat")    
@RestController
@RequestMapping(Constant.MEDICINE_TRANSACTION_DETAIL_API)
public class MedicineTransactionDetailController {

    @Autowired
    private MedicineTransactionDetailService MedicineTransactionDetailService;

    @PreAuthorize("hasAnyRole('STAFF', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<MedicineTransactionDetail> createMedicineTransactionDetail(@RequestBody MedicineTransactionDetail MedicineTransactionDetail) {
        try {
            MedicineTransactionDetail createdDetail = MedicineTransactionDetailService.createMedicineTransactionDetail(MedicineTransactionDetail);
            return new ResponseEntity<>(createdDetail, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<MedicineTransactionDetail>> getAllMedicineTransactionDetails() {
        List<MedicineTransactionDetail> details = MedicineTransactionDetailService.getAllMedicineTransactionDetails();
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineTransactionDetail> getMedicineTransactionDetail(@PathVariable String id) {
        Optional<MedicineTransactionDetail> detail = MedicineTransactionDetailService.getMedicineTransactionDetail(id);
        return detail.map(MedicineTransactionDetail -> new ResponseEntity<>(MedicineTransactionDetail, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineTransactionDetail> updateMedicineTransactionDetail(@PathVariable String id, @RequestBody MedicineTransactionDetail MedicineTransactionDetail) {
        Optional<MedicineTransactionDetail> existingDetail = MedicineTransactionDetailService.getMedicineTransactionDetail(id);
        if (existingDetail.isPresent()) {
            MedicineTransactionDetail.setId_Medicine_transaction_detail(id);
            return new ResponseEntity<>(MedicineTransactionDetailService.createMedicineTransactionDetail(MedicineTransactionDetail), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicineTransactionDetail(@PathVariable String id) {
        if (MedicineTransactionDetailService.getMedicineTransactionDetail(id).isPresent()) {
            MedicineTransactionDetailService.deleteMedicineTransactionDetail(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

