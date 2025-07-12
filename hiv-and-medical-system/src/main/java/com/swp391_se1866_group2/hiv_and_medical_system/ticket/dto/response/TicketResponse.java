package com.swp391_se1866_group2.hiv_and_medical_system.ticket.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TicketType;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    int id;
    int count;
    TicketType ticketType;
    String patientId;
    String serviceName;
    String imageUrl;
}
