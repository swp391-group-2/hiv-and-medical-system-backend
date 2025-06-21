package com.swp391_se1866_group2.hiv_and_medical_system.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private String patientId;
    private String service;
    private int scheduleSlotId;
    private int labTestSlotId;
    private long amount;
}
