package com.swp391_se1866_group2.hiv_and_medical_system.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.image.entity.Image;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false)
    LocalDate createdAt;

    @UpdateTimestamp
    LocalDate updatedAt;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Image> image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    Doctor doctor;
}
