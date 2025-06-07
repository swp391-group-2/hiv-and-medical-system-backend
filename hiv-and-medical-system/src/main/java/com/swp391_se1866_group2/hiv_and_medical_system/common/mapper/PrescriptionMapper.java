package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface PrescriptionMapper {
    @Mapping(source = "id", target = "prescriptionId")
    PrescriptionResponse toPrescriptionResponse(Prescription prescription);
    Prescription toPrescription(PrescriptionRequest request);
    void updatePrescription(@MappingTarget Prescription prescription, PrescriptionUpdateRequest request);
}
