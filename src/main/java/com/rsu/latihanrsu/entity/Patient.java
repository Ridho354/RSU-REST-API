package com.rsu.latihanrsu.entity;

import com.rsu.latihanrsu.constant.PatientStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@Data
@Table(name="m_patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="patient_name", nullable=false)
    private String patient_name;

    @Enumerated(EnumType.STRING)
    @Column(name="patient_status", nullable=false)
    private PatientStatus patient_status;

    // @OneToOne
    // @JoinColumn(name="number_bpjs")
    @Column(name="number_bpjs")
    private String number_bpjs;

    @Column(name="patient_address", nullable=false)
    private String patient_address;

    @Column(name="birth_date", nullable=false)
    private String birth_date;

    @Column(name="gender")
    private String gender;

    @Column(name="phone_number")
    private String phone_number;

    @Column(name="disases_diagnosis", nullable=false)
    private String disases_diagnosis;

    @OneToOne
    @JoinColumn(name="user_account_id")
    private UserAccount userAccount;
}
