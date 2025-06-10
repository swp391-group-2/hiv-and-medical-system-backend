package com.swp391_se1866_group2.hiv_and_medical_system.prescription.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.repository.MedicationRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionItemCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.PrescriptionItem;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionItemRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PrescriptionService {
    PrescriptionMapper prescriptionMapper;
    PrescriptionRepository prescriptionRepository;
    PrescriptionItemRepository prescriptionItemRepository;
    MedicationRepository medicationRepository;

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse createPrescription(PrescriptionCreationRequest request){
        if (prescriptionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRESCRIPTION_EXISTED);
        }

        Prescription prescription = prescriptionMapper.toPrescription(request);
        prescription.setPrescriptionDate(LocalDate.now());
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        List<PrescriptionItem> prescriptionItems = new ArrayList<>();
        for (PrescriptionItemCreationRequest itemRequest : request.getPrescriptionItems()) {
            Medication medication = medicationRepository.findById(itemRequest.getMedicationId())
                    .orElseThrow(() -> new AppException(ErrorCode.MEDICATION_NOT_EXISTED));

            PrescriptionItem item = new PrescriptionItem();
            item.setPrescription(savedPrescription);
            item.setDosage(itemRequest.getDosage());
            item.setFrequency(itemRequest.getFrequency());
            item.setDuration(itemRequest.getDuration());
            item.setMedication(medication);
            prescriptionItems.add(item);

        }

        prescriptionItemRepository.saveAll(prescriptionItems);
        savedPrescription.setPrescriptionItems(prescriptionItems);
        return prescriptionMapper.toPrescriptionResponse(savedPrescription);

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<PrescriptionResponse> getAllPrescriptions(){
        return prescriptionRepository.findAll().stream()
                .map(prescriptionMapper::toPrescriptionResponse)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('PATIENT') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<PrescriptionResponse> getPrescriptionByName(String prescriptionName) {
        List<Prescription> prescriptions = prescriptionRepository.findAllByNameContainingIgnoreCase(prescriptionName);
        if (prescriptions.isEmpty()) {
            throw new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED);
        }
        return prescriptions.stream().
                map(prescriptionMapper::toPrescriptionResponse)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionItemResponse updatePrescription(int prescriptionId, int prescriptionItemId, PrescriptionItemUpdateRequest request) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));

        PrescriptionItem prescriptionItem = prescription.getPrescriptionItems().stream()
                .filter(item -> item.getId() == prescriptionItemId)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_ITEM_NOT_EXISTED));

        prescriptionMapper.updatePrescription(prescriptionItem, request);
        prescriptionRepository.save(prescription);
        return prescriptionMapper.toPrescriptionItemResponse(prescriptionItem);
    }



}
