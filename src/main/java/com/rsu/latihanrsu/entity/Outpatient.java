package com.rsu.latihanrsu.entity;


import java.time.LocalDate;
import com.rsu.latihanrsu.constant.OutpatientStatus;
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
@Table(name="m_outpatient")
public class Outpatient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String outpatient_id;

    // @CreatedDate
    @Column(name="dateControl", nullable=false)
    private LocalDate dateControl;

    @Column(name = "queue_number")
    private int queueNumber;

    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Patient patient_id;

    @ManyToOne
    @JoinColumn(name="idControlNumber")
    private ControlNumber idControlNumber;

    @ManyToOne
    @JoinColumn(name="id_Medicine_transaction")
    private MedicineTransaction id_Medicine_transaction;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private OutpatientStatus status;
}
