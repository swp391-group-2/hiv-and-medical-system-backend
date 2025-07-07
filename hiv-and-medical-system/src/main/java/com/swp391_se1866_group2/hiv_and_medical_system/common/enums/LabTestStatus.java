package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum LabTestStatus {
    AVAILABLE,
    FULL,
    EXPIRED;

    @JsonCreator
    public static LabTestStatus from(String value) {
        try {
            return LabTestStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.LAB_TEST_NOT_EXISTED);
        }
    }
}
