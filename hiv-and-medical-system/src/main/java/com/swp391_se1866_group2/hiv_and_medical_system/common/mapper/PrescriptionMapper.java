package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.PrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",uses = {MedicationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface PrescriptionMapper {
    @Mapping(source = "id", target = "prescriptionId")
    @Mapping(source = "prescriptionItems", target = "prescriptionItems")
    @Mapping(source = "dosageForm", target = "dosageForm")
    PrescriptionResponse toPrescriptionResponse(Prescription prescription);

    @Mapping(source = "id", target = "prescriptionItemId")
    @Mapping(source = "medication", target = "medication")
    PrescriptionItemResponse toPrescriptionItemResponse(PrescriptionItem prescriptionItem);

    Prescription toPrescription(PrescriptionCreationRequest request);
    @Mapping(target = "prescriptionItems", ignore = true)
    void updatePrescription(PrescriptionUpdateRequest request, @MappingTarget Prescription prescription);

    Prescription toPrescriptionByPUpdate(PrescriptionUpdateRequest request);
}
