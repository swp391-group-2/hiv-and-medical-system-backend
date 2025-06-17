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
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabSampleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabTestMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.PrescriptionMapper;
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
    LabTestService labTestService;
    PrescriptionRepository prescriptionRepository;
    PatientService patientService;
    ServiceService serviceService;
    DoctorService doctorService;

    PrescriptionMapper prescriptionMapper;
    LabTestMapper labTestMapper;
    LabSampleMapper labSampleMapper;
    AppointmentMapper appointmentMapper;

//    @PreAuthorize("hasRole('PATIENT')")
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
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        return appointmentMapper.toAppointmentResponse(appointmentSaved);
    }

//    @PreAuthorize("hasRole('PATIENT') or hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
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

//    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
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

//    @PreAuthorize("hasRole('MANAGER') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR') or hasRole('STAFF') or hasRole('ADMIN')")
    public PrescriptionResponse choosePrescription(int prescriptionId, int appointmentId, String note) {
        Appointment appointment = getAppointmentByAppointmentId(appointmentId);
        appointment.setNote(note);
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElseThrow(() -> new AppException(ErrorCode.PRESCRIPTION_NOT_EXISTED));
        appointment.setPrescription(prescription);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
        return prescriptionMapper.toPrescriptionResponse(prescription);
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
}
