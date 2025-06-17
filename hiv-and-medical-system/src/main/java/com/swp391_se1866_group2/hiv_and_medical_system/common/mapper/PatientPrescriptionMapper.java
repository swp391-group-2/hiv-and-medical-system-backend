package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionItemCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientPrescriptionMapper {
    PatientPrescriptionItem toPatientPrescriptionItem(PaPrescriptionItemCreation request);
    PaPrescriptionItemResponse toPaPrescriptionItemResponse (PatientPrescriptionItem patientPrescription);
}
