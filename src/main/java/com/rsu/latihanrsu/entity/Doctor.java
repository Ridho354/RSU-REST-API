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
@Table(name="t_doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String doctor_id;

    @Column(name="doctor_name", nullable=false)
    private String doctor_name;

    @Column(name="number_str", nullable=false)
    private String number_str;

    @Column(name="specialist")
    private String specialist;

    @ManyToOne
    @JoinColumn(name="polyclinic_name")
    private Polyclinic polyclinic_name;

    public void setPolyclinicName(String polyclinicName) {
        Polyclinic polyclinic = new Polyclinic();
        polyclinic.setPolyclinic_name(polyclinicName);
        this.polyclinic_name = polyclinic;
    }
}
