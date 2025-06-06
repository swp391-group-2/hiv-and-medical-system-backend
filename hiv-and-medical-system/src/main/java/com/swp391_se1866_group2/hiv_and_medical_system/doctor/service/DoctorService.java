package com.swp391_se1866_group2.hiv_and_medical_system.doctor.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.DoctorMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository.DoctorRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {
    DoctorRepository doctorRepository;
    UserService userService;
    DoctorMapper doctorMapper;

    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public DoctorResponse createDoctorAccount (DoctorCreationRequest request, String role){
        if(userService.isEmailExisted(request.getEmail())){
            throw new RuntimeException(ErrorCode.EMAIL_EXISTED.getMessage());
        }
        System.out.println(request);
        Doctor doctor = doctorMapper.toDoctor(request) ;
        User user = new User();
        user.setRole(role);
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE.name());
        doctor.setUser(user);
        System.out.println(doctor);
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getAllDoctor (){
        return doctorRepository.findAll().stream().map(doctor -> doctorMapper.toDoctorResponse(doctor)).collect(Collectors.toList());
    }

    public DoctorResponse getDoctorById(String id){
        return doctorMapper.toDoctorResponse(doctorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }


}
