package com.swp391_se1866_group2.hiv_and_medical_system.manager.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ManagerMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.manager.dto.response.ManagerResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerService {
    UserService userService;
    ManagerMapper managerMapper;
    UserRepository userRepository;
    UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public ManagerResponse createManager(UserCreationRequest request) {
        UserResponse userResponse = userService.createUser(request, Role.MANAGER.name());
        return managerMapper.toManagerResponse(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ManagerResponse getManager(String managerId) {
        User user = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOT_EXISTED.getMessage()));
        if (!Role.MANAGER.name().equals(user.getRole())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        UserResponse userResponse = userMapper.toUserResponse(user);
        return managerMapper.toManagerResponse(userResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ManagerResponse> getAllManagers() {
        return userRepository.findAll().stream()
                .filter(user -> Role.MANAGER.name().equals(user.getRole()))
                .map(userMapper::toUserResponse)
                .map(managerMapper::toManagerResponse)
                .collect(Collectors.toList());
    }


}
