package com.swp391_se1866_group2.hiv_and_medical_system.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
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
    Long price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ServiceType serviceType;
    @OneToOne(mappedBy = "service", fetch = FetchType.LAZY)
    @JsonIgnore
    private LabTest labTest;
}
