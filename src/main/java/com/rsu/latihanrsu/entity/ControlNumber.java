package com.rsu.latihanrsu.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "control_number")
public class ControlNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idControlNumber;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Patient patient_id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor_id;

    @ManyToOne
    @JoinColumn(name = "polyclinic_id", referencedColumnName = "polyclinic_id")
    private Polyclinic polyclinic_id;

    private Date nextControlDate;
    private String diagnosisDiseases;

}