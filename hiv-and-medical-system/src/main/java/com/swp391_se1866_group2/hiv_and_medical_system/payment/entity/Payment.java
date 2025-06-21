package com.swp391_se1866_group2.hiv_and_medical_system.payment.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vnpay")
@Getter
@Setter
public class Payment {
    private String tmnCode;
    private String secretKey;
    private String returnUrl;
    private String payUrl;
}
