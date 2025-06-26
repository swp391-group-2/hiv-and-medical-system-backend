package com.swp391_se1866_group2.hiv_and_medical_system.comment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.service.AnonymousPostService;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request.CommentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.repository.CommentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.CommentMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
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

    public CommentResponse createComment(CommentCreationRequest request){
        Comment comment = commentMapper.toComment(request);
        Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
        AnonymousPost anonymousPost = anonymousPostService.getAnonymousPostById(request.getAnonymousPostId());
        comment.setDoctor(doctor);
        comment.setAnonymousPost(anonymousPost);
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

}
