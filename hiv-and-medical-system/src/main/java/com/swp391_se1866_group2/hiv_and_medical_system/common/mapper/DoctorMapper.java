package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateDTORequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {

    @Named("toDoctorResponse")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user.id" , target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.status", target = "userStatus")
    @Mapping(source = "user.code", target = "doctorCode")
    DoctorResponse toDoctorResponse(Doctor doctor);

    Doctor toDoctor(DoctorCreationRequest request);
    @Mapping(source = "fullName", target = "user.fullName")
    void updateDoctor(DoctorUpdateRequest request, @MappingTarget Doctor doctor);

    @Mapping(source = "fullName", target = "user.fullName")
    void updateDoctorDTO(DoctorUpdateDTORequest request, @MappingTarget Doctor doctor);

}
