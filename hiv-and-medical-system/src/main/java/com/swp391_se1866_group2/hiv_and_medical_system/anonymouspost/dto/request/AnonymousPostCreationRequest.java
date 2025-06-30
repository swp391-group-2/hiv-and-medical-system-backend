package com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.dto.request;

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
public class AnonymousPostCreationRequest {
    String nickName;
    String gender;
    int age;
    String title;
    String content;
}
