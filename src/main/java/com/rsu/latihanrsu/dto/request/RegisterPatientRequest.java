package com.rsu.latihanrsu.dto.request;

import com.rsu.latihanrsu.constant.PatientStatus;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPatientRequest {
    private String username;
    private String password;
    private String patient_name;
    private PatientStatus patient_status;
    private String number_bpjs;
    private String patient_address;
    private String birth_date;
    private String gender;
    private String phone_number;
    private String disases_diagnosis;
}
