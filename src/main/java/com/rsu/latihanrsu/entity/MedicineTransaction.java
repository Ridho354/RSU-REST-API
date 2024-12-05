
package com.rsu.latihanrsu.entity;
import org.springframework.data.annotation.CreatedDate;

import com.rsu.latihanrsu.constant.MedicinePayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name="m_Medicine_transaction")
public class MedicineTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_Medicine_transaction;

    // @ManyToOne
    // @JoinColumn(name="id_patient")
    // private Patient id_patient;  

    @CreatedDate
    @Column(name="transaction_date")
    private String transaction_date;

    @Column(name="total_price")
    private Long total_price;

    @Enumerated(EnumType.STRING)
    @Column(name="Medicine_payment_status", nullable=false)
    private MedicinePayment MedicinePaymentStatus;

    @Column(name="order_id")
    private String order;
}
