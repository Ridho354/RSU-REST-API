// src/main/java/com/rsu/latihanrsu/controller/PolyclinicController.java

package com.rsu.latihanrsu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.dto.request.PolyclinicRequest;
import com.rsu.latihanrsu.dto.response.PolyclinicResponse;
import com.rsu.latihanrsu.service.PolyclinicService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Polyclinic Controller", description="CRUD Poli Rawat Jalan")
@RestController
@RequestMapping(Constant.POLYCLINIC_API)
public class PolyclinicController {

    @Autowired
    private PolyclinicService polyclinicService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<PolyclinicResponse> addPolyclinic(@RequestBody PolyclinicRequest request) {
        PolyclinicResponse response = polyclinicService.addPolyclinic(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PolyclinicResponse>> getAllPolyclinics(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PolyclinicResponse> responses = polyclinicService.getAllPolyclinics(page, size);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolyclinicResponse> getPolyclinicById(@PathVariable String id) {
        PolyclinicResponse response = polyclinicService.getPolyclinicById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolyclinicResponse> updatePolyclinic(@PathVariable String id, @RequestBody PolyclinicRequest request) {
        PolyclinicResponse response = polyclinicService.updatePolyclinic(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolyclinic(@PathVariable String id) {
        polyclinicService.deletePolyclinic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
