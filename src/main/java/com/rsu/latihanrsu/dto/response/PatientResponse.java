package com.rsu.latihanrsu.dto.response;

import com.rsu.latihanrsu.constant.PatientStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private Integer patient_id;
    private String patient_name;
    private PatientStatus patient_status;
    private String number_bpjs;
    private String patient_address;
    private String birth_date;
    private String gender;
    private String phone_number;
    private String disases_diagnosis;
    private String userAccountId;
}
