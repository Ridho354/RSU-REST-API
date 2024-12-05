package com.rsu.latihanrsu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonResponse<T> {
    private Integer status;
    private String message;
    private T data;
    private PagingResponse paging;

    public CommonResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
