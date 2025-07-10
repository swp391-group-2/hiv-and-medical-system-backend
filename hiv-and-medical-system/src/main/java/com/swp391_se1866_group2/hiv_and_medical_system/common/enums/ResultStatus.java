package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum ResultStatus {
    PENDING,
    FINISHED,
    REJECTED,;

    @JsonCreator
    public static ResultStatus from(String value) {
        try {
            return ResultStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.LAB_RESULT_NOT_EXISTED);
        }
    }

}
