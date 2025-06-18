package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientPrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientPrescriptionService {
    PatientPrescriptionRepository prescriptionRepository;
    PatientPrescriptionMapper patientPrescriptionMapper;
    PatientPrescriptionItemService prescriptionItemService;
    AppointmentRepository appointmentRepository;

    public PaPrescriptionResponse createPatientPrescription (PaPrescriptionCreation request, int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        PatientPrescription patientPrescription = patientPrescriptionMapper.toPatientPrescription(request);
        List<PatientPrescriptionItem> patientPrescriptionItemList = new ArrayList<>();
        request.getPatientPrescriptionItems().forEach(paPrescriptionItemCreation -> {
            patientPrescriptionItemList.add(prescriptionItemService.create(paPrescriptionItemCreation));
        });
        patientPrescription.setPatientPrescriptionItems(patientPrescriptionItemList);
        appointment.setPatientPrescription(patientPrescription);
        patientPrescription.setAppointment(appointment);
        return patientPrescriptionMapper.toPaPrescriptionResponse(prescriptionRepository.save(patientPrescription));
    }


}
