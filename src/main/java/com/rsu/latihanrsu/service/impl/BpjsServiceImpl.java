package com.rsu.latihanrsu.service.impl;

import com.rsu.latihanrsu.entity.Bpjs;
import com.rsu.latihanrsu.repository.BpjsRepository;
import com.rsu.latihanrsu.service.BpjsService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.rsu.latihanrsu.dto.request.BpjsRequest;
import com.rsu.latihanrsu.dto.response.BpjsResponse;

@Service
@RequiredArgsConstructor
public class BpjsServiceImpl implements BpjsService{

    @Autowired
    private final BpjsRepository bpjsRepository;

    @Override
    public List<Bpjs> findAll() {
        List<Bpjs> bpjsList = bpjsRepository.findAll();
        return bpjsList;
    }

    @Override
    public Optional<Bpjs> findBpjsById(String id) {
        return bpjsRepository.findById(id);
    }

    @Override
    public BpjsResponse addBpjs(BpjsRequest request) {
        Bpjs newBpjs = Bpjs.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .nik(request.getNik())
                    .build();
        Bpjs bpjsResponse = bpjsRepository.saveAndFlush(newBpjs);
        return toBpjsResponse(bpjsResponse);
    }

    @Override
    public void deleteBpjsById(String id) {
        bpjsRepository.deleteById(id);
    }

    @Override
    public BpjsResponse updateBpjs(String id, BpjsRequest request) {
        if(!bpjsRepository.existsById(id)) {
            throw new RuntimeException("Bpjs not found with id: " + id);
        }
        Bpjs bpjs = bpjsRepository.findById(id).get();
        if(!bpjs.getNumber_bpjs().equals(id)) {
            throw new RuntimeException("You can't update number_bpjs");
        }
        bpjs.setName(request.getName());
        bpjs.setAddress(request.getAddress());
        bpjs.setNik(request.getNik());
        Bpjs bpjsResponse = bpjsRepository.saveAndFlush(bpjs);
        return toBpjsResponse(bpjsResponse);
    }

    public BpjsResponse toBpjsResponse(Bpjs bpjs) {
        BpjsResponse bpjsResponse = new BpjsResponse();
        return bpjsResponse.builder()
                .number_bpjs(bpjs.getNumber_bpjs())
                .name(bpjs.getName())
                .address(bpjs.getAddress())
                .nik(bpjs.getNik())
                .build();
    }
}
