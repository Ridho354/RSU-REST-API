package com.rsu.latihanrsu.dto.request;

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
public class OutpatientRegister {
    private OutpatientStatus status;
    private Integer patientId;
    private PatientStatus patientStatus;
    private LocalDate dateControl;
    private Integer idControlNumber;
}
