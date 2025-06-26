package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;

import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request.CommentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "anonymousPost.id", target = "anonymousPostId")
    CommentResponse toCommentResponse(Comment comment);
    Comment toComment(CommentCreationRequest commentCreationRequest);
}
