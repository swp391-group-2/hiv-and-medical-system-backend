package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response.ManagerResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {
    @Mapping(source = "code", target = "managerCode")
    @Mapping(source = "id", target = "managerId")
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "userStatus")
    @Mapping(source = "role", target = "role")
    ManagerResponse toManagerResponse(UserResponse userResponse);
}
