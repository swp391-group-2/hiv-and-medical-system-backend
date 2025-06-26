package com.swp391_se1866_group2.hiv_and_medical_system.security.controller;

import  com.nimbusds.jose.JOSEException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.AuthenticationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.IntrospectRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.RefreshRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.AuthenticationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.IntrospectResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.RefreshResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.service.AuthenticationService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ApiResponse<PatientResponse> signup(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<PatientResponse>builder()
                .success(true)
                .data(authenticationService.createPatientAccount(request, Role.PATIENT.name()))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request ){
        return ApiResponse.<AuthenticationResponse>builder()
                .success(true)
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Logged out successfully")
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .success(true)
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<RefreshResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<RefreshResponse>builder()
                .data(result)
                .success(true)
                .build();
    }

    @PostMapping("/outbound/authentication")
    ApiResponse<AuthenticationResponse> outboundAuthenticate(@RequestParam("code") String code){
        var result = authenticationService.outboundAuthenticate(code);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .success(true)
                .build();
    }

}
