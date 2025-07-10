package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum AppointmentStatus {

    SCHEDULED,
    CHECKED_IN,
    LAB_COMPLETED,
    COMPLETED,
    CANCELLED,
    EXPIRED,
    ;

    @JsonCreator
    public static AppointmentStatus from(String value) {
        try {
            return AppointmentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.APPOINTMENT_NOT_EXISTED);
        }
    }

}
