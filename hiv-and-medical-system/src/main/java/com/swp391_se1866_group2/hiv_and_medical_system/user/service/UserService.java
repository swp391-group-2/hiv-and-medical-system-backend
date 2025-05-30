package com.swp391_se1866_group2.hiv_and_medical_system.user.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.security.repository.RoleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.security.service.RoleService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser (UserCreationRequest request, String role){
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setRole(roleService.getOrCreateRole(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public User findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }



}
