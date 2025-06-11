package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ParameterType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabTestMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.repository.LabSampleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabResultCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.LabTestParameterUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
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
    LabSampleRepository labSampleRepository;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LabTestResponse createLabTest(LabTestCreationRequest request){
       LabTest labTest = labTestMapper.toLabTest(request);
       LabTestParameterCreationRequest parameterRequest = request.getLabTestParameter();
       LabTestParameter labTestParameter = new LabTestParameter();
       labTestParameter.setParameterName(parameterRequest.getParameterName());
       labTestParameter.setDescription(parameterRequest.getDescription());

        ParameterType parameterType = labTest.getTestType().getParameterType();
        labTestParameter.setParameterType(parameterType);

        if(parameterType == ParameterType.NUMERIC){
            labTestParameter.setUnit(parameterRequest.getUnit());
            labTestParameter.setNormalRange(parameterRequest.getNormalRange());
        }
        else {
            labTestParameter.setUnit(null);
            labTestParameter.setNormalRange(null);
        }

        labTest.setLabTestParameter(labTestParameter);
        labTestParameter.setLabTest(labTest);

        LabResultCreationRequest resultRequest = parameterRequest.getLabResult();
        LabSample labSample = labSampleRepository.findById(resultRequest.getSampleId())
                .orElseThrow(() -> new AppException(ErrorCode.LAB_SAMPLE_NOT_EXISTED));
        LabResult labResult = new LabResult();
        labResult.setConclusion(resultRequest.getConclusion());
        labResult.setNote(resultRequest.getNote());
        labResult.setTestDate(resultRequest.getTestDate());
        labResult.setResultDate(resultRequest.getResultDate());

        if(parameterType == ParameterType.NUMERIC){
            labResult.setResultNumeric(resultRequest.getResultNumeric());
            labResult.setResultText(null);
        }
        else {
            labResult.setResultNumeric(null);
            labResult.setResultText(resultRequest.getResultText());
        }

        labResult.setLabSample(labSample);
        labResult.setLabTestParameter(labTestParameter);
        labTestParameter.setLabResult(labResult);

        return labTestMapper.toLabTestResponse(labTestRepository.save(labTest));

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
