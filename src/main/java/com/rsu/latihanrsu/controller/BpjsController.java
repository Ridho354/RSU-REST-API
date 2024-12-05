package com.rsu.latihanrsu.controller;

import com.rsu.latihanrsu.dto.request.BpjsRequest;
import com.rsu.latihanrsu.entity.Bpjs;
import com.rsu.latihanrsu.service.BpjsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.dto.response.BpjsResponse;
import com.rsu.latihanrsu.dto.response.CommonResponse;
import com.rsu.latihanrsu.util.ResponseUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="BPJS Controller", description="CRUD BPJS")
@RestController
@RequestMapping(Constant.BPJS_API)
public class BpjsController {
    @Autowired
    private BpjsService bpjsService;
    
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping
    public List<Bpjs> getAllBpjs() {
        return bpjsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bpjs> getBpjsById(@PathVariable String id) {
        return bpjsService.findBpjsById(id)
                .map(bpjs -> ResponseEntity.ok().body(bpjs))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<CommonResponse<BpjsResponse>> createBpjs(@RequestBody BpjsRequest request) {
        BpjsResponse newBpjs = bpjsService.addBpjs(request);
        return new ResponseUtil().buildResponse(HttpStatus.OK, "Success create BPJS", newBpjs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BpjsResponse>> updateBpjs(@PathVariable String id, @RequestBody BpjsRequest request) {
        BpjsResponse updatedBpjs = bpjsService.updateBpjs(id, request);
        return new ResponseUtil().buildResponse(HttpStatus.OK, "Success update BPJS", updatedBpjs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBpjs(@PathVariable String id) {
        bpjsService.deleteBpjsById(id);
        return ResponseEntity.noContent().build();
    }
}
