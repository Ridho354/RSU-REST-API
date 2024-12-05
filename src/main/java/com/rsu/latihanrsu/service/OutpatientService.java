package com.rsu.latihanrsu.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.constant.OutpatientStatus;
import com.rsu.latihanrsu.constant.UserRole;
import com.rsu.latihanrsu.dto.request.OutpatientRegister;
import com.rsu.latihanrsu.dto.response.OutpatientResponse;
import com.rsu.latihanrsu.entity.Outpatient;
import com.rsu.latihanrsu.entity.UserAccount;
import com.rsu.latihanrsu.repository.ControlNumberRepository;
import com.rsu.latihanrsu.repository.MedicineTransactionRepository;
import com.rsu.latihanrsu.repository.OutpatientRepository;
import com.rsu.latihanrsu.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutpatientService {
    @Autowired
    private final OutpatientRepository outpatientRepository;
    private final PatientRepository patientRepository;
    private final ControlNumberRepository controlNumberRepository;
    private final MedicineTransactionRepository MedicineTransactionRepository;
    private final UserService userService;
    
    public boolean isCustomerAllowedToAccess(String customerUserId) {
        // #SPRING SECURITY# 11
        UserAccount currentUser = userService.getByContext();

        // #SPRING SECURITY# 11 validasi tambahan di level service
        if (currentUser.getRole() == UserRole.ROLE_SUPER_ADMIN) {
            return true;
        }
        if (currentUser.getRole() == UserRole.ROLE_STAFF) {
            return true;
        }

        // #SPRING SECURITY# 11 validasi tambahan di level service
        if (currentUser.getRole() == UserRole.ROLE_PATIENT) {
            return customerUserId.equals(currentUser.getId());
        }

        return false;
    }

    public Outpatient addOutpatient(OutpatientRegister outpatientRegister) {
        // Validate foreign keys
        if (!patientRepository.existsById(outpatientRegister.getPatientId()))
            throw new IllegalArgumentException("Invalid patient ID");
        if (outpatientRegister.getIdControlNumber() != null && !controlNumberRepository.existsById(outpatientRegister.getIdControlNumber()))
            throw new IllegalArgumentException("Invalid control number ID");

        // Generate queue number based on dateControl
        // LocalDate dateControl = LocalDate.now();
        Outpatient outpatient = new Outpatient();
        // // Date dateControl = new Date(LocalDate.now());
        int queueNumber = getQueueNumberForDate(outpatientRegister.getDateControl());
        outpatient.setDateControl(outpatientRegister.getDateControl());
        outpatient.setQueueNumber(queueNumber);
        outpatient.setStatus(OutpatientStatus.REGISTER);

        return outpatientRepository.saveAndFlush(outpatient);
    }

    private int getQueueNumberForDate(LocalDate dateControl) {
        List<Outpatient> outpatients = outpatientRepository.findByDateControl(dateControl);
        int maxQueueNumber = 0;
        for (Outpatient outpatient : outpatients) {
            if (outpatient.getQueueNumber() > maxQueueNumber) {
                maxQueueNumber = outpatient.getQueueNumber();
            }
        }
        return maxQueueNumber + 1;
    }

    public List<Outpatient> getAllOutpatients() {
        return outpatientRepository.findAll();
    }

    public Outpatient getOutpatientById(String id) {
        return outpatientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Outpatient not found"));
    }

    public Outpatient updateOutpatient(String id, Outpatient outpatientDetails) {
        Outpatient outpatient = getOutpatientById(id);
        if (!patientRepository.existsById(outpatientDetails.getPatient_id().getId()))
            throw new IllegalArgumentException("Invalid patient ID");
        if (outpatientDetails.getIdControlNumber() != null && !controlNumberRepository.existsById(outpatientDetails.getIdControlNumber().getIdControlNumber()))
            throw new IllegalArgumentException("Invalid control number ID");
        if (outpatientDetails.getId_Medicine_transaction() != null && !MedicineTransactionRepository.existsById(outpatientDetails.getId_Medicine_transaction().getId_Medicine_transaction()))
            throw new IllegalArgumentException("Invalid Medicine transaction ID");

        outpatient.setDateControl(outpatientDetails.getDateControl());
        outpatient.setPatient_id(outpatientDetails.getPatient_id());
        outpatient.setIdControlNumber(outpatientDetails.getIdControlNumber());
        outpatient.setId_Medicine_transaction(outpatientDetails.getId_Medicine_transaction());
        outpatient.setStatus(outpatientDetails.getStatus());

        return outpatientRepository.saveAndFlush(outpatient);
    }

    public void deleteOutpatient(String id) {
        if (!outpatientRepository.existsById(id))
            throw new NoSuchElementException("Outpatient not found");
        outpatientRepository.deleteById(id);
    }

    public OutpatientResponse getById(String id) {
        Outpatient outpatient = outpatientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Outpatient not found"));

        OutpatientResponse response = new OutpatientResponse();
        response.setQueueNumber(outpatient.getQueueNumber());
        response.setStatus(outpatient.getStatus());
        response.setPatientId(outpatient.getPatient_id().getId());
        response.setPatientName(outpatient.getPatient_id().getPatient_name());
        response.setPatientStatus(outpatient.getPatient_id().getPatient_status());
        response.setNumberBpjs(outpatient.getPatient_id().getNumber_bpjs());
        response.setDateControl(outpatient.getDateControl());
        response.setIdControlNumber(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getIdControlNumber() : null);
        response.setPolyclinicName(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getPolyclinic_id().getPolyclinic_name(): null);
        response.setDiagnosisDiseases(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getDiagnosisDiseases() : null);
        response.setIdMedicineTransaction(outpatient.getId_Medicine_transaction() != null ? outpatient.getId_Medicine_transaction().getId_Medicine_transaction() : null);
        
        return response;
    }

    public List<OutpatientResponse> getByDate(LocalDate date) {
        List<Outpatient> outpatients = outpatientRepository.findByDateControl(date);

        List<OutpatientResponse> responses = new ArrayList<>();
        for (Outpatient outpatient : outpatients) {
            OutpatientResponse response = new OutpatientResponse();
            response.setQueueNumber(outpatient.getQueueNumber());
            response.setStatus(outpatient.getStatus());
            response.setPatientId(outpatient.getPatient_id().getId());
            response.setPatientName(outpatient.getPatient_id().getPatient_name());
            response.setPatientStatus(outpatient.getPatient_id().getPatient_status());
            response.setDateControl(outpatient.getDateControl());
            response.setIdControlNumber(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getIdControlNumber() : null);
            response.setPolyclinicName(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getPolyclinic_id().getPolyclinic_name() : null);
            response.setDiagnosisDiseases(outpatient.getIdControlNumber() != null ? outpatient.getIdControlNumber().getDiagnosisDiseases() : null);
            response.setIdMedicineTransaction(outpatient.getId_Medicine_transaction() != null ? outpatient.getId_Medicine_transaction().getId_Medicine_transaction() : null);

            responses.add(response);
        }

        return responses;
    }

    public Outpatient updateStatus(String outpatientId, OutpatientStatus status) {
        Outpatient outpatient = getOutpatientById(outpatientId);
        outpatient.setStatus(status);
        return outpatientRepository.saveAndFlush(outpatient);
    }

}

