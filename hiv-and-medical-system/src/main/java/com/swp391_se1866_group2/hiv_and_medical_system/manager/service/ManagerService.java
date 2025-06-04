package com.swp391_se1866_group2.hiv_and_medical_system.manager.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ManagerMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response.ManagerResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class ManagerService {
    UserService userService;
    ManagerMapper managerMapper;


    @PreAuthorize("hasRole('ADMIN')")
    public ManagerResponse createManager(UserCreationRequest request) {
        UserResponse userResponse= userService.createUser(request, "MANAGER");
        return managerMapper.toManagerResponse(userResponse);
    }


}
