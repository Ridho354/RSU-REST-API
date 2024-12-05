package com.rsu.latihanrsu.dto.response;

import java.util.List;

import com.rsu.latihanrsu.dto.MedicineTransactionDetailDTO;

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
public class MedicineTransactionResponse {
    private String idMedicineTransaction;
    private Integer idPatient;
    private String transactionDate;
    private Long totalPrice;
    private String MedicinePaymentStatus;
    private List<MedicineTransactionDetailDTO> details;
}
