package com.swp391_se1866_group2.hiv_and_medical_system.appointment.expire;

import com.swp391_se1866_group2.hiv_and_medical_system.appointment.entity.Appointment;
import com.swp391_se1866_group2.hiv_and_medical_system.appointment.repository.AppointmentRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentExpireService {
    AppointmentRepository appointmentRepository;

    @Scheduled(fixedRate = 600000)
    public void expireAppointments() {
        LocalDateTime today = LocalDateTime.now();
        log.info("AppointmentExpireService run at: {}, thread: {}", LocalDateTime.now(), Thread.currentThread().getName());
        List<Appointment> appointmentsConsult = appointmentRepository.findAllAppointmentsConsultCheckExpire(today.toLocalDate(), AppointmentStatus.SCHEDULED);

        appointmentsConsult
                .forEach(appointment -> {
                    if(appointment.getScheduleSlot().getSchedule().getWorkDate().isBefore(today.toLocalDate())){
                        appointment.setStatus(AppointmentStatus.EXPIRED);
                    }else if(appointment.getScheduleSlot().getSchedule().getWorkDate().equals(today.toLocalDate()) && appointment.getScheduleSlot().getSlot().getEndTime().isBefore(today.toLocalTime())){
                        appointment.setStatus(AppointmentStatus.EXPIRED);
                    }
                });
        appointmentRepository.saveAll(appointmentsConsult);


        List<Appointment> appointmentsLabTest = appointmentRepository.findAllAppointmentsTestCheckExpire(today.toLocalDate(), AppointmentStatus.SCHEDULED);
        appointmentsLabTest
                .forEach(appointment -> {
                    if(appointment.getLabTestSlot().getDate().isBefore(today.toLocalDate())){
                        appointment.setStatus(AppointmentStatus.EXPIRED);
                    }else if(appointment.getLabTestSlot().getDate().equals(today.toLocalDate()) && appointment.getLabTestSlot().getSlot().getEndTime().isBefore(today.toLocalTime())){
                        appointment.setStatus(AppointmentStatus.EXPIRED);
                    }
                });
        appointmentRepository.saveAll(appointmentsLabTest);
    }
}
