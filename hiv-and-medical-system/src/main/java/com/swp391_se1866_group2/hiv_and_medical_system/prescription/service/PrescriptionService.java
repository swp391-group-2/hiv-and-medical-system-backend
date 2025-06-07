package com.swp391_se1866_group2.hiv_and_medical_system.prescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PrescriptionService {
    PrescriptionMapper prescriptionMapper;
    PrescriptionRepository prescriptionRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse createPrescription(PrescriptionRequest request){
        if (prescriptionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.MEDICATION_EXISTED);
        }

        Prescription prescription = prescriptionMapper.toPrescription(request);
        return prescriptionMapper.toPrescriptionResponse(prescriptionRepository.save(prescription));
    }


}
