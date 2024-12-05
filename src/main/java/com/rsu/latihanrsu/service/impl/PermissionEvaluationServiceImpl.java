package com.rsu.latihanrsu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.entity.UserAccount;
import com.rsu.latihanrsu.repository.PatientRepository;
import com.rsu.latihanrsu.service.PermissionEvaluationService;

// #SPRING SECURITY# 12
@Service
@RequiredArgsConstructor
public class PermissionEvaluationServiceImpl implements PermissionEvaluationService {
    private final PatientRepository patientRepository;

    @Override
    public boolean hasAccessToCustomer(Integer patientId, UserAccount userAccount) {
        return patientRepository.existsByIdAndUserAccount_id(patientId, userAccount.getId());
    }
}
