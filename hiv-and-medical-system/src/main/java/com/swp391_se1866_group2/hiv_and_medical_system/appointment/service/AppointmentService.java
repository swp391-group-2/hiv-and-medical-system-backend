package com.swp391_se1866_group2.hiv_and_medical_system.appointment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentBlockRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.*;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.*;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.*;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.repository.LabSampleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabResultUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabResultRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestParameterRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service.LabTestService;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.repository.PatientRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.entity.PatientPrescription;
import com.swp391_se1866_group2.hiv_and_medical_system.patientprescription.repository.PatientPrescriptionRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.expire.ScheduleSlotExpireService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service.ScheduleSlotService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.entity.LabTestSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.repository.LabTestSlotRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.laboratory.service.LabTestSlotService;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.service.ServiceService;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.entity.Ticket;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.repository.TicketRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.ticket.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    LabSampleRepository labSampleRepository;
    LabResultRepository labResultRepository;
    LabTestRepository labTestRepository;
    LabTestParameterRepository labTestParameterRepository;
    TicketRepository ticketRepository;
    LabTestSlotRepository labTestSlotRepository;
    ScheduleSlotRepository scheduleSlotRepository;
    LabTestSlotService labTestSlotService;
    ScheduleSlotService scheduleSlotService;
    LabTestService labTestService;
    PatientPrescriptionRepository patientPrescriptionRepository;
    PatientService patientService;
    ServiceService serviceService;
    DoctorService doctorService;
    TicketService ticketService;
    PatientPrescriptionMapper patientPrescriptionMapper;
    PrescriptionMapper prescriptionMapper;
    LabTestMapper labTestMapper;
    LabSampleMapper labSampleMapper;
    AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;

    //    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentCreationResponse createAppointment(AppointmentCreationRequest request) {
        Patient patient = patientService.getPatientById(request.getPatientId());
        ServiceEntity service = serviceService.getServiceEntityById(request.getServiceId());
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .service(service)
                .build();
        if(service.getServiceType().equals(ServiceType.CONSULTATION)){
            ScheduleSlot scheduleSlot = scheduleSlotService.getScheduleSlotById(request.getScheduleSlotId());
            if(!scheduleSlot.getStatus().equals(ScheduleSlotStatus.AVAILABLE)){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
            scheduleSlot.setStatus(ScheduleSlotStatus.UNAVAILABLE);
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
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        Appointment appointmentSaved = appointmentRepository.save(appointment);
        String appointmentCode = String.format("APP%06d", appointmentSaved.getId());
        appointmentRepository.updateAppointmentCode(appointmentSaved.getId(), appointmentCode);
        appointmentSaved.setAppointmentCode(appointmentCode);
        return appointmentMapper.toAppointmentBasicResponse(appointmentSaved);
    }

//    @PreAuthorize("hasRole('PATIENT') or hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public AppointmentLabSampleResponse getAppointmentById(int id) {
        AppointmentLabSampleResponse appointmentLabSampleResponse = appointmentMapper.toAppointmentLabResponse(appointmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED)));
        if(appointmentLabSampleResponse.getLabSampleId() != null){
            LabResult labResult = labResultRepository.findByLabSampleId(appointmentLabSampleResponse.getLabSampleId());
            appointmentLabSampleResponse.setLabResult(labTestMapper.toLabResultResponse(labResult));
        }
        if(appointmentLabSampleResponse.getPatientPrescription() != null){
            PatientPrescription prescription = patientPrescriptionRepository.findById(appointmentLabSampleResponse.getPatientPrescription().getId()).orElse(null);
            appointmentLabSampleResponse.setPatientPrescription(patientPrescriptionMapper.toPaPrescriptionResponse(prescription));
        }
        return appointmentLabSampleResponse;
    }

