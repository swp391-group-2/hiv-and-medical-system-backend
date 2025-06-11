package com.swp391_se1866_group2.hiv_and_medical_system.common.enums;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;

public enum TestType {
    SCREENING,
    CONFIRMATORY,
    CD4_VIRAL_LOAD;

    public ParameterType getParameterType() {
        switch (this) {
            case SCREENING:
            case CONFIRMATORY:
                return ParameterType.TEXT;
            case CD4_VIRAL_LOAD:
                return ParameterType.NUMERIC;

            default:
                throw new AppException(ErrorCode.TEST_TYPE_NOT_EXISTED);
        }
    }
}
