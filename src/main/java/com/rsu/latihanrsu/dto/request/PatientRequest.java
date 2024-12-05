package com.rsu.latihanrsu.dto.request;

import java.util.Optional;

import com.rsu.latihanrsu.constant.PatientStatus;
import com.rsu.latihanrsu.entity.Bpjs;
import com.rsu.latihanrsu.entity.UserAccount;

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
public class PatientRequest {
    private String patient_name;
    private PatientStatus patient_status;
    private String number_bpjs;
    private String patient_address;
    private String birth_date;
    private String gender;
    private String phone_number;
    private String disases_diagnosis;
    private UserAccount userAccount;
}
