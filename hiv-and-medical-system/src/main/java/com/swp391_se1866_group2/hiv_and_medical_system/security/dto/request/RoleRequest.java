package com.swp391_se1866_group2.hiv_and_medical_system.security.dto.request;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Permission;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    Set<String> permissions;
}
