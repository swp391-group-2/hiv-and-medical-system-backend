package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request.AnonymousPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response.AnonymousPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.repository.AnonymousPostRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.AnonymousPostMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnonymousPostService {
    AnonymousPostRepository anonymousPostRepository;
    AnonymousPostMapper anonymousPostMapper;
    PatientService patientService;

    public AnonymousPostResponse createAnonymousPost(AnonymousPostCreationRequest request){
        AnonymousPost anonymousPost = anonymousPostMapper.toAnonymousPost(request);
        Patient patient = patientService.getPatientById(request.getPatientId());
        anonymousPost.setPatient(patient);
        return anonymousPostMapper.toAnonymousPostResponse(anonymousPostRepository.save(anonymousPost));
    }

    public AnonymousPost getAnonymousPostById(int anonymousPostId){
        return anonymousPostRepository.findById(anonymousPostId).orElseThrow(() -> new AppException(ErrorCode.ANONYMOUS_POST_NOT_EXISTED));
    }

    public List<AnonymousPostResponse> getAllAnonymousPosts() {
        return anonymousPostRepository.findAll().stream()
                .map(anonymousPostMapper::toAnonymousPostResponse)
                .collect(Collectors.toList());
    }


}
