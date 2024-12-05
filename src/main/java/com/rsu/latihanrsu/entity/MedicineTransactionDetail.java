package com.rsu.latihanrsu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="t_Medicine_transaction_detail")
public class MedicineTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_Medicine_transaction_detail;

    @JoinColumn(name="id_Medicine_transaction")
    @ManyToOne
    private MedicineTransaction id_Medicine_transaction;

    @ManyToOne
    @JoinColumn(name="Medicine_id")
    private Medicine Medicine_id;

    @Column(name="quantity", nullable=false)
    private int quantity;

    public String getId() {
        return this.id_Medicine_transaction_detail;
    }

}
