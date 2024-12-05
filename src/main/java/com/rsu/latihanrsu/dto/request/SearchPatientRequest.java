
package com.rsu.latihanrsu.dto.request;

import com.rsu.latihanrsu.dto.request.PagingRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SearchPatientRequest extends PagingRequest {
    private String name;
    private String status;
    private String no_bpjs;
}
