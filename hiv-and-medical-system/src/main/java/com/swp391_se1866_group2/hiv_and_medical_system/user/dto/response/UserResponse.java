package com.swp391_se1866_group2.hiv_and_medical_system.user.dto.response;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserResponse {
    String id;
    String email;
    String fullName;
    String status;
    String code;
    String role;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getCode() {
        return code;
    }

    @JsonProperty("managerId")
    public String getManagerId() {
        return "MANAGER".equals(role) ? id : null;
    }

    @JsonProperty("managerCode")
    public String getManagerCode() {
        return "MANAGER".equals(role) ? code : null;
    }

    @JsonProperty("staffId")
    public String getStaffId() {
        return "STAFF".equals(role) ? id : null;
    }

    @JsonProperty("staffCode")
    public String getStaffCode() {
        return "STAFF".equals(role) ? code : null;
    }

    @JsonProperty("labTechnicianId")
    public String getLabTechnicianId() {
        return "LAB_TECHNICIAN".equals(role) ? id : null;
    }

    @JsonProperty("labTechnicianCode")
    public String getLabTechnicianCode() {
        return "LAB_TECHNICIAN".equals(role) ? code : null;
    }


}
