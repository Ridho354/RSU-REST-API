package com.rsu.latihanrsu.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.rsu.latihanrsu.constant.UserRole;
import com.rsu.latihanrsu.dto.request.AuthRequest;
import com.rsu.latihanrsu.dto.request.PatientRequest;
import com.rsu.latihanrsu.dto.request.RegisterPatientRequest;
import com.rsu.latihanrsu.dto.response.LoginResponse;
import com.rsu.latihanrsu.dto.response.RegisterResponse;
import com.rsu.latihanrsu.entity.UserAccount;
import com.rsu.latihanrsu.repository.BpjsRepository;
import com.rsu.latihanrsu.service.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PatientService patientService;
    private final BpjsRepository bpjsRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(RegisterPatientRequest request) {
        if(userService.getByUsername(request.getUsername()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_PATIENT)
                .build();

        UserAccount savedUserAccount = userService.createUser(userAccount);

        PatientRequest patientRequest = PatientRequest.builder()
                .patient_name(request.getPatient_name())
                .patient_status(request.getPatient_status())
                .number_bpjs(request.getNumber_bpjs())
                .patient_address(request.getPatient_address())
                .birth_date(request.getBirth_date())
                .gender(request.getGender())
                .phone_number(request.getPhone_number())
                .disases_diagnosis(request.getDisases_diagnosis())
                .userAccount(userAccount)
                .build();

        patientService.addPatient(patientRequest);

        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(String.valueOf(userAccount.getRole()))
                .build();
    }

    @Override
    public RegisterResponse registerStaff(AuthRequest request) {
        if(userService.getByUsername(request.getUsername()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_STAFF)
                .build();

        userService.createUser(userAccount);
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(String.valueOf(userAccount.getRole()))
                .build();
    }

    @Override
    public RegisterResponse registerSuperAdmin(AuthRequest request, String superAdminRequestHeader) {
        if (!superAdminRequestHeader.equals("12345678")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Super Admin header does not match");
        }

        if(userService.getByUsername(request.getUsername()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        UserAccount userAccount = UserAccount.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_SUPER_ADMIN)
                .build();

        userService.createUser(userAccount);
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(String.valueOf(userAccount.getRole()))
                .build();
    }

    private UserAccount manualValidateCredentialsAndGetUserAccount(AuthRequest request) {
        UserAccount userAccount = userService.getByUsername(request.getUsername());

        if (userAccount != null){
            boolean isValid = passwordEncoder.matches(request.getPassword(), userAccount.getPassword());

            if (isValid){
                return userAccount;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken
        );

        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

//        UserAccount userAccount = manualValidateCredentialsAndGetUserAccount(request);
//
        String generatedToken = jwtService.generateToken(userAccount);

        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .token(generatedToken)
                .roles(String.valueOf(userAccount.getRole()))
                .build();
    }
}
