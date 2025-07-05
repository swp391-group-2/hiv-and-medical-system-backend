package com.swp391_se1866_group2.hiv_and_medical_system.comment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service.AnonymousPostService;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request.CommentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.repository.CommentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.Role;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.CommentMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.security.service.AuthenticationService;
import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import com.swp391_se1866_group2.hiv_and_medical_system.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    UserService userService;

    public CommentResponse createComment(CommentCreationRequest request){
        Comment comment = commentMapper.toComment(request);
        AnonymousPost anonymousPost = anonymousPostService.getAnonymousPostById(request.getAnonymousPostId());
        comment.setAnonymousPost(anonymousPost);

        User user = userService.getUserResponseByToken();
        String role = user.getRole();

        if(role.equals("DOCTOR")){
            Doctor doctor = doctorService.getDoctorResponseByToken();
            comment.setDoctor(doctor);
        }

        if (role.equals("PATIENT")){
            Patient patient = patientService.getPatientResponseByToken();
            if (!anonymousPost.getPatient().getId().equals(patient.getId())){
                throw new AppException(ErrorCode.PATIENT_NOT_POST_OWNER);
            }
            comment.setPatient(patient);
        }

        CommentResponse commentResponse = commentMapper.toCommentResponse(commentRepository.save(comment));

        if (commentResponse.getDoctorId() !=null){
            commentResponse.setDoctorImageUrl(doctorService.getDoctorImageUrl(commentResponse.getDoctorId()));
        }
        return commentResponse;
    }

    public List<CommentResponse> getAllComments(int anonymousPostId, Pageable pageable) {
        Slice<CommentResponse> slice = commentRepository.getAllComments(anonymousPostId,pageable).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        return slice.getContent();
    }

}
