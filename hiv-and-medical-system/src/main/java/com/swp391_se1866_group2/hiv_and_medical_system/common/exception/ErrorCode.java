package com.swp391_se1866_group2.hiv_and_medical_system.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized Exception", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email already existed", HttpStatus.CONFLICT),
    PATIENT_NOT_EXISTED(1003, "Patient not existed", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1004, "Password is invalid, Password must be at least 8 characters " , HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    DOCTOR_NOT_EXISTED(1009, "Doctor not existed", HttpStatus.NOT_FOUND),
    MEDICATION_NOT_EXISTED(1010, "Medication not existed", HttpStatus.NOT_FOUND),
    SLOT_EXISTED(1011, "Slot already existed", HttpStatus.CONFLICT),
    SLOT_NOT_EXISTED(1012, "Slot not existed", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatus statusCode;
}
