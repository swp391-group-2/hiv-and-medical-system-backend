package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentPatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientPrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.repository.MedicationRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionItemRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
    MedicationRepository medicationRepository;
    private final PatientPrescriptionItemRepository patientPrescriptionItemRepository;

    public PaPrescriptionResponse createPatientPrescription (PaPrescriptionCreation request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        PatientPrescription patientPrescription = patientPrescriptionMapper.toPatientPrescription(request);
        List<PatientPrescriptionItem> patientPrescriptionItemList = new ArrayList<>();
        request.getPatientPrescriptionItems().forEach(paPrescriptionItemCreation -> {
            PatientPrescriptionItem item = patientPrescriptionMapper.toPatientPrescriptionItem(paPrescriptionItemCreation);
            Medication medication = medicationRepository.findById(paPrescriptionItemCreation.getMedicationId())
                    .orElseThrow(() -> new AppException(ErrorCode.MEDICATION_NOT_EXISTED));
            item.setMedication(medication);
            patientPrescriptionItemRepository.save(item);
            patientPrescriptionItemList.add(patientPrescriptionItemRepository.save(item));
        });
        patientPrescription.setPrescriptionDefault(prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED)));
        patientPrescription.setPatientPrescriptionItems(patientPrescriptionItemList);
        patientPrescription.setNote(request.getNote());
        appointment.setPatientPrescription(patientPrescription);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
        patientPrescription.setAppointment(appointment);
        return patientPrescriptionMapper.toPaPrescriptionResponse(patientPrescriptionRepository.save(patientPrescription));
    }

    public PaPrescriptionResponse getPatientPrescription (int prescriptionId) {
        return patientPrescriptionMapper.toPaPrescriptionResponse(patientPrescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_PRESCRIPTION_NOT_EXISTED)));
    }

    public PaPrescriptionResponse getPatientPrescriptionByPatientId (String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_EXISTED));
        List<Appointment> appointments = appointmentRepository.findByPatient(patient).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        if(appointments == null || appointments.isEmpty()){
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        }
        List<PaPrescriptionResponse> prescriptionResponses = appointments.stream()
                .map(appointment -> patientPrescriptionMapper.toPaPrescriptionResponse(appointment.getPatientPrescription()))
                .toList();

        final PaPrescriptionResponse[] response = new PaPrescriptionResponse[1];

        prescriptionResponses.forEach(paPrescriptionResponse -> {
            if(paPrescriptionResponse != null){
                response[0] = paPrescriptionResponse ;
            }
        });

        return response[0];

    }


}
