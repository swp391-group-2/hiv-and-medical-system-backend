package com.swp391_se1866_group2.hiv_and_medical_system.ticket.repository;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TicketType;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    Ticket findTicketByPatientIdAndTicketType(String id, TicketType type);
}
