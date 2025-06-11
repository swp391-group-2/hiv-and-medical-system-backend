package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.DoctorMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PatientMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.AuthenticationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.AuthenticationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response.UserResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    DoctorService doctorService;
    PatientService patientService;
    UserService userService;

    DoctorMapper doctorMapper;
    UserMapper userMapper;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;
    public PatientResponse createPatientAccount (UserCreationRequest request, String role){
        return patientService.createPatient(request, role);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findUserByEmail(request.getEmail());
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        AuthenticationResponse authen = AuthenticationResponse
                .builder()
                .token(token)
                .authenticated(true)
                .build();
        if(user.getRole().equals(Role.PATIENT.name())){
            PatientResponse patient = patientService.getPatientByEmail(user.getEmail());
            authen.setUser(patient);
        }else if(user.getRole().equals(Role.DOCTOR.name())){
            DoctorResponse doctor = doctorService.getDoctorByEmail(user.getEmail());
            authen.setUser(doctor);
        }
        else{
            authen.setUser(userMapper.toUserResponse(user));
        }
        return authen;
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issuer("medcarehiv.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .claim("scope", "ROLE_" + user.getRole())
                .build();
        Payload payload =  new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try{
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
        }catch (JOSEException e){
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

}
