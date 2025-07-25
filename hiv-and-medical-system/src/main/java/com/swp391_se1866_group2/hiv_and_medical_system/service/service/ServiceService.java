package com.swp391_se1866_group2.hiv_and_medical_system.service.service;

import com.swp391_se1866_group2.hiv_and_medical_system.common.enums.ServiceType;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.AppException;
import com.swp391_se1866_group2.hiv_and_medical_system.common.exception.ErrorCode;
import com.swp391_se1866_group2.hiv_and_medical_system.common.mapper.ServiceMapper;
import com.swp391_se1866_group2.hiv_and_medical_system.image.service.ImageService;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request.ServiceCreationRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.request.ServiceUpdateRequest;
import com.swp391_se1866_group2.hiv_and_medical_system.service.dto.response.ServiceResponse;
import com.swp391_se1866_group2.hiv_and_medical_system.service.entity.ServiceEntity;
import com.swp391_se1866_group2.hiv_and_medical_system.service.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {
    ServiceRepository serviceRepository;
    ServiceMapper serviceMapper;
    ImageService imageService;

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ServiceResponse createService(ServiceCreationRequest request) {
        if(serviceRepository.existsByServiceType(request.getServiceType())){
            throw new AppException(ErrorCode.SERVICE_TYPE_EXISTED);
        }
        ServiceEntity service = serviceMapper.toServiceEntity(request);
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ServiceResponse updateService(int serviceId, ServiceUpdateRequest request, MultipartFile image) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTED));
        serviceMapper.updateServiceEntity(request, service);
        if(image != null && !image.isEmpty()){
            service = imageService.saveServiceEntityImage(image, service);
        }
        return serviceMapper.toServiceResponse(serviceRepository.save(service));
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.getAllServices();
    }

    public ServiceResponse getService(int serviceId) {
        return serviceRepository.getServiceById(serviceId);
    }

    public ServiceEntity getServiceEntityById(int serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_EXISTED));
    }

    public ServiceResponse getServiceByType(String type) {
        ServiceType serviceType;
        try {
            serviceType = ServiceType.valueOf(type.toUpperCase());
        }catch (AppException e){
            throw new AppException(ErrorCode.SERVICE_TYPE_NOT_EXISTED);
        }
        return serviceRepository.getServiceByServiceType(serviceType);
    }

}
