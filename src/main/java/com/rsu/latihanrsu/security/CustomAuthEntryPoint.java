package com.rsu.latihanrsu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsu.latihanrsu.dto.response.CommonResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// #SPRING SECURITY# 13
@Component // kita berikan annotasi component supaya bisa diinject
@RequiredArgsConstructor
// AuthenticationEntryPoint interface/contract dari si Spring Security yang nangani kasus unauthorized
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    // commence = method yang akan dipanggil/dijalankan ketika user coba akses tanpa authentication
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // buat common response object
        CommonResponse<?> commonResponse = new CommonResponse<>(HttpStatus.UNAUTHORIZED.value(), authException.getMessage(), null);

        // convert object ke JSON String
        String responseString = objectMapper.writeValueAsString(commonResponse);

        // tulis responsenya
        response.getWriter().write(responseString);

        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // tulis response header "application/json"
    }
}
