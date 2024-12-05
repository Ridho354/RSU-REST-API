package com.rsu.latihanrsu.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="t_Medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String medicine_id;

    @Column(name="Medicine_name", nullable=false)
    private String Medicine_name;
    
    @Column(name="Medicine_price", nullable=false)
    private Long Medicine_price;

    @Column(name="Medicine_info")
    private String Medicine_info;

    @OneToMany(mappedBy="medicine", cascade=CascadeType.ALL,orphanRemoval=true)
    private List<MedicineImage> images;
}
