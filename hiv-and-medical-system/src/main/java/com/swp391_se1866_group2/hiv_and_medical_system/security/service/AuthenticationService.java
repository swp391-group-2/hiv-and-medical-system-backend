package com.swp391_se1866_group2.hiv_and_medical_system.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AuthProvider;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.UserStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.DoctorMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.UserMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.AuthenticationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.IntrospectRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request.RefreshRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.security.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.user.dto.request.UserCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.repository.UserRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
    UserRepository userRepository;

    DoctorMapper doctorMapper;
    UserMapper userMapper;
    private final PatientRepository patientRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;
    @NonFinal
    @Value("${google.oauth2.client-id}")
    private String clientId;
    @NonFinal
    @Value("${google.oauth2.client-secret}")
    private String clientSecret;
    @NonFinal
    @Value("${google.oauth2.redirect-uri}")
    private String redirectUri;


    public PatientResponse createPatientAccount (UserCreationRequest request, String role){
        User user = userRepository.getUserByEmail(request.getEmail());
        if(user != null){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        return patientService.createPatient(request, role);
    }

    public AuthenticationResponse outboundAuthenticate(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("code", code);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("redirect_uri", redirectUri);
        form.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        ResponseEntity<ExchangeTokenResponse> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                request,
                ExchangeTokenResponse.class
        );
        ExchangeTokenResponse tokenResponse = response.getBody();

        if (tokenResponse == null) {
            throw new RuntimeException("Failed to exchange token");
        }

        headers.setBearerAuth(tokenResponse.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<GoogleUserInfo> userInfoResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET,
                entity,
                GoogleUserInfo.class
        );

        GoogleUserInfo googleUser = userInfoResponse.getBody();

        if (googleUser == null || googleUser.getEmail() == null) {
            throw new AppException(ErrorCode.GET_GOOGLE_EMAIL_FAILED);
        }
        Optional<User> optionalUser = userRepository.findByEmail(googleUser.getEmail());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.getAuthProvider() == null) {
                user.setAuthProvider(AuthProvider.GOOGLE);
                userRepository.save(user);
            }

            if (patientRepository.findPatientByUserEmail(user.getEmail()).isEmpty()) {
                Patient patient = new Patient();
                patient.setUser(user);
                patientRepository.save(patient);
            }
        } else {
            User newUser = User.builder()
                    .email(googleUser.getEmail())
                    .fullName(googleUser.getName())
                    .status(UserStatus.ACTIVE.name())
                    .role(Role.PATIENT.name())
                    .authProvider(AuthProvider.GOOGLE)
                    .build();
            Patient patient = new Patient();
            patient.setUser(newUser);
            patientRepository.save(patient);
            user = newUser;
        }

        return AuthenticationResponse.builder()
                .accessToken(generateToken(user))
                .user(userMapper.toUserResponse(user))
                .refreshToken(generateRefreshToken(user))
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userService.findUserByEmail(request.getEmail());
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var accessToken = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        AuthenticationResponse authen = AuthenticationResponse
                .builder()
                .user(userMapper.toUserResponse(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authenticated(true)
                .build();
        return authen;
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet
                .Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issuer("medcarehiv.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .jwtID(UUID.randomUUID().toString())
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

    private String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .issuer("medcarehiv.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot create token", e);
        }

        return jwsObject.serialize();
    }


    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getRefreshToken());
        var email = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var accessToken = generateToken(user);
        return RefreshResponse.builder().accessToken(accessToken).build();
    }

}
