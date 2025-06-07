package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.request.MedicationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.dto.response.MedicationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.medication.entity.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MedicationMapper {
    @Mapping(source = "id", target = "medicationId")
    MedicationResponse toMedicationResponse(Medication medication);
    Medication toMedication(MedicationRequest medicationRequest);

}
