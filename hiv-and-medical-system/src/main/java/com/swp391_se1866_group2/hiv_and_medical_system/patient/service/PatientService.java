package com.swp391_se1866_group2.hiv_and_medical_system.patient.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.request.PatientUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientService {
    PasswordEncoder passwordEncoder;
    PatientRepository patientRepository;
    PatientMapper patientMapper;
    UserService userService;
    UserMapper userMapper;
    private final UserRepository userRepository;

//    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN') or hasRole('STAFF') or hasRole('DOCTOR')" )
    public PatientResponse getPatient(String patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException(ErrorCode.PATIENT_NOT_EXISTED.getMessage()));
        return patientMapper.toPatientResponse(patient);
    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('DOCTOR')")
    public List<PatientResponse> getAllPatients(){
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse createPatient(UserCreationRequest request, String role) {
        Patient patient = new Patient();
        User user = userMapper.toUser(request);
        if(userService.isEmailExisted(user.getEmail())) {
            throw new RuntimeException(ErrorCode.EMAIL_EXISTED.getMessage());
        }
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE.name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        patient.setUser(user);
        return patientMapper.toPatientResponse(patientRepository.save(patient));
    }
//    @PreAuthorize("hasRole('PATIENT') or hasRole('ADMIN')")
    public PatientResponse updatePatientProfile(String patientId , PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException(ErrorCode.PATIENT_NOT_EXISTED.getMessage()));
//        if(patient.getUser().getCode() == null || patient.getUser().getCode().isEmpty()) {
//            User user = userRepository.findById(patient.getUser().getId())
//                    .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOT_EXISTED.getMessage()));
//            Long genCode = patientRepository.getNextPatientCodeSequence();
//            String patientCode = String.format("PA%6d", genCode);
//            user.setCode(patientCode);
//            userRepository.save(user);
//        }
        patientMapper.updatePatientAndUser(request, patient);
        patient.setUpdatedProfile(isUpdateProfile(patient));
        return patientMapper.toPatientResponse(patientRepository.save(patient));
    }

    private boolean isUpdateProfile(Patient patient) {
        return patient.getDob() != null && patient.getGender() != null && patient.getAddress() != null && patient.getPhoneNumber() != null && patient.getIdentificationCard() != null && patient.getHealthInsurance() != null && patient.getOccupation() != null &&
                !(patient.getGender().isEmpty() || patient.getAddress().isEmpty() || patient.getPhoneNumber().isEmpty() || patient.getIdentificationCard().isEmpty() || patient.getHealthInsurance().isEmpty() || patient.getOccupation().isEmpty());
    }

    public PatientResponse getPatientProfileByToken () {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return patientRepository.findPatientByUserEmail(email).orElseThrow(() -> new RuntimeException(ErrorCode.PATIENT_NOT_EXISTED.getMessage()));
    }

    public PatientResponse getPatientByEmail(String email){
        return patientRepository.findPatientByUserEmail(email).orElseThrow(() -> new RuntimeException(ErrorCode.PATIENT_NOT_EXISTED.getMessage()));
    }

    public Patient getPatientById (String patientId){
        return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException(ErrorCode.PATIENT_NOT_EXISTED.getMessage()));
    }

}
