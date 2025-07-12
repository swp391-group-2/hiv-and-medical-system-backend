package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AnonymousPostResponse {
    int anonymousPostId;
    String title;
    String content;
    LocalDateTime createdAt;
    String patientId;
    List<CommentResponse> comments;
}
