package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum ScheduleSlotStatus {
    AVAILABLE,
    UNAVAILABLE,
    BLOCKED,
    EXPIRED
    ;
    @JsonCreator
    public static ScheduleSlotStatus from(String value) {
        try {
            return ScheduleSlotStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.SCHEDULE_SLOT_NOT_EXISTED);
        }
    }
}
