package com.swp391_se1866_group2.hiv_and_medical_system.image.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String url;
    boolean isActive;
    @ManyToOne
    @JsonIgnore
    Doctor doctor;
}
