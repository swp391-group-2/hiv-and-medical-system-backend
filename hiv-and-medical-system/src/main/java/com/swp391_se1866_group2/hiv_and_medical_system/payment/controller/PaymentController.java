package com.swp391_se1866_group2.hiv_and_medical_system.payment.controller;

import com.stripe.exception.StripeException;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.dto.PaymentRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.service.StripeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    StripeService stripeService;

    @PostMapping("/create-session")
    public ApiResponse<String> createSession(@RequestBody PaymentRequest req) throws StripeException {
        String checkoutUrl = stripeService.createCheckoutSession(req);

        return ApiResponse.<String>builder()
                .success(true)
                .data(checkoutUrl)
                .build();
    }
}
