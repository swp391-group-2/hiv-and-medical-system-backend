package com.swp391_se1866_group2.hiv_and_medical_system.vnpay.controller;

import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.vnpay.dto.CreatePaymentRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.vnpay.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vnpay")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VnPayController {
    private VnPayService vnPayService;

    @PostMapping("/create")
    public ApiResponse<Map<String, String>> createPayment (@RequestBody CreatePaymentRequest paymentRequest, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        String url = vnPayService.createPayment(paymentRequest.getOrderId(), paymentRequest.getAmount(), ip);
        return  ApiResponse.<Map<String, String>>builder()
                .data(Collections.singletonMap("paymentUrl", url))
                .success(true)
                .build();
    }

    @GetMapping("/return")
    public void paymentReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String responseCode = request.getParameter("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            response.sendRedirect("/payment-success");
        } else {
            response.sendRedirect("/payment-fail");
        }
    }

}
