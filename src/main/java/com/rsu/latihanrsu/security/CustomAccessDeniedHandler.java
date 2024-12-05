package com.rsu.latihanrsu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsu.latihanrsu.dto.response.CommonResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// #SPRING SECURITY# 13
@Component // kita berikan annotasi component supaya bisa diinject
@RequiredArgsConstructor
// AccessDeniedHandler = interface/contract dari si Spring Security yang nangani kasus forbidden access
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    // handle = method yang akan dipanggil/dijalankan ketika user coba akses tanpa Role yang sesuai
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // buat common response object
        CommonResponse<?> commonResponse = new CommonResponse<>(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage(), null);

        // convert object ke JSON String
        String responseString = objectMapper.writeValueAsString(commonResponse);

        // tulis responsenya
        response.getWriter().write(responseString);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 alternative dari HttpStatus.FORBIDDEN.value()
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // tulis response header "application/json"
    }
}