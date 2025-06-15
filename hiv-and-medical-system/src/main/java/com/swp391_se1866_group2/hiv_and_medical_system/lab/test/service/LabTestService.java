package com.swp391_se1866_group2.hiv_and_medical_system.lab.test.service;


import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ParameterType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ResultStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabTestMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.repository.LabSampleRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.request.*;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabResultResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestParameterResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.dto.response.LabTestResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabResult;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.entity.LabTestParameter;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabResultRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestParameterRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.test.repository.LabTestRepository;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.service.ServiceService;
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
    ServiceService serviceService;

    LabTestMapper labTestMapper;
    LabResultRepository labResultRepository;
    LabTestRepository labTestRepository;
    LabTestParameterRepository labTestParameterRepository;

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LabTestResponse createLabTest(LabTestCreationRequest request){
       LabTest labTest = labTestMapper.toLabTest(request);
       LabTestParameterCreationRequest parameterRequest = request.getLabTestParameter();
       LabTestParameter labTestParameter = new LabTestParameter();
       labTestParameter.setParameterName(parameterRequest.getParameterName());
       labTestParameter.setDescription(parameterRequest.getDescription());

        ParameterType parameterType = labTest.getTestType().getParameterType();
        labTestParameter.setParameterType(parameterType);

        ServiceEntity service = serviceService.getServiceEntityById(request.getServiceId());
        if(parameterType == ParameterType.NUMERIC){
            labTestParameter.setUnitCD4(parameterRequest.getUnitCD4());
            labTestParameter.setUnitViralLoad(parameterRequest.getUnitViralLoad());
            labTestParameter.setNormalRangeCD4(parameterRequest.getNormalRangeCD4());
            labTestParameter.setNormalRangeStringViralLoad(parameterRequest.getNormalRangeStringViralLoad());
        }
        else {
            labTestParameter.setUnitCD4(null);
            labTestParameter.setUnitViralLoad(null);
            labTestParameter.setNormalRangeCD4(null);
            labTestParameter.setNormalRangeStringViralLoad(null);
        }
        labTest.setLabTestParameter(labTestParameter);
        labTestParameter.setLabTest(labTest);

        labTest.setService(service);
        service.setLabTest(labTest);
        return labTestMapper.toLabTestResponse(labTestRepository.save(labTest));

    }

//    @PreAuthorize("hasRole('DOCTOR') or hasRole('LAB_TECHNICIAN') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<LabTestResponse> getAllLabTests(){
        return labTestRepository.findAll().stream()
                .map(labTestMapper::toLabTestResponse)
                .collect(Collectors.toList());

    }

//    @PreAuthorize("hasRole('DOCTOR') or hasRole('LAB_TECHNICIAN') or hasRole('STAFF') or hasRole('ADMIN') or hasRole('MANAGER')")
    public List<LabTestResponse> getLabTestByName(String labTestName) {
        List<LabTest> labTests = labTestRepository.findAllByNameContainingIgnoreCase(labTestName);
        if (labTests.isEmpty()) {
            throw new AppException(ErrorCode.LAB_TEST_NOT_EXISTED);
        }
        return labTests.stream().
                map(labTestMapper::toLabTestResponse)
                .collect(Collectors.toList());

    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LabTestParameterResponse updateLabTestParameter(int labTestId, int labTestParameterId, LabTestParameterUpdateRequest request) {
        LabTest labTest = labTestRepository.findById(labTestId)
                .orElseThrow(() -> new AppException(ErrorCode.LAB_TEST_NOT_EXISTED));

        LabTestParameter labTestParameter = labTest.getLabTestParameter();
        if (labTestParameter == null || labTestParameter.getId() != labTestParameterId) {
            throw new AppException(ErrorCode.LAB_TEST_PARAMETER_NOT_EXISTED);
        }

        labTestParameter.setUnitCD4(request.getUnitCD4());
        labTestParameter.setUnitViralLoad(request.getUnitViralLoad());
        labTestParameter.setNormalRangeCD4(request.getNormalRangeCD4());
        labTestParameter.setNormalRangeStringViralLoad(request.getNormalRangeStringViralLoad());
        labTestParameter.setDescription(request.getDescription());

        labTestParameterRepository.save(labTestParameter);
        return labTestMapper.toLabTestParameterResponse(labTestParameter);
    }

    public LabResultResponse updateLabResult(LabResult labResult, LabResultUpdateRequest request) {
        labTestMapper.updateLabResult(request, labResult);
        labResult.setResultStatus(ResultStatus.FINISHED);
        return labTestMapper.toLabResultResponse(labResultRepository.save(labResult));
    }

    public LabResultResponse inputLabResult (int sampleId, LabResultUpdateRequest request){
        LabResult labResult = labResultRepository.findByLabSampleId(sampleId);
        if(labResult == null) {
            throw new AppException(ErrorCode.LAB_RESULT_NOT_EXISTED);
        }
        labTestMapper.updateLabResult(request, labResult);
        labResult.setResultStatus(ResultStatus.FINISHED);
        return labTestMapper.toLabResultResponse(labResultRepository.save(labResult)) ;
    }


}
