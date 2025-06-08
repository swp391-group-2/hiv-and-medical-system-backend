package com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionItemMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.entity.PrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.repository.PrescriptionItemRepository;
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
public class PrescriptionItemService {
    PrescriptionItemMapper prescriptionItemMapper;
    PrescriptionItemRepository prescriptionItemRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionItemResponse createPrescriptionItem(PrescriptionItemRequest request) {
        if (prescriptionItemRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRESCRIPTION_ITEM_EXISTED);
        }

        PrescriptionItem prescriptionItem = prescriptionItemMapper.toPrescriptionItem(request);
        Prescription prescription = new Prescription();
        prescriptionItem.setPrescription(prescription);
        return prescriptionItemMapper.toPrescriptionItemResponse(prescriptionItemRepository.save(prescriptionItem));
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<PrescriptionItemResponse> getAllPrescriptionItems() {
        return prescriptionItemRepository.findAll().stream()
                .map(prescriptionItemMapper::toPrescriptionItemResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public PrescriptionItemResponse getPrescriptionItem(int prescriptionItemId) {
        PrescriptionItem prescriptionItem = prescriptionItemRepository.findById(prescriptionItemId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));
        return prescriptionItemMapper.toPrescriptionItemResponse(prescriptionItem);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionItemResponse updatePrescriptionItem(int prescriptionItemId, PrescriptionItemUpdateRequest request) {
        PrescriptionItem prescriptionItem = prescriptionItemRepository.findById(prescriptionItemId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_ITEM_NOT_EXISTED));

        prescriptionItemMapper.updatePrescriptionItem(prescriptionItem, request);
        return prescriptionItemMapper.toPrescriptionItemResponse(prescriptionItemRepository.save(prescriptionItem));
    }
}
