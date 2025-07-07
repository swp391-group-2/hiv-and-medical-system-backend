package com.swp391_se1866_group2.hiv_and_medical_system.ticket.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TicketType;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    int count;
    @Enumerated(EnumType.STRING)
    TicketType ticketType;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonManagedReference
    Patient patient;
}
