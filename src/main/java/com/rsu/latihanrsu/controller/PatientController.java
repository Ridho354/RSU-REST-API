package com.rsu.latihanrsu.controller;

import java.util.List;

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
import com.rsu.latihanrsu.dto.request.PagingRequest;
import com.rsu.latihanrsu.dto.request.PatientRequest;
import com.rsu.latihanrsu.dto.response.CommonResponse;
import com.rsu.latihanrsu.dto.response.PatientResponse;
import com.rsu.latihanrsu.service.PatientService;
import com.rsu.latihanrsu.util.ResponseUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name="Patient Controller", description="CRUD Pasien")
@RestController
@RequestMapping(Constant.PATIENT_API)
@RequiredArgsConstructor
public class PatientController {
    @Autowired
    private final PatientService patientService;
    
    @PreAuthorize("hasAnyRole = {'PATIENT','STAFF', 'SUPER_ADMIN'}")
    @PostMapping
    public ResponseEntity<?> addPatient(@RequestBody PatientRequest request) {
        PatientResponse addNewPatient = patientService.addPatient(request);
        CommonResponse<PatientResponse> commonResponse = new CommonResponse<>(HttpStatus.CREATED.value(), "Success add patient", addNewPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }
    
    @GetMapping
    public ResponseEntity<CommonResponse<List<PatientResponse>>> getAllPatient(@RequestParam(required = false, defaultValue = "0") int page,
                                                                               @RequestParam(required = false, defaultValue = "10") int size) {
        PagingRequest pagingRequest = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<PatientResponse> allPatient = patientService.getAllPatient(pagingRequest);
        return new ResponseUtil().buildPageResponse(HttpStatus.OK, "Success get all patient", allPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<PatientResponse>> getPatientById(@PathVariable Integer id) {
        PatientResponse patient = patientService.getPatientById(id);
        return new ResponseUtil().buildResponse(HttpStatus.OK, "Success get patient", patient);
    }
    
    @PreAuthorize("hasAnyRole('PATIENT','STAFF', 'SUPER_ADMIN') or @permissionEvaluationServiceImpl.hasAccessToCustomer(#id, authentication.principal)")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<PatientResponse>> updatePatient(@PathVariable Integer id, @RequestBody PatientRequest request) {
        PatientResponse patient = patientService.updatePatient(id, request);
        System.out.println("id : " + id);
        System.out.println("request : " + request);
        System.out.println("\n\n");
        return new ResponseUtil().buildResponse(HttpStatus.OK, "Success update patient", patient);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return new ResponseUtil().buildResponse(HttpStatus.OK, "Success delete patient", null);
    }
}
