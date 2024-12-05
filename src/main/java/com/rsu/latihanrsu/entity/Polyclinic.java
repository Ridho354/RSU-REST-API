package com.rsu.latihanrsu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name="t_polyclinic")
public class Polyclinic {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String polyclinic_id;

    @Column(name="name_polyclinic", nullable=false)
    private String polyclinic_name;
}
