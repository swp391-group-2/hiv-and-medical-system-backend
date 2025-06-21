package com.swp391_se1866_group2.hiv_and_medical_system.payment.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentCreationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.PaymentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.repository.PaymentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/payments/webhook")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeWebhookController {
    @Value("${stripe.webhook.secret}")
    String endpointSecret;

    final PaymentRepository paymentRepo;
    final AppointmentService appointmentService;


    @PostMapping
    public ApiResponse<AppointmentCreationResponse> handle (@RequestHeader("Stripe-Signature") String signature, @RequestBody String payload) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, signature, endpointSecret);
        AppointmentCreationResponse appointmentResponse = null;
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session)event.getDataObjectDeserializer()
                    .getObject().orElseThrow();
            String sessionId = session.getId();
            String intentId  = session.getPaymentIntent();


            Payment payment = paymentRepo.findBySessionId(sessionId)
                    .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

            if (payment.getStatus() != PaymentStatus.PAID) {
                payment.setStatus(PaymentStatus.PAID);
                payment.setPaymentIntentId(intentId);
                payment.setSucceededAt(Instant.now());
                paymentRepo.save(payment);
                AppointmentCreationRequest appointment = new AppointmentCreationRequest();
                appointment.setLabTestSlotId(payment.getLabTestSlotId());
                appointment.setPatientId(payment.getPatientId());
                appointment.setServiceId(payment.getServiceId());
                appointment.setScheduleSlotId(payment.getScheduleSlotId());
                appointmentResponse = appointmentService.createAppointment(appointment);
            }

        }
        return ApiResponse.<AppointmentCreationResponse>builder()
                .success(true)
                .data(appointmentResponse)
                .build();
    }


}
