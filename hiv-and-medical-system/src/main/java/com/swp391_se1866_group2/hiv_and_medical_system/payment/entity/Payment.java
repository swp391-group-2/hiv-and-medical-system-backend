package com.swp391_se1866_group2.hiv_and_medical_system.payment.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String patientId;
    private int serviceId;
    private int scheduleSlotId;
    private int labTestSlotId;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String sessionId;
    private String paymentIntentId;
    @CreationTimestamp
    private Instant succeededAt;
}
