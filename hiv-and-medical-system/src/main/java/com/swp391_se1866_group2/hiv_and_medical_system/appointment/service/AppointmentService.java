package com.swp391_se1866_group2.hiv_and_medical_system.appointment.service;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.request.AppointmentCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentLabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response.AppointmentResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.*;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.AppointmentMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabTestMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionMapper;
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
import com.swp391_se1866_group2.hiv_and_medical_system.patient.entity.Patient;
import com.swp391_se1866_group2.hiv_and_medical_system.patient.service.PatientService;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.dto.response.PrescriptionResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.entity.Prescription;
import com.swp391_se1866_group2.hiv_and_medical_system.prescription.repository.PrescriptionRepository;
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
    LabTestSlotService labTestSlotService;
    ScheduleSlotService scheduleSlotService;
    PrescriptionRepository prescriptionRepository;
    PatientService patientService;
    ServiceService serviceService;

    PrescriptionMapper prescriptionMapper;
    LabTestMapper labTestMapper;
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

    @PreAuthorize("hasRole('PATIENT') or hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public AppointmentLabSampleResponse getAppointmentById(int id) {
        AppointmentLabSampleResponse appointmentLabSampleResponse = appointmentMapper.toAppointmentLabResponse(appointmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED)));
        if(appointmentLabSampleResponse.getLabSampleId() != null){
            LabResult labResult = labResultRepository.findByLabSampleId(appointmentLabSampleResponse.getLabSampleId());
            appointmentLabSampleResponse.setLabResult(labTestMapper.toLabResultResponse(labResult));
        }
        if(appointmentLabSampleResponse.getPrescription() != null){
            Prescription prescription = prescriptionRepository.findById(appointmentLabSampleResponse.getPrescription().getPrescriptionId()).orElse(null);
            appointmentLabSampleResponse.setPrescription(prescriptionMapper.toPrescriptionResponse(prescription));
        }
        return appointmentLabSampleResponse;
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentLabSampleResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> {
                    AppointmentLabSampleResponse response = appointmentMapper.toAppointmentLabResponse(appointment);
                    if (response.getLabSampleId() != null) {
                        LabResult labResult = labResultRepository.findByLabSampleId(response.getLabSampleId());
                        response.setLabResult(labTestMapper.toLabResultResponse(labResult));
                    }
                    if(response.getPrescription() != null){
                        Prescription prescription = prescriptionRepository.findById(response.getPrescription().getPrescriptionId()).orElse(null);
                        response.setPrescription(prescriptionMapper.toPrescriptionResponse(prescription));
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public List<AppointmentLabSampleResponse> getAllAppointmentsByStatus(AppoimentStatus status) {
        return appointmentRepository.findByStatus(status).stream()
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

    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public AppointmentLabSampleResponse checkinAppointment(int appointmentId, LabSampleCreationRequest request) {
        Appointment appointment = getAppointmentByAppointmentId(appointmentId);
        if (appointment.getLabSample() != null) {
            throw new AppException(ErrorCode.ALREADY_CHECKED_IN);
        }
        LabSample sample = new LabSample();
        sample.setSampleCode(request.getSampleCode());
        sample.setSampleType(request.getSampleType());
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
        labTestParameter.setLabResult(labResult);
        labResult.setLabSample(sample);
        sample.setLabResults(labResult);
        sample.setAppointment(appointment);
        appointment.setStatus(AppoimentStatus.CHECKED_IN);
        appointment.setLabSample(sample);
        labSampleRepository.save(sample);
        return appointmentMapper.toAppointmentLabResponse(appointmentRepository.save(appointment));
    }

    public LabResultResponse updateLabResultAppointment(int sampleId, LabResultUpdateRequest request) {
        LabResult labResult =  labResultRepository.findByLabSampleId(sampleId);
        labTestMapper.updateLabResult(request, labResult);
        Appointment appointment = getAppointmentByAppointmentId(labResult.getLabSample().getAppointment().getId());
        appointment.setStatus(AppoimentStatus.LAB_COMPLETED);
        appointmentRepository.save(appointment);
        return labTestMapper.toLabResultResponse(labResultRepository.save(labResult)) ;
    }
    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public PrescriptionResponse choosePrescription(int prescriptionId, int appointmentId) {
        Appointment appointment = getAppointmentByAppointmentId(appointmentId);
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));
        appointment.setPrescription(prescription);
        prescription.setAppointment(appointment);
        appointmentRepository.save(appointment);
        Prescription prescriptionSaved = prescriptionRepository.save(prescription);
        return prescriptionMapper.toPrescriptionResponse(prescriptionSaved);
    }

    public Appointment getAppointmentByAppointmentId(int id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED));
    }

}
