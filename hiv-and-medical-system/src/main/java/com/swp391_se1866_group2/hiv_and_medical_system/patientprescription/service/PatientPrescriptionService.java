package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.service.AppointmentService;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientPrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientPrescriptionService {
    PatientPrescriptionRepository patientPrescriptionRepository;
    PatientPrescriptionMapper patientPrescriptionMapper;
    PatientPrescriptionItemService prescriptionItemService;
    AppointmentRepository appointmentRepository;
    PatientRepository patientRepository;
    PrescriptionRepository prescriptionRepository;

    public PaPrescriptionResponse createPatientPrescription (PaPrescriptionCreation request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        PatientPrescription patientPrescription = patientPrescriptionMapper.toPatientPrescription(request);
        List<PatientPrescriptionItem> patientPrescriptionItemList = new ArrayList<>();
        request.getPatientPrescriptionItems().forEach(paPrescriptionItemCreation -> {
            patientPrescriptionItemList.add(prescriptionItemService.create(paPrescriptionItemCreation));
        });
        patientPrescription.setPrescription(prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED)));
        patientPrescription.setPatientPrescriptionItems(patientPrescriptionItemList);
        appointment.setPatientPrescription(patientPrescription);
        patientPrescription.setAppointment(appointment);
        return patientPrescriptionMapper.toPaPrescriptionResponse(patientPrescriptionRepository.save(patientPrescription));
    }

    public PaPrescriptionResponse getPatientPrescription (int prescriptionId) {
        return patientPrescriptionMapper.toPaPrescriptionResponse(patientPrescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_PRESCRIPTION_NOT_EXISTED)));
    }

    public List<PaPrescriptionResponse> getPatientPrescriptionByPatientId (String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_EXISTED));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient)
                .orElseThrow(()-> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        return appointments.stream()
                .map(appointment -> patientPrescriptionMapper.toPaPrescriptionResponse(appointment.getPatientPrescription()))
                .collect(Collectors.toList());
    }


}
