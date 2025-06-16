package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ScheduleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.dto.response.DoctorResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.request.ScheduleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.DoctorWorkScheduleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.service.SlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorWorkScheduleService {
    DoctorWorkScheduleRepository doctorWorkScheduleRepository;

    SlotService slotService;
    DoctorService doctorService;
    ScheduleMapper scheduleMapper;

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') ")
    public DoctorWorkScheduleResponse createDoctorSchedule (String doctorId , ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if(doctorWorkScheduleRepository.existsByWorkDateAndDoctorId(request.getWorkDate(), doctorId)){
            throw new AppException(ErrorCode.WORK_DATE_EXISTED);
        }
        DoctorWorkSchedule schedule = new DoctorWorkSchedule();
        schedule.setDoctor(doctor);
        schedule.setWorkDate(request.getWorkDate());
        List<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = new ScheduleSlot();
                    scheduleSlot.setSlot(slotService.getSlotById(slotId));
                    scheduleSlot.setSchedule(schedule);
                    return scheduleSlot;
                })
                .collect(Collectors.toList());
        schedule.setScheduleSlots(scheduleSlots);
        DoctorWorkSchedule doctorWorkSchedule = doctorWorkScheduleRepository.save(schedule);
        return scheduleMapper.toDoctorWorkScheduleResponse(doctorWorkSchedule);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public DoctorWorkScheduleResponse updateDoctorSchedule (String doctorId, ScheduleUpdateRequest request, LocalDate date) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        DoctorWorkSchedule schedule = doctorWorkScheduleRepository.findByWorkDate(date)
                .orElseThrow(() -> new AppException(ErrorCode.WORK_DATE_NOT_EXISTED));
        List<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = new ScheduleSlot();
                    scheduleSlot.setSlot(slotService.getSlotById(slotId));
                    scheduleSlot.setSchedule(schedule);
                    return scheduleSlot;
                })
                .collect(Collectors.toList());
        schedule.setScheduleSlots(scheduleSlots);
        return scheduleMapper.toDoctorWorkScheduleResponse(doctorWorkScheduleRepository.save(schedule));
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<DoctorWorkScheduleResponse> createDoctorScheduleBulk (String doctorId, ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        LocalDate start = request.getWorkDate();
        LocalDate end = request.getWorkDate().plusDays(7);
        List<DoctorWorkScheduleResponse> doctorWorkSchedules = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (!doctorWorkScheduleRepository.existsByWorkDateAndDoctorId(date, doctorId)) {
                request.setWorkDate(date);
                doctorWorkSchedules.add(createDoctorSchedule(doctorId, request));
            }
        }
        return doctorWorkSchedules;
    }

    public List<ScheduleResponse> getDoctorWorkScheduleByDate (String doctorId, LocalDate workDate) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<DoctorWorkSchedule> doctorWorkSchedules = doctorWorkScheduleRepository.findAllByWorkDateAndDoctorId(workDate, doctorId);
        return doctorWorkSchedules.stream().map(scheduleMapper::toScheduleResponse).collect(Collectors.toList());
    }

    public List<ScheduleResponse> getDoctorWorkScheduleByDoctorId (String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<DoctorWorkSchedule> doctorWorkSchedules = doctorWorkScheduleRepository.findAllByDoctorId(doctorId);
        return doctorWorkSchedules.stream().map(scheduleMapper::toScheduleResponse).collect(Collectors.toList());
    }

//    @PreAuthorize("hasRole('DOCTOR')")
    public List<ScheduleResponse> getDWScheduleByTokenAndBetweenDate (LocalDate startTime, LocalDate endTime) {
        DoctorResponse doctor = doctorService.getDoctorProfileByToken();
        if(startTime == null && endTime == null) {
            throw new AppException(ErrorCode.DATE_INPUT_INVALID);
        }
        List<DoctorWorkSchedule> listDWSchedule = doctorWorkScheduleRepository.findAllByWorkDateBetweenAndDoctorId(startTime, endTime, doctor.getDoctorId());
        return listDWSchedule.stream().map(scheduleMapper::toScheduleResponse).collect(Collectors.toList());
    }

    public List<DoctorWorkScheduleResponse> getAllDoctorWorkSchedule () {
        return doctorWorkScheduleRepository.findAll().stream().map(scheduleMapper::toDoctorWorkScheduleResponse).collect(Collectors.toList());
    }



}
