package com.rsu.latihanrsu.dto.request;

import com.rsu.latihanrsu.constant.MedicinePayment;
import com.rsu.latihanrsu.entity.Medicine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusRequest {
    private MedicinePayment orderStatus;
}
