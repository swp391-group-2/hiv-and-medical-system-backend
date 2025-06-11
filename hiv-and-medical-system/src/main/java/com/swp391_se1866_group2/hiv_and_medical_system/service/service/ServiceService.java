package com.swp391_se1866_group2.hiv_and_medical_system.service.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ServiceMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request.ServiceCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {
    ServiceRepository serviceRepository;
    ServiceMapper serviceMapper;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ServiceResponse createService(ServiceCreationRequest request) {
        if(serviceRepository.existsByServiceType(request.getServiceType())){
            throw new AppException(ErrorCode.SERVICE_TYPE_EXISTED);
        }
        ServiceEntity service = serviceMapper.toServiceEntity(request);
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

}
