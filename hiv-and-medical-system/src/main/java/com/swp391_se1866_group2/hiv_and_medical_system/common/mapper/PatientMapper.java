package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    @Mapping(source = "id", target = "patientId" )
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.createdAt", target = "createdAt")
    @Mapping(source = "user.status", target = "userStatus")
    @Mapping(source = "user.code", target = "patientCode")
    PatientResponse toPatientResponse(Patient patient);

    @Mapping(source = "fullName", target = "user.fullName")
    void updatePatientAndUser(PatientUpdateRequest request, @MappingTarget Patient patient);

}
