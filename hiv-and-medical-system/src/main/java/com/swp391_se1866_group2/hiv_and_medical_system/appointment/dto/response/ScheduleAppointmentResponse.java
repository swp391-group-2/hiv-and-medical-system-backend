package com.swp391_se1866_group2.hiv_and_medical_system.appointment.dto.response;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.AppointmentStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.dto.response.ScheduleSlotResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.schedule.consultation.entity.ScheduleSlot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleAppointmentResponse {
    LocalDate workDate;
    AppointmentStatus appointmentStatus;
    String patientName;
    ScheduleSlot scheduleSlot;

    public ScheduleAppointmentResponse() {
    }

    public ScheduleAppointmentResponse(LocalDate workDate, AppointmentStatus appointmentStatus, String patientName, ScheduleSlot scheduleSlot) {
        this.workDate = workDate;
        this.appointmentStatus = appointmentStatus;
        this.patientName = patientName;
        this.scheduleSlot = scheduleSlot;
    }
}
