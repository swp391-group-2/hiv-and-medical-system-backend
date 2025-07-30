package com.swp391_se1866_group2.hiv_and_medical_system.security.controller;

import  com.nimbusds.jose.JOSEException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.dto.ApiResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.AuthenticationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.GoogleCodeRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.IntrospectRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.RefreshRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.AuthenticationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.IntrospectResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.RefreshResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.service.AuthenticationService;
import com.swp391_se1866_group2.hiv_and_medical_system.security.service.OtpService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    OtpService otpService;

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
    ApiResponse<AuthenticationResponse> outboundAuthenticate(@RequestBody GoogleCodeRequest code){
        var result = authenticationService.outboundAuthenticate(code.getCode());
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .success(true)
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        try {
            String otp = otpService.generateAndStoreOtp(email);
            otpService.sendOtpEmail(email, otp);
            return ApiResponse.<String>builder()
                    .success(true)
                    .data("OTP đã được gửi đến email, hợp lệ trong 5 phút")
                    .build();

        } catch (Exception ex) {
            return ApiResponse.<String>builder()
                    .success(false)
                    .data("Gửi OTP thất bại: " + ex.getMessage())
                    .build();
        }
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {

        boolean valid = otpService.validateOtp(email, otp);
        authenticationService.resetPassword(email, newPassword, valid);
        return ApiResponse.<String>builder()
                .success(true)
                .data("Reset password successful")
                .build();
    }

}
