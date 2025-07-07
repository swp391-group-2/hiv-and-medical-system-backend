package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum LabSampleStatus {
    PENDING,
    COLLECTED,
    COMPLETED,
    REJECTED;

    @JsonCreator
    public static LabSampleStatus from(String value) {
        try {
            return LabSampleStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.LAB_SAMPLE_NOT_EXISTED);
        }
    }

}
