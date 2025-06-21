package com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.query;


import java.sql.Date;
import java.time.LocalDate;


public class AppointmentCountPerDate {
    private LocalDate date;
    private long countAppointment;

    public AppointmentCountPerDate(LocalDate date, long countAppointment) {
        this.date = date;
        this.countAppointment = countAppointment;
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCountAppointment() {
        return countAppointment;
    }

    public void setCountAppointment(long countAppointment) {
        this.countAppointment = countAppointment;
    }
}