//    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentLabSampleResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> {
                    AppointmentLabSampleResponse response = appointmentMapper.toAppointmentLabResponse(appointment);
                    if (response.getLabSampleId() != null) {
                        LabResult labResult = labResultRepository.findByLabSampleId(response.getLabSampleId());
                        response.setLabResult(labTestMapper.toLabResultResponse(labResult));
                    }
                    if(response.getPatientPrescription() != null){
                        PatientPrescription prescription = patientPrescriptionRepository.findById(response.getPatientPrescription().getId()).orElse(null);
                        response.setPatientPrescription(patientPrescriptionMapper.toPaPrescriptionResponse(prescription));
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }
//    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentLabSampleResponse> getAllAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(AppointmentStatus.valueOf(status.toUpperCase()))
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED))
                .stream()
                .map(appointment -> {
                    AppointmentLabSampleResponse response = appointmentMapper.toAppointmentLabResponse(appointment);
                    if(response.getLabSampleId() != null){
                        LabResult labResult = labResultRepository.findByLabSampleId(response.getLabSampleId());
                        response.setLabResult(labTestMapper.toLabResultResponse(labResult));
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

//    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public AppointmentLabSampleResponse checkinAppointment(int appointmentId, LabSampleCreationRequest request) {
        Appointment appointment = getAppointmentByAppointmentId(appointmentId);
        if (appointment.getLabSample() != null) {
            throw new AppException(ErrorCode.ALREADY_CHECKED_IN);
        }
        if(appointment.getScheduleSlot() != null &&
                appointment.getService().getServiceType().equals(ServiceType.CONSULTATION)){
            ScheduleSlot scheduleSlot = appointment.getScheduleSlot();
            scheduleSlot.setStatus(ScheduleSlotStatus.CHECKED_IN);
            scheduleSlotRepository.save(scheduleSlot);
        }
        LabSample sample = labSampleMapper.toLabSample(request);
        sample.setStatus(LabSampleStatus.COLLECTED);
        LabTestParameter labTestParameter;
        LabTest labTest = labTestRepository.findByServiceServiceType(appointment.getService().getServiceType());
        labTestParameter = labTestParameterRepository.findByLabTestId(labTest.getId());

        LabResult labResult = new LabResult();

        if(labTestParameter.getParameterType() == ParameterType.NUMERIC){
            labResult.setResultNumericCD4(labResult.getResultNumericCD4());
            labResult.setResultNumericViralLoad(labResult.getResultNumericViralLoad());
            labResult.setResultText(null);
        }
        else {
            labResult.setResultNumericCD4(null);
            labResult.setResultNumericViralLoad(null);
            labResult.setResultText(labResult.getResultText());
        }
        labResult.setLabSample(sample);
        labResult.setLabTestParameter(labTestParameter);
        labResult.setConclusion("");
        labResult.setNote("");
        labResult.setLabSample(sample);
        sample.setLabResults(labResult);
        sample.setAppointment(appointment);
        appointment.setStatus(AppointmentStatus.CHECKED_IN);
        appointment.setLabSample(sample);
        labSampleRepository.save(sample);
        return appointmentMapper.toAppointmentLabResponse(appointmentRepository.save(appointment));
    }

    public LabResultResponse inputLabResultAppointment(int sampleId, LabResultUpdateRequest request) {
        return labTestService.inputLabResult(sampleId, request);
    }

    public boolean isResultReturnAllowed(int appointmentId , boolean status){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        LabResult labResult = labResultRepository.findLabResultByLabSampleId(appointment.getLabSample().getId()).orElseThrow(() -> new AppException(ErrorCode.LAB_RESULT_NOT_EXISTED));
        if(!labResult.getResultStatus().equals(ResultStatus.FINISHED)){
            throw new AppException(ErrorCode.LAB_RESULT_CAN_NOT_ALLOWED);
        }
        if(!status){
            labResult.setResultStatus(ResultStatus.REJECTED);
            labResultRepository.save(labResult);
            return false;
        }
        if(appointment.getService().getServiceType().equals(ServiceType.CONSULTATION)){
            appointment.setStatus(AppointmentStatus.LAB_COMPLETED);
        }else{
            appointment.setStatus(AppointmentStatus.COMPLETED);
        }
        appointmentRepository.save(appointment);
        return true;
    }

    public Appointment getAppointmentByAppointmentId(int id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
    }

    public LabResultResponse updateLabResultAppointment(int appointmentId, LabResultUpdateRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        LabResult labResult = labResultRepository.findLabResultByLabSampleId(appointment.getLabSample().getId()).orElseThrow(() -> new AppException(ErrorCode.LAB_RESULT_NOT_EXISTED));
        return labTestService.updateLabResult(labResult, request);
    }

    public List<AppointmentLabSampleResponse> getAllDoctorAppointmentsByStatus(String status) {
        DoctorResponse doctorResponse = doctorService.getDoctorProfileByToken();
        AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
        if( !(appointmentStatus.equals(AppointmentStatus.LAB_COMPLETED) || appointmentStatus.equals(AppointmentStatus.COMPLETED)) ){
            throw new AppException(ErrorCode.INPUT_STATUS_FAILED);
        }
        List<Appointment> appointments = appointmentRepository.findByStatus(appointmentStatus)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        List<Appointment> filterAppointments =  appointments.stream()
                        .filter(appointment -> {
                            ScheduleSlot slot = appointment.getScheduleSlot();
                            if (slot == null) return false;
                            return !slot.getSchedule().getDoctor().getId().equals(doctorResponse.getDoctorId());
                        })
                        .toList();
        return  filterAppointments.stream()
                .map(appointment -> {
                    AppointmentLabSampleResponse response = appointmentMapper.toAppointmentLabResponse(appointment);
                    if(response.getLabSampleId() != null){
                        LabResult labResult = labResultRepository.findByLabSampleId(response.getLabSampleId());
                        response.setLabResult(labTestMapper.toLabResultResponse(labResult));
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<AppointmentCreationResponse> getAllAppointmentByPatientId(String patientId) {
        Patient patient = patientService.getPatientById(patientId);
        List<Appointment> appointments = appointmentRepository.findByPatient(patient).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        return appointments.stream()
                .map(appointmentMapper::toAppointmentBasicResponse)
                .collect(Collectors.toList());
    }

    public boolean cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(now, appointment.getCreatedAt());
        if(diff.toHours() > 24){
            throw new AppException(ErrorCode.CANCELLATION_DEADLINE_EXCEEDED);
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        Patient patient = patientService.getPatientById(appointment.getPatient().getId());
        String ticketType = String.valueOf(appointment.getService().getServiceType());
        ticketService.createTicket(patient.getId(), TicketType.valueOf(ticketType));

        LocalDateTime timeNow = LocalDateTime.now();

        if(appointment.getService().getServiceType().equals(ServiceType.CONSULTATION) && appointment.getScheduleSlot() != null){
            ScheduleSlot scheduleSlot = appointment.getScheduleSlot();
            appointment.getScheduleSlot().setStatus(ScheduleSlotStatus.AVAILABLE);
            if(scheduleSlot.getSchedule().getWorkDate().isBefore(timeNow.toLocalDate())){
                appointment.getScheduleSlot().setStatus(ScheduleSlotStatus.EXPIRED);
            }else if(scheduleSlot.getSchedule().getWorkDate().equals(timeNow.toLocalDate()) &&
                    scheduleSlot.getSlot().getStartTime().isBefore(timeNow.toLocalTime())){
                appointment.getScheduleSlot().setStatus(ScheduleSlotStatus.EXPIRED);
            }
        } else{
            appointment.getLabTestSlot().setBookedCount(appointment.getLabTestSlot().getBookedCount() - 1);
        }
        appointmentRepository.save(appointment);

        return true;
    }

    public List<AppointmentPatientResponse> getAllAppointmentCompletedByPatientId(String patientId) {
        Patient patient = patientService.getPatientById(patientId);
        List<Appointment> appointments = appointmentRepository.findByPatient(patient).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
        List<AppointmentPatientResponse> appPatient = appointments.stream()
                .map(appointmentMapper::toAppointmentPatientResponse)
                .toList();
        List<AppointmentPatientResponse> response = new ArrayList<>();
        appPatient.forEach(appointment -> {
            if(appointment.getStatus().equals(AppointmentStatus.COMPLETED) && appointment.getServiceType().equals(ServiceType.CONSULTATION.name())){
                response.add(appointment);
            }
        });
        return response;
    }

    public List<AppointmentCreationResponse> getAllAppointmentByToken() {
        Patient patient = patientService.getPatientResponseByToken();
        List<Appointment> appointments = appointmentRepository.findAppointmentByPatient(patient);
        if(appointments == null || appointments.isEmpty()){
            return new ArrayList<AppointmentCreationResponse>();
        }
        return appointments.stream()
                .map(appointmentMapper::toAppointmentBasicResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentPatientResponse> getAllAppointmentCompletedByToken() {
        Patient patient = patientService.getPatientResponseByToken();
        List<Appointment> appointments = appointmentRepository.findAppointmentByPatient(patient);
        if(appointments == null || appointments.isEmpty()){
            return new ArrayList<AppointmentPatientResponse>();
        }
        List<AppointmentPatientResponse> appPatient = appointments.stream()
                .map(appointmentMapper::toAppointmentPatientResponse)
                .toList();
        List<AppointmentPatientResponse> response = new ArrayList<>();
        appPatient.forEach(appointment -> {
            if(appointment.getStatus().equals(AppointmentStatus.COMPLETED) && appointment.getServiceType().equals(ServiceType.CONSULTATION.name())){
                response.add(appointment);
            }
        });
        return response;
    }

    public boolean createAppointmentByTicket(AppointmentCreationRequest request) {
        Patient patient = patientService.getPatientById(request.getPatientId());

        ServiceEntity service = serviceService.getServiceEntityById(request.getServiceId());

        ScheduleSlot scheduleSlot = scheduleSlotRepository.findScheduleSlotById(request.getScheduleSlotId());
        if(scheduleSlot != null){
            if(scheduleSlot.getStatus().equals(ScheduleSlotStatus.UNAVAILABLE)){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
        }
        LabTestSlot labTestSlot = labTestSlotRepository.findByLabTestSlotId(request.getLabTestSlotId());
        if(service.getServiceType().equals(ServiceType.CONSULTATION) && scheduleSlot == null && labTestSlot != null){
            Page<ScheduleSlot> scheduleSlotTmp = scheduleSlotRepository.chooseDoctorBySlotId(labTestSlot.getSlot().getId(), labTestSlot.getDate() , PageRequest.of(0,1));
            if(scheduleSlotTmp.getContent().getFirst() == null){
                throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_AVAILABLE);
            }
            request.setScheduleSlotId(scheduleSlotTmp.getContent().getFirst().getId());
        }

        String ticketType = String.valueOf(service.getServiceType());
        Ticket ticket = ticketService.getTicketByTypeAndPatientId(patient.getId(), TicketType.valueOf(ticketType));
        if(ticket.getCount() > 0) {
            int count = ticket.getCount() - 1;
            ticket.setCount(count);
            createAppointment(request);
            ticketRepository.save(ticket);
            return true;
        }else{
            throw new AppException(ErrorCode.TICKET_LACKED);
        }
    }

    public boolean cancelAppointmentByManager (AppointmentBlockRequest request){
        Appointment appointment = appointmentRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        if(request.isContinuity()){
            Patient patient = patientService.getPatientById(appointment.getPatient().getId());
            String ticketType = String.valueOf(appointment.getService().getServiceType());
            ticketService.createTicket(patient.getId(), TicketType.valueOf(ticketType));
        }

        if(appointment.getService().getServiceType().equals(ServiceType.CONSULTATION) && appointment.getScheduleSlot() != null){
            appointment.getScheduleSlot().setStatus(ScheduleSlotStatus.BLOCKED);
        }else{
            appointment.getLabTestSlot().setBookedCount(appointment.getLabTestSlot().getBookedCount() - 1);
        }

        appointmentRepository.save(appointment);

        return true;
    }


}
