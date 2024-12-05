package com.rsu.latihanrsu.dto.request;

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
public class DoctorRequest {
    private String doctorName;
    private String numberStr;
    private String specialist;
    private String polyclinicId;
}