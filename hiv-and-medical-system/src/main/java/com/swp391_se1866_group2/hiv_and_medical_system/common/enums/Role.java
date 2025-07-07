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
    public static Role from(String value) {
        try {
            return Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
}
