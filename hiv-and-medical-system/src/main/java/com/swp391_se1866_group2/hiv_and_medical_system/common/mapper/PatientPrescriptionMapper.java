package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.request.PaPrescriptionItemCreation;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionItemResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.dto.response.PaPrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescriptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {PrescriptionMapper.class, MedicationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientPrescriptionMapper {
    PatientPrescriptionItem toPatientPrescriptionItem(PaPrescriptionItemCreation request);
    PaPrescriptionItemResponse toPaPrescriptionItemResponse (PatientPrescriptionItem patientPrescription);
    PatientPrescription toPatientPrescription (PaPrescriptionCreation request);
    @Mapping(source = "prescriptionDefault.id", target = "prescriptionDefaultId")
    @Mapping(source = "prescriptionDefault.name", target = "prescriptionDefaultName")
    PaPrescriptionResponse toPaPrescriptionResponse (PatientPrescription patientPrescription);
}
