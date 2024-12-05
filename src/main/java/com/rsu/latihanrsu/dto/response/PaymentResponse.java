package com.rsu.latihanrsu.dto.response;

import com.rsu.latihanrsu.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// #Midtrans# buat PaymentResponseDTO
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String order;
    private Long amount;
    private PaymentStatus paymentStatus;
    private String tokenSnap;
    private String redirectUrl;
}
