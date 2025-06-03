package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    UserService userService;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;
    public UserResponse createPatientAccount (UserCreationRequest request){
        return userService.createUser(request, Role.PATIENT.name());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findUserByEmail(request.getEmail());
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getEmail())
                .issuer("medcarehiv.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
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
