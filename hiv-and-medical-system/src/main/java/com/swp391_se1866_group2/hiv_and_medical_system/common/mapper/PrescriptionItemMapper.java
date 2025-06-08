package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;


import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.request.PrescriptionUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.request.PrescriptionItemUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.dto.response.PrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescriptionitem.entity.PrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface PrescriptionItemMapper {
    @Mapping(source = "id", target = "prescriptionItemId")
    @Mapping(source = "prescription.id", target = "prescriptionId")
    PrescriptionItemResponse toPrescriptionItemResponse(PrescriptionItem prescriptionItem);
    PrescriptionItem toPrescriptionItem(PrescriptionItemRequest request);
    void updatePrescriptionItem(@MappingTarget PrescriptionItem prescriptionItem, PrescriptionItemUpdateRequest request);
}
