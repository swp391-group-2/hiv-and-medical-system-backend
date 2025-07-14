package com.swp391_se1866_group2.hiv_and_medical_system.ticket.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.TicketType;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.repository.ServiceRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.service.service.ServiceService;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.dto.response.TicketResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.entity.Ticket;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.repository.TicketRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService {
    PatientRepository patientRepository;
    PatientService patientService;
    TicketRepository ticketRepository;
    ServiceRepository serviceRepository;

    public TicketResponse createTicket(String patientId, TicketType ticketType) {
        ServiceResponse service = serviceRepository.getServiceByServiceType(ServiceType.valueOf(String.valueOf(ticketType)));
        Ticket ticket = ticketRepository.findTicketByPatientIdAndTicketType(patientId, ticketType);
        if (ticket == null) {
            ticket = Ticket.builder()
                    .count(1)
                    .ticketType(ticketType)
                    .patient(patientRepository.findById(patientId).get())
                    .build();
        }else{
            ticket.setCount(ticket.getCount() + 1);
        }
        ticket = ticketRepository.save(ticket);
        return TicketResponse.builder()
                .id(ticket.getId())
                .count(ticket.getCount())
                .ticketType(ticketType)
                .patientId(ticket.getPatient().getId())
                .serviceName(service.getName())
                .price(service.getPrice())
                .imageUrl(service.getImageUrl())
                .build();
    }

    public TicketResponse getTicketResponseByTypeAndPatientId(String patientId, TicketType ticketType) {
        ServiceResponse service = serviceRepository.getServiceByServiceType(ServiceType.valueOf(String.valueOf(ticketType)));
        Ticket ticket = ticketRepository.findTicketByPatientIdAndTicketType(patientId, ticketType);
        if(ticket == null){
            ticket = Ticket.builder()
                    .count(0)
                    .ticketType(ticketType)
                    .patient(patientRepository.findById(patientId).get())
                    .build();
            ticket = ticketRepository.save(ticket);

        }
        return TicketResponse.builder()
                .id(ticket.getId())
                .count(ticket.getCount())
                .ticketType(ticketType)
                .patientId(ticket.getPatient().getId())
                .serviceName(service.getName())
                .price(service.getPrice())
                .imageUrl(service.getImageUrl())
                .build();
    }


    public List<TicketResponse> getListTicketByPatientId(String patientId) {
        List<TicketResponse> ticketResponses = new ArrayList<>();

        ticketResponses.add(getTicketResponseByTypeAndPatientId(patientId,TicketType.SCREENING));
        ticketResponses.add(getTicketResponseByTypeAndPatientId(patientId, TicketType.CONFIRMATORY));
        ticketResponses.add(getTicketResponseByTypeAndPatientId(patientId, TicketType.CONSULTATION));

        return ticketResponses;
    }

    public TicketResponse getTicketByTypeAndPatientToken(TicketType ticketType) {
        Patient patient = patientService.getPatientResponseByToken();
        return getTicketResponseByTypeAndPatientId(patient.getId(), ticketType);
    }


    public List<TicketResponse> getListTicketByPatientToken() {
        Patient patient = patientService.getPatientResponseByToken();
        return getListTicketByPatientId(patient.getId());
    }


    public Ticket getTicketByTypeAndPatientId(String patientId, TicketType ticketType) {
        Ticket ticket = ticketRepository.findTicketByPatientIdAndTicketType(patientId, ticketType);
        if(ticket == null){
            ticket = Ticket.builder()
                    .count(0)
                    .ticketType(ticketType)
                    .patient(patientRepository.findById(patientId).get())
                    .build();
            ticket = ticketRepository.save(ticket);
        }
        return ticket;
    }


}
