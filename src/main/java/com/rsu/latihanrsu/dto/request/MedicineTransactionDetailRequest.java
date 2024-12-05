// MedicineTransactionDetailRequest.java
package com.rsu.latihanrsu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineTransactionDetailRequest {
    private String id_Medicine_transaction;
    private String id_Medicine;
    private Integer quantity;
}