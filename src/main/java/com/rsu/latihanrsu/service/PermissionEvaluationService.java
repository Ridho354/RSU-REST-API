package com.rsu.latihanrsu.service;

import com.rsu.latihanrsu.entity.UserAccount;

public interface PermissionEvaluationService {
    public boolean hasAccessToCustomer(Integer patientId, UserAccount userAccount);
}
