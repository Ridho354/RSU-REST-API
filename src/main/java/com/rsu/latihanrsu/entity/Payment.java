package com.rsu.latihanrsu.entity;

import com.rsu.latihanrsu.constant.PaymentStatus;
import com.rsu.latihanrsu.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

// #Midtrans#
@Entity
@Table(name = Constant.PAYMENT_TABLE)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="id_medicine_transaction")
    private MedicineTransaction medicineTransaction;

    // // @OneToOne
    // // @JoinColumn(name = "order_id")
    
    // @Column(name = "order_id")
    // private String orderId;

    @Column(name = "total_amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus status;

    @Column(name = "token_snap")
    private String tokenSnap;

    @Column(name = "redirect_url")
    private String redirectUrl;
}
