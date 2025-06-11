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
    MEDICATION_EXISTED(1011, "Medication already existed", HttpStatus.BAD_REQUEST),
    PRESCRIPTION_NOT_EXISTED(1012, "Prescription not existed", HttpStatus.NOT_FOUND),
    PRESCRIPTION_EXISTED(1013, "Prescription already existed", HttpStatus.BAD_REQUEST),
    SLOT_EXISTED(1014, "Slot already existed", HttpStatus.CONFLICT),
    SLOT_NOT_EXISTED(1015, "Slot not existed", HttpStatus.NOT_FOUND),
    WORK_DATE_EXISTED(1016, "Work date is existed", HttpStatus.CONFLICT),
    DATE_INPUT_INVALID(1017, "Date input is invalid", HttpStatus.BAD_REQUEST),
    WORK_DATE_NOT_EXISTED(1018, "Work date is existed", HttpStatus.NOT_FOUND),
    SCHEDULE_NOT_EXISTED(1019, "Schedule not existed", HttpStatus.NOT_FOUND),
    LAB_SAMPLE_NOT_EXISTED(1020, "Lab sample not existed", HttpStatus.NOT_FOUND),
    LAB_SAMPLE_EXISTED(1021, "Lab sample already existed", HttpStatus.BAD_REQUEST),
    LAB_SAMPLE_CODE_EXISTED(1022, "Lab sample code already existed", HttpStatus.BAD_REQUEST),
    SERVICE_TYPE_NOT_EXISTED(1023, "Service type not existed", HttpStatus.NOT_FOUND),
    SERVICE_TYPE_EXISTED(1024, "Service type already existed", HttpStatus.BAD_REQUEST),
    SERVICE_EXISTED(1025, "Service already existed", HttpStatus.BAD_REQUEST),
    SERVICE_NOT_EXISTED(1026, "Service not existed", HttpStatus.NOT_FOUND),
    STATUS_NOT_EXISTED(1027, "Status not existed", HttpStatus.NOT_FOUND),
    LAB_TEST_SLOT_EXISTED(1028, "Lab test slot already existed", HttpStatus.BAD_REQUEST),
    LAB_TEST_SLOT_NOT_EXISTED(1029, "Lab test slot not existed", HttpStatus.NOT_FOUND),
    PRESCRIPTION_ITEM_NOT_EXISTED(1030, "Prescription item not existed", HttpStatus.NOT_FOUND),
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
