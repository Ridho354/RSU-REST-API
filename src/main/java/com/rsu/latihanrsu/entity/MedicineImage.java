package com.rsu.latihanrsu.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rsu.latihanrsu.constant.Constant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = Constant.MEDICINE_IMAGE_TABLE)
public class MedicineImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "public_id", nullable = false)
    private String publicId;

    @Column(name = "secure_url", nullable = false)
    private String secureUrl;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    @JsonIgnore
    private Medicine medicine;
}
