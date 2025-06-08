package com.swp391_se1866_group2.hiv_and_medical_system.schedule.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ScheduleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.entity.Doctor;
import com.swp391_se1866_group2.hiv_and_medical_system.doctor.service.DoctorService;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.request.ScheduleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.dto.response.DoctorWorkScheduleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.DoctorWorkSchedule;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.repository.DoctorWorkScheduleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.slot.service.SlotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorWorkScheduleService {
    DoctorWorkScheduleRepository scheduleRepository;
    SlotService slotService;
    DoctorService doctorService;
    ScheduleMapper scheduleMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') ")
    public DoctorWorkScheduleResponse createDoctorSchedule (String doctorId , ScheduleCreationRequest request) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Set<ScheduleSlot> scheduleSlots = request.getSlotId().stream()
                .map(slotId -> {
                    ScheduleSlot scheduleSlot = new ScheduleSlot();
                    scheduleSlot.setSlot(slotService.getSlotById(slotId));
                    return scheduleSlot;
                })
                .collect(Collectors.toSet());
        DoctorWorkSchedule schedule = new DoctorWorkSchedule();
        schedule.setDoctor(doctor);
        schedule.setScheduleSlots(scheduleSlots);
        schedule.setWorkDate(request.getWorkDate());
        return scheduleMapper.toDoctorWorkScheduleResponse(scheduleRepository.save(schedule));
    }

}
