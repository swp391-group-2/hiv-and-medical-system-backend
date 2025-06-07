package com.swp391_se1866_group2.hiv_and_medical_system.slot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false, unique = true)
    int slotNumber;
    @Column(nullable = false)
    LocalTime startTime;
    @Column(nullable = false)
    LocalTime endTime;
    @Column(columnDefinition = "NVARCHAR(100)")
    String description;
}
