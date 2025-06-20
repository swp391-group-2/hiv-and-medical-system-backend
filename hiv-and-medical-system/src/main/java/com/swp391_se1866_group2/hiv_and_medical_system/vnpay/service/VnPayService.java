package com.swp391_se1866_group2.hiv_and_medical_system.vnpay.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.utils.VnPayUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VnPayService {
    @Autowired
    private VnPayUtil util;

    public String createPayment (String orderId, long amount, String clientIp){
        try {
            return util.buildPaymentUrl(orderId, amount, clientIp);
        } catch (Exception ex) {
            throw new RuntimeException("Tạo VNPay URL thất bại", ex);
        }
    }


}
