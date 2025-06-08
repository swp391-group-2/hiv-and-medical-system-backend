package com.swp391_se1866_group2.hiv_and_medical_system.prescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
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
public class PrescriptionService {
    PrescriptionMapper prescriptionMapper;
    PrescriptionRepository prescriptionRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse createPrescription(PrescriptionRequest request){
        if (prescriptionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRESCRIPTION_EXISTED);
        }

        Prescription prescription = prescriptionMapper.toPrescription(request);
        return prescriptionMapper.toPrescriptionResponse(prescriptionRepository.save(prescription));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public PrescriptionResponse getPrescription(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));
        return prescriptionMapper.toPrescriptionResponse(prescription);
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List <PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(prescriptionMapper::toPrescriptionResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse updatePrescription(String prescriptionId, PrescriptionUpdateRequest request) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));

        prescriptionMapper.updatePrescription(prescription, request);
        return prescriptionMapper.toPrescriptionResponse(prescriptionRepository.save(prescription));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public void deletePrescription(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));
        prescriptionRepository.deleteById(prescriptionId);
    }


}
