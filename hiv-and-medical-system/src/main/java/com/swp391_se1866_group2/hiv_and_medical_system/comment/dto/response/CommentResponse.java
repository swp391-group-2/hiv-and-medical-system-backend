package com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CommentResponse {
    int commentId;
    int anonymousPostId;
    String doctorId;
    String content;
    LocalDate createdAt;

}
