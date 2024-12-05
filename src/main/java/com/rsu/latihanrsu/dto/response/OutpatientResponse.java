package com.rsu.latihanrsu.dto.response;

import java.time.LocalDate;

import com.rsu.latihanrsu.constant.OutpatientStatus;
import com.rsu.latihanrsu.constant.PatientStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutpatientResponse {
    private int queueNumber;
    private OutpatientStatus status;
    private Integer patientId;
    private String patientName;
    private PatientStatus patientStatus;
    private String numberBpjs;
    private LocalDate dateControl;
    private Integer idControlNumber;
    private String polyclinicName;
    private String diagnosisDiseases;
    private String idMedicineTransaction;
}
