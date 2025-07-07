package com.swp391_se1866_group2.hiv_and_medical_system.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.PaymentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service.LabTestService;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.dto.PaymentRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.entity.Payment;
import com.swp391_se1866_group2.hiv_and_medical_system.payment.repository.PaymentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.repository.LabTestSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service.LabTestSlotService;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeService {
    final PaymentRepository paymentRepository;
    final  ServiceRepository serviceRepository;
    final ScheduleSlotRepository scheduleSlotRepo;
    final LabTestSlotRepository labTestSlotRepo;
    final LabTestSlotService labTestSlotService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    public String createCheckoutSession(PaymentRequest request) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        ServiceEntity service = serviceRepository.findById(request.getServiceId()).orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTED));
        ScheduleSlot scheduleSlot = scheduleSlotRepo.findScheduleSlotById(request.getScheduleSlotId());
        if(scheduleSlot != null){
            if(scheduleSlot.getStatus().equals(ScheduleSlotStatus.UNAVAILABLE)){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
        }
        LabTestSlot labTestSlot = labTestSlotRepo.findByLabTestSlotId(request.getLabTestSlotId());
        if(service.getServiceType().equals(ServiceType.CONSULTATION) && scheduleSlot == null && labTestSlot != null){
            Page<ScheduleSlot> scheduleSlotTmp = scheduleSlotRepo.chooseDoctorBySlotId(labTestSlot.getSlot().getId(), labTestSlot.getDate() , PageRequest.of(0,1));
            if(scheduleSlotTmp.getContent().getFirst() == null){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
            request.setScheduleSlotId(scheduleSlotTmp.getContent().getFirst().getId());
        }

        String price = String.valueOf(service.getPrice());
        Payment payment = Payment.builder()
                .patientId(request.getPatientId())
                .serviceId(request.getServiceId())
                .amount(Long.parseLong(price))
                .scheduleSlotId(request.getScheduleSlotId())
                .labTestSlotId(request.getLabTestSlotId())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        payment = paymentRepository.findById(payment.getId()).orElseThrow(() -> new  AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment-success")
                .setCancelUrl("http://localhost:5173/payment-cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("vnd")
                                                .setUnitAmount(Long.parseLong(price))
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
