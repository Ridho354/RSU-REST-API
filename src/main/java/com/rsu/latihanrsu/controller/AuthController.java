package com.rsu.latihanrsu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsu.latihanrsu.constant.Constant;
import com.rsu.latihanrsu.dto.request.AuthRequest;
import com.rsu.latihanrsu.dto.request.RegisterPatientRequest;
import com.rsu.latihanrsu.dto.response.LoginResponse;
import com.rsu.latihanrsu.dto.response.RegisterResponse;
import com.rsu.latihanrsu.service.AuthService;
import com.rsu.latihanrsu.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Auth Controller", description="Register dan Login")
@RestController
@RequestMapping(Constant.AUTH_API)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register Patient & Account", description = "Mendaftarkan Pasien baru sekaligus membuat akun")
    @ApiResponses(
         @ApiResponse(
            responseCode = "200",
            description = "Patient Account Has Been Created Successfully",
            content = @Content(schema = @Schema(implementation = RegisterResponse.class))
        )
    )

    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterPatientRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Successfully", response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/register-staff")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerStaff(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Successfully", response);
    }

    @PostMapping("/register-super-admin")
    public ResponseEntity<?> registerSuperAdmin(@RequestBody AuthRequest request, @RequestHeader(name = "Super-Admin-Header-Key") String superAdminRequestHeader) {
        RegisterResponse response = authService.registerSuperAdmin(request, superAdminRequestHeader);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, "Register Successfully", response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse response = authService.login(authRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully Login", response);
    }
}
