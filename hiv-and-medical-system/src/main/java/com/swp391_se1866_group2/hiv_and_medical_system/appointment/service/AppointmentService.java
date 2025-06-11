package com.swp391_se1866_group2.hiv_and_medical_system.appointment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppoimentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabTestStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.AppointmentMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service.ScheduleSlotService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service.LabTestSlotService;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.service.ServiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    LabTestSlotService labTestSlotService;
    ScheduleSlotService scheduleSlotService;
    PatientService patientService;
    ServiceService serviceService;

    AppointmentMapper appointmentMapper;

    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse createAppointment(AppointmentCreationRequest request) {
        Patient patient = patientService.getPatientById(request.getPatientId());
        ServiceEntity service = serviceService.getServiceEntityById(request.getServiceId());
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .service(service)
                .build();
        if(service.getServiceType().equals(ServiceType.CONSULTATION)){
            ScheduleSlot scheduleSlot = scheduleSlotService.getScheduleSlotById(request.getScheduleSlotId());
            if(scheduleSlot.getStatus().equals(ScheduleSlotStatus.UNAVAILABLE.name())){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
            scheduleSlot.setStatus(ScheduleSlotStatus.UNAVAILABLE.name());
            appointment.setScheduleSlot(scheduleSlot);
        }else {
            LabTestSlot labTestSlot = labTestSlotService.getLabTestSlotById(request.getLabTestSlotId());
            if(labTestSlot.getBookedCount() >= labTestSlot.getMaxCount()){
                throw new AppException(ErrorCode.LAB_TEST_SLOT_FULL);
            }
            labTestSlot.setBookedCount(labTestSlot.getBookedCount() + 1);
            if(labTestSlot.getBookedCount() == labTestSlot.getMaxCount()){
                labTestSlot.setStatus(LabTestStatus.FULL);
            }
            appointment.setLabTestSlot(labTestSlot);
        }
        appointment.setStatus(AppoimentStatus.SCHEDULED);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return appointmentMapper.toAppointmentResponse(appointmentSaved);
    }

    @PreAuthorize("hasRole('PATIENT')or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public AppointmentResponse getAppointmentById(int id) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        }
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    @PreAuthorize("hasRole('PATIENT')or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment))
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentResponse> getAllAppointmentsByStatus(AppoimentStatus status) {
        return appointmentRepository.findByStatus(status).stream()
                .map(appointment -> appointmentMapper.toAppointmentResponse(appointment))
                .collect(Collectors.toList());
    }


}
