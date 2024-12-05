package com.rsu.latihanrsu.dto.response;

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
public class BpjsResponse {
    private String number_bpjs;
    private String name;
    private String address;
    private String nik;
}
