package com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientPrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.service.MedicationService;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionItemCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientPrescriptionItemService {
    PatientPrescriptionItemRepository paPreItemRepository;
    PatientPrescriptionMapper patientPrescriptionMapper;

    MedicationService medicationService;

    public PatientPrescriptionItem create(PaPrescriptionItemCreation request) {
        medicationService.getMedication(request.getMedicationId());
        PatientPrescriptionItem patientPrescriptionItem = patientPrescriptionMapper.toPatientPrescriptionItem(request);
        return paPreItemRepository.save(patientPrescriptionItem);
    }
}
