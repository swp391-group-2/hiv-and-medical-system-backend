package com.swp391_se1866_group2.hiv_and_medical_system.blog.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BlogResponse {
    int blogId;
    String author;
    String title;
    String snippet;
    String content;
}
