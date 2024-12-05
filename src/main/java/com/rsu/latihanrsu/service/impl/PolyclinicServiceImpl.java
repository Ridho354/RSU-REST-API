// src/main/java/com/rsu/latihanrsu/service/impl/PolyclinicServiceImpl.java

package com.rsu.latihanrsu.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.dto.request.PagingRequest;
import com.rsu.latihanrsu.dto.request.PolyclinicRequest;
import com.rsu.latihanrsu.dto.response.PolyclinicResponse;
import com.rsu.latihanrsu.entity.Polyclinic;
import com.rsu.latihanrsu.repository.PolyclinicRepository;
import com.rsu.latihanrsu.service.PolyclinicService;

@Service
public class PolyclinicServiceImpl implements PolyclinicService {
    private final PolyclinicRepository polyclinicRepository;

    public PolyclinicServiceImpl(PolyclinicRepository polyclinicRepository) {
        this.polyclinicRepository = polyclinicRepository;
    }

    @Override
    public PolyclinicResponse addPolyclinic(PolyclinicRequest request) {
        Polyclinic polyclinic = Polyclinic.builder()
                .polyclinic_name(request.getPolyclinicName())
                .build();
        polyclinic = polyclinicRepository.save(polyclinic);
        return PolyclinicResponse.builder()
                .polyclinicId(polyclinic.getPolyclinic_id())
                .polyclinicName(polyclinic.getPolyclinic_name())
                .build();
    }

    @Override
    public Page<PolyclinicResponse> getAllPolyclinics(PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize());
        Page<Polyclinic> polyclinics = polyclinicRepository.findAll(pageable);
        return polyclinics.map(polyclinic -> PolyclinicResponse.builder()
                .polyclinicId(polyclinic.getPolyclinic_id())
                .polyclinicName(polyclinic.getPolyclinic_name())
                .build());
    }

    @Override
    public PolyclinicResponse getPolyclinicById(String polyclinicId) {
        Polyclinic polyclinic = polyclinicRepository.findById(polyclinicId).orElse(null);
        if (polyclinic == null) {
            return null;
        }
        return PolyclinicResponse.builder()
                .polyclinicId(polyclinic.getPolyclinic_id())
                .polyclinicName(polyclinic.getPolyclinic_name())
                .build();
    }

    @Override
    public PolyclinicResponse updatePolyclinic(String polyclinicId, PolyclinicRequest request) {
        Polyclinic polyclinic = polyclinicRepository.findById(polyclinicId).orElse(null);
        if (polyclinic == null) {
            return null;
        }
        polyclinic.setPolyclinic_name(request.getPolyclinicName());
        polyclinic = polyclinicRepository.save(polyclinic);
        return PolyclinicResponse.builder()
                .polyclinicId(polyclinic.getPolyclinic_id())
                .polyclinicName(polyclinic.getPolyclinic_name())
                .build();
    }

    @Override
    public void deletePolyclinic(String polyclinicId) {
        polyclinicRepository.deleteById(polyclinicId);
    }

    @Override
    public Page<PolyclinicResponse> getAllPolyclinics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Polyclinic> polyclinics = polyclinicRepository.findAll(pageable);
        return polyclinics.map(polyclinic -> PolyclinicResponse.builder()
                .polyclinicId(polyclinic.getPolyclinic_id())
                .polyclinicName(polyclinic.getPolyclinic_name())
                .build());
    }
}
