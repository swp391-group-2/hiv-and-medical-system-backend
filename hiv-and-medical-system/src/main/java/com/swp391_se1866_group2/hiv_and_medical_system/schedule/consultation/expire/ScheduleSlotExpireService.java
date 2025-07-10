package com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.expire;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ScheduleSlotStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.repository.ScheduleSlotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ScheduleSlotExpireService {
    ScheduleSlotRepository scheduleSlotRepository;

    @Scheduled(fixedDelay = 600000)
    public void expireScheduleSlots() {
        LocalDateTime today = LocalDateTime.now();
        log.info("ScheduleSlotExpireService run at: {}, thread: {}", LocalDateTime.now(), Thread.currentThread().getName());
        List<ScheduleSlot> scheduleSlots = scheduleSlotRepository.findAllByStatusAndDateBefore(ScheduleSlotStatus.AVAILABLE, today.toLocalDate());
        scheduleSlots.forEach(scheduleSlot -> {
                    if(scheduleSlot.getSchedule().getWorkDate().isBefore(today.toLocalDate())) {
                        scheduleSlot.setStatus(ScheduleSlotStatus.EXPIRED);
                    } else if(scheduleSlot.getSchedule().getWorkDate().equals(today.toLocalDate()) && scheduleSlot.getSlot().getStartTime().isBefore(today.toLocalTime())) {
                        scheduleSlot.setStatus(ScheduleSlotStatus.EXPIRED);
                    }
                });
        scheduleSlotRepository.saveAll(scheduleSlots);
    }

    @Scheduled(fixedDelay = 600000)
    public void expireCheckedInScheduleSlots() {
        LocalDateTime today = LocalDateTime.now();
        log.info("ScheduleSlotExpire_No_CheckedInService run at: {}, thread: {}", LocalDateTime.now(), Thread.currentThread().getName());
        List<ScheduleSlot> scheduleSlots = scheduleSlotRepository.findAllByStatusAndAppoiStatusAndDateBefore(ScheduleSlotStatus.UNAVAILABLE, AppointmentStatus.EXPIRED, today.toLocalDate());
        scheduleSlots.forEach(scheduleSlot -> {
            if(scheduleSlot.getSchedule().getWorkDate().isBefore(today.toLocalDate())) {
                scheduleSlot.setStatus(ScheduleSlotStatus.EXPIRED_NO_CHECKED_IN);
            } else if(scheduleSlot.getSchedule().getWorkDate().equals(today.toLocalDate()) && scheduleSlot.getSlot().getEndTime().isBefore(today.toLocalTime())) {
                scheduleSlot.setStatus(ScheduleSlotStatus.EXPIRED_NO_CHECKED_IN);
            }
        });
        scheduleSlotRepository.saveAll(scheduleSlots);
    }
}
