package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request.AnonymousPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response.AnonymousPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.repository.AnonymousPostRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.AnonymousPostMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.dto.response.PatientResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    DoctorService doctorService;
    PatientRepository patientRepository;

    public AnonymousPostResponse createAnonymousPost(AnonymousPostCreationRequest request){
        Patient patient = patientService.getPatientResponseByToken();
        AnonymousPost anonymousPost = anonymousPostMapper.toAnonymousPost(request);
        anonymousPost.setPatient(patient);
        return anonymousPostMapper.toAnonymousPostResponse(anonymousPostRepository.save(anonymousPost));
    }

    public AnonymousPost getAnonymousPostById(int anonymousPostId){
        return anonymousPostRepository.findById(anonymousPostId).orElseThrow(() -> new AppException(ErrorCode.ANONYMOUS_POST_NOT_EXISTED));
    }

    public List<AnonymousPostResponse> getAllMyAnonymousPosts(String title, Pageable pageable) {
        String patientId = patientService.getPatientResponseByToken().getId();
        Slice<AnonymousPostResponse> slice = anonymousPostRepository.getAllMyAnonymousPosts(patientId, title, pageable)
                .orElseThrow(() -> new AppException(ErrorCode.ANONYMOUS_POST_NOT_EXISTED));
        return slice.getContent();
    }

    public List<AnonymousPostResponse> getAllAnonymousPosts(Pageable pageable, String title) {
        title = '%' + title.trim() + '%';
        List<AnonymousPostResponse> anonymousPostResponseList = new ArrayList<>();
        Page<AnonymousPost> anonymousPosts = anonymousPostRepository.getAll(pageable, title);
        if( anonymousPosts == null){
            return anonymousPostResponseList;
        }
        anonymousPosts.forEach(anonymousPost -> {

            AnonymousPostResponse anonymousPostResponse = anonymousPostMapper.toAnonymousPostResponse(anonymousPost);
            anonymousPostResponse.getComments().forEach(commentResponse -> {
                if (commentResponse.getDoctorId() != null){
                    DoctorResponse doctorResponse = doctorService.getDoctorResponseById(commentResponse.getDoctorId());
                    commentResponse.setDoctorName(doctorResponse.getFullName());
                    commentResponse.setDoctorImageUrl(doctorService.getDoctorImageUrl(commentResponse.getDoctorId()));
                }
            });

            anonymousPostResponseList.add(anonymousPostResponse);
        });

        return anonymousPostResponseList;
    }


}
