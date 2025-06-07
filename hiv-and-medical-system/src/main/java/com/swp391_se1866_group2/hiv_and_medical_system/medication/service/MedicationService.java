package com.swp391_se1866_group2.hiv_and_medical_system.medication.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.MedicationMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class MedicationService {
    MedicationMapper medicationMapper;
    MedicationRepository medicationRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public MedicationResponse createMedication(MedicationRequest request) {
        Medication medication = medicationMapper.toMedication(request);
        return medicationMapper.toMedicationResponse((medicationRepository.save(medication)));
    }
}
