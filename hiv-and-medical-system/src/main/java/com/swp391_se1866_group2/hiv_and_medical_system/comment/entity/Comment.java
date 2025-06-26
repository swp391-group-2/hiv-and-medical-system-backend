package com.swp391_se1866_group2.hiv_and_medical_system.comment.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.anonymouspost.entity.AnonymousPost;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String content;

    @CreationTimestamp
    LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anonymous_post_id", nullable = false)
    AnonymousPost anonymousPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    Doctor doctor;


}
