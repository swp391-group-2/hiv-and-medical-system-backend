package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.staff.dto.response.StaffResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface StaffMapper {
    @Mapping(source = "code", target = "staffCode")
    @Mapping(source = "id", target = "staffId")
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "userStatus")
    @Mapping(source = "role", target = "role")
    StaffResponse toStaffResponse(UserResponse userResponse);

}
