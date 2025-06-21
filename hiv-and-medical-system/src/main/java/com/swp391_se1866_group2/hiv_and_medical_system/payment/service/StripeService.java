package com.swp391_se1866_group2.hiv_and_medical_system.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.PaymentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.dto.PaymentRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.repository.PaymentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeService {
    final PaymentRepository paymentRepository;
    final  ServiceRepository serviceRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    public String createCheckoutSession(PaymentRequest request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Payment payment = Payment.builder()
                .patientId(request.getPatientId())
                .serviceId(request.getServiceId())
                .amount(request.getAmount())
                .scheduleSlotId(request.getScheduleSlotId())
                .labTestSlotId(request.getLabTestSlotId())
                .status(PaymentStatus.PENDING)
                .build();

        ServiceEntity service = serviceRepository.findById(payment.getServiceId()).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTED));

        paymentRepository.save(payment);

        payment = paymentRepository.findById(payment.getId()).orElseThrow(() -> new  AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success")
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("vnd")
                                                .setUnitAmount(request.getAmount())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(service.getName())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("paymentId", String.valueOf(payment.getId()))
                .build();

        Session session = Session.create(params);

        payment.setSessionId(session.getId());
        paymentRepository.save(payment);


        
        return session.getUrl();
    }

}
