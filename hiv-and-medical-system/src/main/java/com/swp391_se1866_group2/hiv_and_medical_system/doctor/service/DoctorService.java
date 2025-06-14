package com.swp391_se1866_group2.hiv_and_medical_system.doctor.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.DoctorMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.request.DoctorUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.repository.DoctorRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public DoctorResponse createDoctorAccount (DoctorCreationRequest request){
        if(userService.isEmailExisted(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED) ;
        }
        System.out.println(request);
        Doctor doctor = doctorMapper.toDoctor(request) ;
        User user = new User();
        user.setRole(Role.DOCTOR.name());
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

    public DoctorResponse getDoctorResponseById(String id){
        return doctorMapper.toDoctorResponse(doctorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public DoctorResponse getDoctorProfileByToken(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return doctorRepository.findDoctorByToken(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }

    public Doctor getDoctorById(String id){
        return doctorRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public DoctorResponse getDoctorByEmail(String email){
        return doctorRepository.findDoctorByUserEmail(email).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED));
    }


    public DoctorResponse updatePatientProfile(String doctorId , DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException(ErrorCode.DOCTOR_NOT_EXISTED.getMessage()));
        doctorMapper.updateDoctor(request, doctor);
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    public List<DoctorResponse> getTopDoctorsForHome(){
        return new ArrayList<>(doctorRepository.getTopDoctor(PageRequest.of(0, 4)).orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_EXISTED)));
    }



}
