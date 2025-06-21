package com.swp391_se1866_group2.hiv_and_medical_system.payment.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("api/payments")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService vnPayService;

    public PaymentController(PaymentService vnPayService) {
        this.vnPayService = vnPayService;
    }

    @GetMapping("/vn-pay")
    public ApiResponse<String> getVnPayUrl(
            @RequestParam Long amount,
            @RequestParam String bankCode,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        String paymentUrl = vnPayService.createPaymentUrl(amount, bankCode, request);
        return ApiResponse.<String>builder()
                .success(true)
                .data(paymentUrl)
                .build();


    }
}
