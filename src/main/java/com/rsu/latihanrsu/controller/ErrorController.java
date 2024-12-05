package com.rsu.latihanrsu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.rsu.latihanrsu.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j // untuk logger
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        return ResponseUtil.buildResponse(HttpStatus.valueOf(statusCode.value()), e.getReason(), null);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleResponseStatusException(RuntimeException e) {
        return ResponseUtil.buildResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);

    }
}
