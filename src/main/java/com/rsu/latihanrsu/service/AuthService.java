package com.rsu.latihanrsu.service;

import com.rsu.latihanrsu.dto.request.AuthRequest;
import com.rsu.latihanrsu.dto.request.RegisterPatientRequest;
import com.rsu.latihanrsu.dto.response.LoginResponse;
import com.rsu.latihanrsu.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterPatientRequest request);
    RegisterResponse registerStaff(AuthRequest request);
    RegisterResponse registerSuperAdmin(AuthRequest request, String superAdminRequestHeader);
    LoginResponse login(AuthRequest request);
}
