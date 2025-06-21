package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;


public enum Role {
    ADMIN,
    PATIENT,
    DOCTOR,
    STAFF,
    MANAGER,

    LAB_TECHNICIAN;
    @JsonCreator
    public static ServiceType from(String value) {
        try {
            return ServiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.STATUS_NOT_EXISTED);
        }
    }
}
