package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.AuthenticationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.AuthenticationResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
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
                .subject(user.getPhoneNumber())
                .issuer("medcarehiv.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
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

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Role role = user.getRoles();
        if(role != null && StringUtils.hasText(role.getName())) {
            stringJoiner.add("ROLE_" + role.getName());
            if(!CollectionUtils.isEmpty(role.getPermissions())) {
                role.getPermissions().forEach(permission -> {stringJoiner.add(permission.getName());});
            }
        }
        return stringJoiner.toString();
    }

}
