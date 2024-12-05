package com.rsu.latihanrsu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.entity.ControlNumber;
import com.rsu.latihanrsu.service.ControlNumberService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Control Number Controller", description="CRUD untuk Surat kontrol")
@RestController
@RequestMapping(Constant.CONTROL_NUMBER_API)
public class ControlNumberController {

    @Autowired
    private ControlNumberService controlNumberService;

    @PostMapping
    public ResponseEntity<?> createControlNumber(@RequestBody ControlNumber controlNumber) {
        try {
            ControlNumber savedControlNumber = controlNumberService.addControlNumber(controlNumber);
            return new ResponseEntity<>(savedControlNumber, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ControlNumber>> getAllControlNumbers() {
        List<ControlNumber> controlNumbers = controlNumberService.getAllControlNumbers();
        return new ResponseEntity<>(controlNumbers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControlNumber> getControlNumber(@PathVariable Integer id) {
        return controlNumberService.getControlNumberById(id)
                .map(controlNumber -> ResponseEntity.ok(controlNumber))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateControlNumber(@PathVariable Integer id, @RequestBody ControlNumber controlNumber) {
        try {
            ControlNumber updatedControlNumber = controlNumberService.updateControlNumber(id, controlNumber);
            return new ResponseEntity<>(updatedControlNumber, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControlNumber(@PathVariable Integer id) {
        try {
            controlNumberService.deleteControlNumber(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/patient/{patientId}")
    // public ResponseEntity<List<ControlNumber>> getControlNumbersByPatientId(@PathVariable String patientId) {
    //     List<ControlNumber> controlNumbers = controlNumberService.getControlNumbersByPatientId(patientId);
    //     return new ResponseEntity<>(controlNumbers, HttpStatus.OK);
    // }

    // @GetMapping("/polyclinic/{polyclinicId}")
    // public ResponseEntity<List<ControlNumber>> getControlNumbersByPolyclinicId(@PathVariable String polyclinicId) {
    //     List<ControlNumber> controlNumbers = controlNumberService.getControlNumbersByPolyclinicId(polyclinicId);
    //     return new ResponseEntity<>(controlNumbers, HttpStatus.OK);
    // }
}

