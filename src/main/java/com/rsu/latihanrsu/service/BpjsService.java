package com.rsu.latihanrsu.service;

import java.util.List;
import java.util.Optional;

import com.rsu.latihanrsu.dto.request.BpjsRequest;
import com.rsu.latihanrsu.dto.response.BpjsResponse;
import com.rsu.latihanrsu.entity.Bpjs;

public interface BpjsService {
    public List<Bpjs> findAll();
    public Optional<Bpjs> findBpjsById(String id);
    public BpjsResponse addBpjs(BpjsRequest bpjsRequest);
    public void deleteBpjsById(String id);
    public BpjsResponse updateBpjs(String id, BpjsRequest bpjsRequest);
}
