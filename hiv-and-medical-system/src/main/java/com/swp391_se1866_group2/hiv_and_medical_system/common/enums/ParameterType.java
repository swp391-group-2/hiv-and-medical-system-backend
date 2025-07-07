package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum ParameterType {
    NUMERIC,
    TEXT;
    @JsonCreator
    public static ParameterType from(String value) {
        try {
            return ParameterType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.LAB_TEST_PARAMETER_NOT_EXISTED);
        }
    }

}
