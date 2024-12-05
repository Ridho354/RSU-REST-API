package com.rsu.latihanrsu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private String doctorId;
    private String doctorName;
    private String numberStr;
    private String specialist;
    private PolyclinicResponse polyclinic;
}