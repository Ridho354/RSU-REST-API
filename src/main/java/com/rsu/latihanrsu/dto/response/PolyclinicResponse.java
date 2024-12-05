// src/main/java/com/rsu/latihanrsu/dto/response/PolyclinicResponse.java

package com.rsu.latihanrsu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolyclinicResponse {
    private String polyclinicId;
    private String polyclinicName;
}