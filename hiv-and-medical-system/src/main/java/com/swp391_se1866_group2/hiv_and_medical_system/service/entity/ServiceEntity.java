package com.swp391_se1866_group2.hiv_and_medical_system.service.entity;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    double price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ServiceType serviceType;
}
