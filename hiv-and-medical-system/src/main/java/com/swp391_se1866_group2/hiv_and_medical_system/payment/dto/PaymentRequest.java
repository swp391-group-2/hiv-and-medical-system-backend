package com.swp391_se1866_group2.hiv_and_medical_system.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long id;
    private String patientId;
    private int serviceId;
    private int scheduleSlotId;
    private int labTestSlotId;
}
