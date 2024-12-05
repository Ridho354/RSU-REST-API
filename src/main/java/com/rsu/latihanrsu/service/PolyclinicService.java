// src/main/java/com/rsu/latihanrsu/service/PolyclinicService.java

package com.rsu.latihanrsu.service;

import org.springframework.data.domain.Page;

import com.rsu.latihanrsu.dto.request.PagingRequest;
import com.rsu.latihanrsu.dto.request.PolyclinicRequest;
import com.rsu.latihanrsu.dto.response.PolyclinicResponse;

public interface PolyclinicService {
    PolyclinicResponse addPolyclinic(PolyclinicRequest request);
    Page<PolyclinicResponse> getAllPolyclinics(PagingRequest pagingRequest);
    PolyclinicResponse getPolyclinicById(String polyclinicId);
    PolyclinicResponse updatePolyclinic(String polyclinicId, PolyclinicRequest request);
    void deletePolyclinic(String polyclinicId);
    Page<PolyclinicResponse> getAllPolyclinics(int page, int size);
}
