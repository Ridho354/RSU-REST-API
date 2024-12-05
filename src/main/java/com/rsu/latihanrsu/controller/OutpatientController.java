package com.rsu.latihanrsu.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.constant.OutpatientStatus;
import com.rsu.latihanrsu.dto.request.OutpatientRegister;
import com.rsu.latihanrsu.dto.response.OutpatientResponse;
import com.rsu.latihanrsu.entity.Outpatient;
import com.rsu.latihanrsu.service.OutpatientService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="Outpatient Controller", description="CRUD Rawat jalan")
@RestController
@RequestMapping(Constant.OUTPATIENT_API)
@RequiredArgsConstructor
public class OutpatientController {
    private final OutpatientService outpatientService;

    @PostMapping("/public")
    public ResponseEntity<Outpatient> createOutpatient(@RequestBody OutpatientRegister outpatient) {
        return new ResponseEntity<>(outpatientService.addOutpatient(outpatient), HttpStatus.CREATED);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Outpatient>> getAllOutpatients() {
        return ResponseEntity.ok(outpatientService.getAllOutpatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outpatient> getOutpatientById(@PathVariable String id) {
        return ResponseEntity.ok(outpatientService.getOutpatientById(id));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<OutpatientResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(outpatientService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Outpatient> updateOutpatient(
            @PathVariable String id, @RequestBody Outpatient outpatientDetails) {
        return ResponseEntity.ok(outpatientService.updateOutpatient(id, outpatientDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutpatient(@PathVariable String id) {
        outpatientService.deleteOutpatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<OutpatientResponse>> getByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(outpatientService.getByDate(date));
    }

    // @GetMapping("/by-patient/{patientId}")
    // public ResponseEntity<List<Outpatient>> getByPatientId(@PathVariable String patientId) {
    //     return ResponseEntity.ok(outpatientService.getByPatientId(patientId));
    // }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Outpatient> updateStatus(@PathVariable String id, @RequestParam OutpatientStatus status) {
        return ResponseEntity.ok(outpatientService.updateStatus(id, status));
    }
}
