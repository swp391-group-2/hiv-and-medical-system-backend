package com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.LabSampleStatus;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.LabSampleMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.request.LabSampleUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.dto.response.LabSampleResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.entity.LabSample;
import com.swp391_se1866_group2.hiv_and_medical_system.lab.sample.repository.LabSampleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LabSampleService {
    LabSampleRepository labSampleRepository;
    LabSampleMapper labSampleMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public LabSampleResponse createLabSample(LabSampleCreationRequest request) {
        if(labSampleRepository.existsBySampleCode(request.getSampleCode())){
            throw new AppException(ErrorCode.LAB_SAMPLE_EXISTED);
        }
        LabSample labSample = labSampleMapper.toLabSample(request);
        labSample.setStatus(LabSampleStatus.COLLECTED.name());
        return labSampleMapper.toLabSampleResponse(labSampleRepository.save(labSample));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR')")
    public List<LabSampleResponse> getLabSamples() {
        return labSampleRepository.findAll().stream()
                .map(labSample -> labSampleMapper.toLabSampleResponse(labSample)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF') or hasRole('LAB_TECHNICIAN') or hasRole('DOCTOR')")
    public LabSampleResponse getLabSampleById(int id) {
        return labSampleMapper.toLabSampleResponse(labSampleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LAB_SAMPLE_NOT_EXISTED)));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public LabSampleResponse upDateSampleLabById(int id, LabSampleUpdateRequest request) {
        LabSample labSample = labSampleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LAB_SAMPLE_NOT_EXISTED));
        if(!labSample.getSampleCode().equals(request.getSampleCode()) && labSampleRepository.existsBySampleCode(request.getSampleCode())){
            throw new AppException(ErrorCode.LAB_SAMPLE_CODE_EXISTED);
        }
        labSample.setSampleCode(request.getSampleCode());
        labSample.setSampleType(request.getSampleType());
        return labSampleMapper.toLabSampleResponse(labSampleRepository.save(labSample));
    }

}
