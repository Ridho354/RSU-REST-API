
// MedicineTransactionDetailResponse.java
package com.rsu.latihanrsu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineTransactionDetailResponse {
    private String id;
    private String id_Medicine_transaction;
    private String id_Medicine;
    private Integer quantity;
    private String Medicine_name;
}
