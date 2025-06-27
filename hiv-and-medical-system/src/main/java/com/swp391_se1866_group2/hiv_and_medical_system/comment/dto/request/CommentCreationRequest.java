package com.swp391_se1866_group2.hiv_and_medical_system.comment.dto.request;

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
public class CommentCreationRequest {
    String content;
    String doctorId;
    String patientId;
    int anonymousPostId;
}
