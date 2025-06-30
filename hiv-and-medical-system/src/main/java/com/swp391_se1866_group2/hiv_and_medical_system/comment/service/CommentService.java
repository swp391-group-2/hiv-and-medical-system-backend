package com.swp391_se1866_group2.hiv_and_medical_system.comment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service.AnonymousPostService;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request.CommentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.repository.CommentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.CommentMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
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
public class CommentService {
    CommentMapper commentMapper;
    CommentRepository commentRepository;
    DoctorService doctorService;
    AnonymousPostService anonymousPostService;
    PatientService patientService;

    public CommentResponse createComment(CommentCreationRequest request){
        Comment comment = commentMapper.toComment(request);
        AnonymousPost anonymousPost = anonymousPostService.getAnonymousPostById(request.getAnonymousPostId());
        comment.setAnonymousPost(anonymousPost);

        if(request.getDoctorId() !=null){
            Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
            comment.setDoctor(doctor);
        }

        if (request.getDoctorId() == null){
            Patient patient = patientService.getPatientResponseByToken();
            if (!anonymousPost.getPatient().getId().equals(patient.getId())){
                throw new AppException(ErrorCode.PATIENT_NOT_POST_OWNER);
            }
            comment.setPatient(patient);
        }

        CommentResponse commentResponse = commentMapper.toCommentResponse(commentRepository.save(comment));
        commentResponse.setDoctorImageUrl(doctorService.getDoctorImageUrl(request.getDoctorId()));
        return commentResponse;
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

}
