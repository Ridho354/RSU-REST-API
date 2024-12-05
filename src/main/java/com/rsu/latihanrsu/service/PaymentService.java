package com.rsu.latihanrsu.service;

import com.rsu.latihanrsu.dto.request.MidtransNotificationRequest;
import com.rsu.latihanrsu.dto.response.PaymentResponse;

// #Midtrans#
public interface PaymentService {
    PaymentResponse createPayment(String orderId);
    void handlePaymentNotification(MidtransNotificationRequest request); // di hit oleh midtrans, dengan mekanisme WebHook, ketika misal pembayaran telah berhasil
}
