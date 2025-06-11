package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum ServiceType {
    CONSULTATION,
    SCREENING,
    CONFIRMATORY;

    @JsonCreator
    public static ServiceType from(String value) {
        try {
            return ServiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.SERVICE_TYPE_NOT_EXISTED);
        }
    }
}
