package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service;


import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabTestMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestParameterRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class LabTestService {
    LabTestMapper labTestMapper;
    LabTestRepository labTestRepository;
    LabTestParameterRepository labTestParameterRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LabTestResponse createLabTest(LabTestCreationRequest request){
        LabTest labTest = labTestMapper.toLabTest(request);

        LabTestParameter parameter = new LabTestParameter();
        parameter.setParameterName(request.getLabTestParameter().getParameterName());
        parameter.setUnit(request.getLabTestParameter().getUnit());
        parameter.setNormalRange(request.getLabTestParameter().getNormalRange());
        parameter.setDescription(request.getLabTestParameter().getDescription());
        parameter.setParameterType(labTest.getTestType().getParameterType());

        labTest.setLabTestParameter(parameter);
        parameter.setLabTest(labTest);
        LabTest savedLabTest = labTestRepository.save(labTest);
        return labTestMapper.toLabTestResponse(savedLabTest);

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('LAB_TECHNICIAN') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<LabTestResponse> getAllLabTests(){
        return labTestRepository.findAll().stream()
                .map(labTestMapper::toLabTestResponse)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('DOCTOR') or hasRole('LAB_TECHNICIAN') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<LabTestResponse> getLabTestByName(String labTestName) {
        List<LabTest> labTests = labTestRepository.findAllByNameContainingIgnoreCase(labTestName);
        if (labTests.isEmpty()) {
            throw new AppException(ErrorCode.LAB_TEST_NOT_EXISTED);
        }
        return labTests.stream().
                map(labTestMapper::toLabTestResponse)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LabTestParameterResponse updateLabTestParameter(int labTestId, int labTestParameterId, LabTestParameterUpdateRequest request) {
        LabTest labTest = labTestRepository.findById(labTestId)
                .orElseThrow(() -> new AppException(ErrorCode.LAB_TEST_NOT_EXISTED));

        LabTestParameter labTestParameter = labTest.getLabTestParameter();
        if (labTestParameter == null || labTestParameter.getId() != labTestParameterId) {
            throw new AppException(ErrorCode.LAB_TEST_PARAMETER_NOT_EXISTED);
        }

        labTestParameter.setUnit(request.getUnit());
        labTestParameter.setNormalRange(request.getNormalRange());
        labTestParameter.setDescription(request.getDescription());

        labTestParameterRepository.save(labTestParameter);
        return labTestMapper.toLabTestParameterResponse(labTestParameter);

    }


}
