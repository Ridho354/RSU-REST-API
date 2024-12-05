package com.rsu.latihanrsu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.rsu.latihanrsu.constant.MedicinePayment;
import com.rsu.latihanrsu.constant.PaymentStatus;
import com.rsu.latihanrsu.dto.request.MidtransNotificationRequest;
import com.rsu.latihanrsu.dto.request.MidtransPaymentRequest;
import com.rsu.latihanrsu.dto.request.MidtransTransactionDetailRequest;
import com.rsu.latihanrsu.dto.request.UpdateOrderStatusRequest;
import com.rsu.latihanrsu.dto.response.MidtransSnapResponse;
import com.rsu.latihanrsu.dto.response.PaymentResponse;
import com.rsu.latihanrsu.entity.Medicine;
import com.rsu.latihanrsu.entity.MedicineTransaction;
import com.rsu.latihanrsu.entity.Payment;
import com.rsu.latihanrsu.repository.PaymentRepository;
import com.rsu.latihanrsu.service.MedicineTransactionService;
import com.rsu.latihanrsu.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Qualifier("midtransPaymentGatewayClient")
    private final RestClient midtransPaymentGatewayClient;
    private final PaymentRepository paymentRepository;
    private final MedicineTransactionService medicineTransactionService;

    public PaymentServiceImpl(RestClient midtransPaymentGatewayClient, PaymentRepository paymentRepository, MedicineTransactionService orderService) {
        this.midtransPaymentGatewayClient = midtransPaymentGatewayClient;
        this.paymentRepository = paymentRepository;
        this.medicineTransactionService = orderService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResponse createPayment(String id) {
        
        // #Midtrans# 1 ambil order
        MedicineTransaction order = medicineTransactionService.getOneMedicineTransactionById(id);
        if (order.getMedicinePaymentStatus() != MedicinePayment.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order status must be PENDING but current status is " + order.getMedicinePaymentStatus());
        }
        /*
        // #Midtrans# 2 dapetin total amount dari order, total masing-masing item dijumlah
        // bagaimana dapatkan total amount dari berbagai order items/details? bisa pakai reduce bisa pakai
        // https://www.baeldung.com/java-stream-sum
        List<OrderDetails> orderDetailList = order.getOrderDetails();
        Long totalAmount = orderDetailList.stream().mapToLong(orderDetail -> {
            Long totalAmountPerDetail = orderDetail.getMenu().getPrice() * orderDetail.getQuantity();
            return totalAmountPerDetail;
        }).sum();
        */
        
        // #Midtrans# 3 build MidtransTransactionDetailRequest nya
        MidtransTransactionDetailRequest midtransTransactionDetailRequest = MidtransTransactionDetailRequest.builder()
                .orderId(id)
                .grossAmount(medicineTransactionService.getOneMedicineTransactionById(id).getTotal_price())
                .build();

        MidtransPaymentRequest midtransRequest = MidtransPaymentRequest.builder()
                .transactionDetail(midtransTransactionDetailRequest)
                .enabledPayments(List.of("gopay", "shopeepay", "bca_va"))
                .build();

        // #Midtrans# 3 request ke midtrans
        MidtransSnapResponse midtransSnapResponse = midtransPaymentGatewayClient
                .post() // http method
                .uri("/snap/v1/transactions") // endpoint path
                .body(midtransRequest)
                .retrieve()
                .body(MidtransSnapResponse.class);

        assert midtransSnapResponse != null;
        Payment payment = Payment.builder()
                .medicineTransaction(medicineTransactionService.getOneMedicineTransactionById(id))
                .amount(medicineTransactionService.getOneMedicineTransactionById(id).getTotal_price())
                .status(PaymentStatus.PENDING)
                .tokenSnap(midtransSnapResponse.getToken()) // dari midtransSnapResponse
                .redirectUrl(midtransSnapResponse.getRedirectUrl()) // dari midtransSnapResponse
                .build();

        // #Midtrans# 4 save ke table payment
        paymentRepository.saveAndFlush(payment);

        MedicineTransaction updatedOrder = medicineTransactionService.getOneMedicineTransactionById(id);
        updatedOrder.setOrder(payment.getMedicineTransaction().getId_Medicine_transaction());

        return PaymentResponse.builder()
                .order(payment.getMedicineTransaction().getId_Medicine_transaction())
                .amount(medicineTransactionService.getOneMedicineTransactionById(id).getTotal_price())
                .paymentStatus(PaymentStatus.PENDING)
                .tokenSnap(midtransSnapResponse.getToken())
                .redirectUrl(midtransSnapResponse.getRedirectUrl())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlePaymentNotification(MidtransNotificationRequest request) {
        // 1. dapetin payment dari order id
        Payment payment = paymentRepository.findByMedicineTransactionId(request.getOrderId());
        System.out.println("-".repeat(30));
        System.out.println("orderId :"+request.getOrderId());
        System.out.println("request :"+request);
        
        System.out.println("\npayment :"+payment);
        System.out.println("-".repeat(30));

        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }

        // 2. update payment status berdasarkan dari midtrans
        PaymentStatus newPaymentStatus = PaymentStatus.fromStatus(request.getTransactionStatus());

        payment.setStatus(newPaymentStatus);
        paymentRepository.saveAndFlush(payment);

        // 3. update order status
        if (newPaymentStatus == PaymentStatus.SETTLEMENT) {
            UpdateOrderStatusRequest updateOrderStatusToPaid = UpdateOrderStatusRequest.builder().orderStatus(MedicinePayment.PAID).build();
            medicineTransactionService.updateOrderStatus(request.getOrderId(), updateOrderStatusToPaid);
        } else if (
                newPaymentStatus == PaymentStatus.CANCEL ||
                newPaymentStatus == PaymentStatus.DENY ||
                newPaymentStatus == PaymentStatus.EXPIRE
            ) {
            UpdateOrderStatusRequest updateOrderStatusToCanceled = UpdateOrderStatusRequest.builder().orderStatus(MedicinePayment.CANCELLED).build();
            medicineTransactionService.updateOrderStatus(request.getOrderId(), updateOrderStatusToCanceled);
        }
    }
}