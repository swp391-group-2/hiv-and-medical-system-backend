package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String author;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String snippet;

    @Column(nullable = false, columnDefinition = "TEXT")
    String content;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;


}
