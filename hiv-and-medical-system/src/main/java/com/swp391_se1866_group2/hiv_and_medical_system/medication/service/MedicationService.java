package com.swp391_se1866_group2.hiv_and_medical_system.medication.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.MedicationMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class MedicationService {
    MedicationMapper medicationMapper;
    MedicationRepository medicationRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public MedicationResponse createMedication(MedicationRequest request) {
        if(medicationRepository.existsByNameAndStrength(request.getName(), request.getStrength())){
            throw new AppException(ErrorCode.MEDICATION_EXISTED);
        }

        Medication medication = medicationMapper.toMedication(request);
        return medicationMapper.toMedicationResponse((medicationRepository.saveAndFlush(medication)));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('PATIENT') or hasRole('LAB_TECHNICIAN')")
    public MedicationResponse getMedication(int medicationId) {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new AppException(ErrorCode.MEDICATION_NOT_EXISTED));
        return medicationMapper.toMedicationResponse(medication);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('PATIENT') or hasRole('LAB_TECHNICIAN')")
    public List <MedicationResponse> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(medicationMapper::toMedicationResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public MedicationResponse updateMedication(int medicationId, MedicationUpdateRequest request) {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new AppException(ErrorCode.MEDICATION_NOT_EXISTED));
        if (medicationRepository.existsByNameAndStrengthAndIdNot(request.getName(), request.getStrength(), medicationId)){
            throw new AppException(ErrorCode.MEDICATION_EXISTED);
        }

        medicationMapper.updateMedication(medication, request);
        return medicationMapper.toMedicationResponse(medicationRepository.save(medication));
    }


}
