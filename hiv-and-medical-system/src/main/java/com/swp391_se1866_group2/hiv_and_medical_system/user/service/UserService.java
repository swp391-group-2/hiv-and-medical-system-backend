package com.swp391_se1866_group2.hiv_and_medical_system.user.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UpdateStatusRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UpdateUserRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser (UserCreationRequest request, String role){
        if(isEmailExisted(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user =  userMapper.toUser(request);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE.name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers(Role role) {
        if (role == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        String roleName = role.name();
        return userRepository.findByRole(roleName).stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public User getUserResponseByToken(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }


    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public boolean isEmailExisted(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean updateUserStatus(String userId, UpdateStatusRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(request.isActive()){
            user.setStatus(UserStatus.ACTIVE.name());
        }else{
            user.setStatus(UserStatus.INACTIVE.name());
        }

        userRepository.save(user);
        return true;
    }

    public List<UserResponse> getAllUser (){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public boolean updateUser(String userId, UpdateUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        user.setFullName(request.getFullName());

        userRepository.save(user);
        return true;
    }

}
