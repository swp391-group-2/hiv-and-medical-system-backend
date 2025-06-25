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
    LAB_TEST_NOT_EXISTED(1031, "Lab test not existed", HttpStatus.NOT_FOUND),
    TEST_TYPE_NOT_EXISTED(1032, "Test type not existed", HttpStatus.NOT_FOUND),
    LAB_TEST_PARAMETER_NOT_EXISTED(1033, "Lab test parameter not existed", HttpStatus.NOT_FOUND),
    SCHEDULE_SLOT_NOT_EXISTED(1050, "Schedule slot not existed", HttpStatus.NOT_FOUND),
    SCHEDULE_SLOT_NOT_AVAILABLE(1051, "Schedule slot not available", HttpStatus.CONFLICT),
    DOCTOR_AND_SCHEDULE_SLOT_CONFLICT(1052, "Doctor and schedule slot conflict", HttpStatus.BAD_REQUEST),
    LAB_TEST_SLOT_FULL(1053, "Lab test slot full", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_EXISTED(1054, "Appointment not existed", HttpStatus.NOT_FOUND),
    ALREADY_CHECKED_IN(1055, "Appointment already checked in", HttpStatus.CONFLICT),
    LAB_RESULT_NOT_EXISTED(1056, "Lab result not existed", HttpStatus.NOT_FOUND),
    LAB_RESULT_CAN_NOT_ALLOWED(1057, "Lab result can not return allowed", HttpStatus.CONFLICT),
    UPLOAD_FAILED(1058, "Upload image failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INPUT_STATUS_FAILED(1059, "Input status failed", HttpStatus.INTERNAL_SERVER_ERROR),
    PATIENT_PRESCRIPTION_NOT_EXISTED(1060, "Patient Prescription not existed", HttpStatus.NOT_FOUND),
    PAYMENT_NOT_EXISTED(1070, "Payment not existed. ", HttpStatus.NOT_FOUND),
    CANCELLATION_DEADLINE_EXCEEDED(1071, "Cancellation deadline exceeded", HttpStatus.CONFLICT),
    PASSWORD_NOT_MATCHES(1072,"Password not matches", HttpStatus.BAD_REQUEST),
    BLOG_POST_NOT_EXISTED(1080, "Blog post not existed. ", HttpStatus.NOT_FOUND),
    BLOG_POST_EXISTED(1081, "Blog post already existed", HttpStatus.BAD_REQUEST),



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
