package com.swp391_se1866_group2.hiv_and_medical_system.patient.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    LocalDate dob;
    String gender;
    String address;
    String phoneNumber;
    String identificationCard;
    String healthInsurance;
    String occupation;
    @UpdateTimestamp
    LocalDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false, unique = true)
    User user;
}
