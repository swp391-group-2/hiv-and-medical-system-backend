package com.swp391_se1866_group2.hiv_and_medical_system.dashboard.dto.projection;

import java.time.LocalDate;

public interface MaxMinAppointmentResponse {
    LocalDate getDate();
    long getCount();
}
