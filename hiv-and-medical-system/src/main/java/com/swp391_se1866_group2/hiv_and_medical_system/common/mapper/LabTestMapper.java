package com.swp391_se1866_group2.hiv_and_medical_system.common.mapper;


import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LabTestMapper {
    @Mapping(source = "id", target = "labTestId")
    LabTestResponse toLabTestResponse(LabTest labTest);

    @Mapping(source = "id", target = "labTestParameterId")
    LabTestParameterResponse toLabTestParameterResponse(LabTestParameter labTestParameter);
    LabTest toLabTest(LabTestCreationRequest request);

    void updateLabTestParameter(LabTestParameterUpdateRequest request, @MappingTarget LabTestParameter labTestParameter);
}
