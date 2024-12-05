package com.rsu.latihanrsu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.entity.MedicineTransaction;
import com.rsu.latihanrsu.entity.MedicineTransactionDetail;
import com.rsu.latihanrsu.repository.MedicineRepository;
import com.rsu.latihanrsu.repository.MedicineTransactionDetailRepository;
import com.rsu.latihanrsu.repository.MedicineTransactionRepository;

@Service
public class MedicineTransactionDetailService {

    @Autowired
    private MedicineTransactionDetailRepository MedicineTransactionDetailRepository;

    @Autowired
    private MedicineTransactionRepository MedicineTransactionRepository;

    @Autowired
    private MedicineRepository MedicineRepository;

    // Create MedicineTransactionDetail with validation
    @PreAuthorize("hasRole('STAFF', 'SUPER_ADMIN')")
    @Transactional
    public MedicineTransactionDetail createMedicineTransactionDetail(MedicineTransactionDetail MedicineTransactionDetail) {
        // Check if the Medicine transaction and Medicine exist
        Optional<MedicineTransaction> MedicineTransactionOpt = MedicineTransactionRepository.findById(MedicineTransactionDetail.getId_Medicine_transaction().getId_Medicine_transaction());
        Optional<Medicine> MedicineOpt = MedicineRepository.findById(MedicineTransactionDetail.getMedicine_id().getMedicine_id());
        if (MedicineTransactionOpt.isEmpty()) {
            throw new IllegalArgumentException("MedicineTransaction with id " + MedicineTransactionDetail.getId_Medicine_transaction().getId_Medicine_transaction() + " does not exist.");
        }

        if (MedicineOpt.isEmpty()) {
            throw new IllegalArgumentException("Medicine with id " + MedicineTransactionDetail.getMedicine_id().getMedicine_id() + " does not exist.");
        }
        return MedicineTransactionDetailRepository.saveAndFlush(MedicineTransactionDetail);
    }

    // Get all MedicineTransactionDetails
    public List<MedicineTransactionDetail> getAllMedicineTransactionDetails() {
        return MedicineTransactionDetailRepository.findAll();
    }

    // Get MedicineTransactionDetail by ID
    public Optional<MedicineTransactionDetail> getMedicineTransactionDetail(String id) {
        return MedicineTransactionDetailRepository.findById(id);
    }

    // Delete MedicineTransactionDetail by ID
    @Transactional
    public void deleteMedicineTransactionDetail(String id) {
        MedicineTransactionDetailRepository.deleteById(id);
    }

    public List<MedicineTransactionDetail> getMedicineTransactionDetailsByMedicineTransaction(MedicineTransaction MedicineTransaction) {
        return MedicineTransactionDetailRepository.findByMedicineTransaction(MedicineTransaction);
    }
    
}
