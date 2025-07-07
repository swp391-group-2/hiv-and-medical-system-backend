package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum TicketType {
    CONSULTATION,
    SCREENING,
    CONFIRMATORY;

    @JsonCreator
    public static TicketType from(String value) {
        try {
            return TicketType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.TICKET_TYPE_NOT_EXISTED);
        }
    }

}
