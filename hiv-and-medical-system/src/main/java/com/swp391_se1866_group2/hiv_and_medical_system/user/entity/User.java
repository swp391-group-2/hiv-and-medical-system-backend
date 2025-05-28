package com.swp391_se1866_group2.hiv_and_medical_system.user.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.security.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    @ManyToMany
    Set<Role> roles;
}
