package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request.AnonymousPostCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response.AnonymousPostResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnonymousPostMapper {
    @Mapping(source = "id", target = "anonymousPostId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "comments", target = "comments")
    AnonymousPostResponse toAnonymousPostResponse(AnonymousPost anonymousPost);
    AnonymousPost toAnonymousPost(AnonymousPostCreationRequest anonymousPostCreationRequest);
    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "anonymousPost.id", target = "anonymousPostId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    CommentResponse toCommentResponse(Comment comment);

}
